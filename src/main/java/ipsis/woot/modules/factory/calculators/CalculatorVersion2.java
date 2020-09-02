package ipsis.woot.modules.factory.calculators;

import ipsis.woot.crafting.FactoryRecipe;
import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FluidStackHelper;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CalculatorVersion2 {

    private static final Logger LOGGER = LogManager.getLogger();

    private static double getEfficiency(FakeMob fakeMob, FormedSetup setup) {
        double v = 0.0F;
        if (setup.hasConatusExotic()) {
            v = FactoryConfiguration.EXOTIC_C.get();
            LOGGER.debug("Calculator: EXOTIC {} conatus efficiency", v);
        } else if (setup.getAllPerks().containsKey(PerkType.EFFICIENCY)) {
            v = setup.getAllMobParams().get(fakeMob).getPerkEfficiencyValue();
            LOGGER.debug("Calculator: PERK {} conatus efficiency", v);
        }
        return v;
    }

    private static int calcConatus(FormedSetup setup) {
        int fluidCost = 0;
        for (FakeMob fakeMob : setup.getAllMobs()) {
            int mobCost = setup.getAllMobParams().get(fakeMob).baseFluidCost * setup.getAllMobParams().get(fakeMob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS), setup.hasMassExotic());
            LOGGER.debug("Calculator mob:{} fluidCost:{}", fakeMob, mobCost);

            int fluidSaving = (int)((mobCost / 100.0F) * getEfficiency(fakeMob, setup));
            mobCost -= fluidSaving;
            mobCost = MathHelper.clamp(mobCost, 0, Integer.MAX_VALUE);
            LOGGER.debug("Calculator: {} saving of {}mB -> {}mB", fakeMob, fluidSaving, mobCost);
            fluidCost += mobCost;
        }
        return fluidCost;
    }

    private static int calcSpawnTick(int actualSpawnTicks, int baseSpawnTicks, FormedSetup setup) {
        int ticks = actualSpawnTicks;
        if (setup.hasSpawnTimExotic()) {
            ticks = FactoryConfiguration.EXOTIC_D.get();
            LOGGER.debug("Calculator: EXOTIC {} ticks", ticks);
        } else {
            int rateSaving = (int)((baseSpawnTicks / 100.0F) * setup.getMinRateValue());
            LOGGER.debug("Calculator: PERK {} rate", rateSaving);
            // Lowest reduction in rate across all mobs
            ticks -= rateSaving;
            LOGGER.debug("Calculator: {} @ {}% -> {}", baseSpawnTicks, setup.getMinRateValue(), actualSpawnTicks);
        }

        return ticks;
    }

    public static List<ItemStack> getRecipeItems(FakeMob fakeMob, NonNullList<ItemStack> recipeItems, FormedSetup setup) {
        int mobCount = setup.getAllMobParams().get(fakeMob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS), setup.hasMassExotic());
        List<ItemStack> items = new ArrayList<>();
        if (setup.hasItemIngredientExotic()) {
            for (ItemStack itemStack : recipeItems) {
                ItemStack itemStack1 = itemStack.copy();
                itemStack1.setCount(itemStack1.getCount() * mobCount);
                itemStack1 = ItemStackHelper.reduceByPercentage(itemStack1, FactoryConfiguration.EXOTIC_B.get());
                if (!itemStack1.isEmpty())
                    items.add(itemStack1);
            }
        } else {
            for (ItemStack itemStack : recipeItems) {
                if (!itemStack.isEmpty()) {
                    ItemStack itemStack1 = itemStack.copy();
                    itemStack1.setCount(itemStack1.getCount() * mobCount);
                    items.add(itemStack1);
                }
            }
        }
        return items;
    }

    public static List<FluidStack> getRecipeFluids(FakeMob fakeMob, NonNullList<FluidStack> recipeFluids, FormedSetup setup) {
        int mobCount = setup.getAllMobParams().get(fakeMob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS), setup.hasMassExotic());
        List<FluidStack> fluids = new ArrayList<>();
        if (setup.hasFluidIngredientExotic()) {
            for (FluidStack fluidStack : recipeFluids) {
                FluidStack fluidStack1 = fluidStack.copy();
                fluidStack1.setAmount(fluidStack1.getAmount() * mobCount);
                fluidStack1 = FluidStackHelper.reduceByPercentage(fluidStack1, FactoryConfiguration.EXOTIC_A.get());
                if (!fluidStack1.isEmpty())
                    fluids.add(fluidStack1);
            }
        } else {
            for (FluidStack fluidStack : recipeFluids) {
                if (!fluidStack.isEmpty()) {
                    FluidStack fluidStack1 = fluidStack.copy();
                    fluidStack1.setAmount(fluidStack1.getAmount() * mobCount);
                    fluids.add(fluidStack1);
                }
            }
        }
        return fluids;
    }


    public static HeartTileEntity.Recipe calculate(FormedSetup setup) {

        for (FakeMob fakeMob : setup.getAllMobs())
            LOGGER.debug("Calulator mob:{} params:{}", fakeMob, setup.getAllMobParams().get(fakeMob));

        /**
         * Conatus Cost
         * Impacted by efficiency or exotic C
         */
        int fluidCost = calcConatus(setup);
        LOGGER.debug("Calculator fluidCost:{}", fluidCost);

        /**
         * Spawn ticks
         * Impacted by rate or exotic D
         */
        int baseSpawnTicks = setup.getMaxSpawnTime(); // Highest spawn time across all mobs
        int actualSpawnTicks = baseSpawnTicks;
        LOGGER.debug("Calculator baseSpawnTicks:{} actualSpawnTick:{}", baseSpawnTicks, actualSpawnTicks);
        actualSpawnTicks = calcSpawnTick(actualSpawnTicks, baseSpawnTicks, setup);
        LOGGER.debug("Calculator actualSpawnTick:{}", actualSpawnTicks);

        HeartTileEntity.Recipe recipe = new HeartTileEntity.Recipe(actualSpawnTicks, fluidCost);

        /**
         * Recipe ingredients
         * Impacted by exotic A and exotic B
         */
        for (FakeMob fakeMob : setup.getAllMobs()) {
            ItemStack controller = ControllerTileEntity.getItemStack(fakeMob);
            List<FactoryRecipe> recipes = setup.getWorld().getRecipeManager().getRecipes(
                    FactoryRecipe.FACTORY_TYPE,
                    new Inventory(controller), setup.getWorld());

            for (FactoryRecipe factoryRecipe : recipes) {
                if (factoryRecipe.getFakeMob().equals(fakeMob)) {
                    List<ItemStack> recipeItems = getRecipeItems(fakeMob, factoryRecipe.getItems(), setup);
                    List<FluidStack> recipeFluids = getRecipeFluids(fakeMob, factoryRecipe.getFluids(), setup);
                    recipeItems.forEach(i -> recipe.addItem(fakeMob, i.copy()));
                    recipeFluids.forEach(i -> recipe.addFluid(fakeMob, i.copy()));
                    break;
                }
            }
        }

        return recipe;
    }
}
