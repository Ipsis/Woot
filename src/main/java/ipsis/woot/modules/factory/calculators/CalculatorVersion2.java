package ipsis.woot.modules.factory.calculators;

import ipsis.woot.crafting.FactoryRecipe;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.util.FakeMob;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CalculatorVersion2 {

    private static final Logger LOGGER = LogManager.getLogger();

    public static HeartTileEntity.Recipe calculate(FormedSetup setup) {

        int baseSpawnTicks = setup.getMaxSpawnTime(); // HIgh spawn time across all mobs
        int actualSpawnTicks = baseSpawnTicks;

        LOGGER.debug("Calculator baseSpawnTicks:{} actualSpawnTick:{}",
                baseSpawnTicks, actualSpawnTicks);
        for (FakeMob fakeMob : setup.getAllMobs())
            LOGGER.debug("Calulator mob:{} params:{}", fakeMob, setup.getAllMobParams().get(fakeMob));

        int fluidCost = 0;
        for (FakeMob fakeMob : setup.getAllMobs()) {
            int mobFluidCost = setup.getAllMobParams().get(fakeMob).baseFluidCost * setup.getAllMobParams().get(fakeMob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS));
            LOGGER.debug("Calculator mob:{} fluidCost:{}", fakeMob, mobFluidCost);
            if (setup.getAllPerks().containsKey(PerkType.EFFICIENCY)) {
                LOGGER.debug("Calculator: EFFICIENCY");
                int fluidSaving = (int)(mobFluidCost / 100.0F) * setup.getAllMobParams().get(fakeMob).getPerkEfficiencyValue();
                mobFluidCost -= fluidSaving;
                mobFluidCost = MathHelper.clamp(mobFluidCost, 0, Integer.MAX_VALUE);
                LOGGER.debug("Calculator: saving of {}mB -> {}mB", fluidSaving, mobFluidCost);
            }
            fluidCost += mobFluidCost;
        }
        LOGGER.debug("Calculator fluidCost:{}", fluidCost);

        if (setup.getAllPerks().containsKey(PerkType.RATE)) {
            LOGGER.debug("Calculator: RATE");
            // Lowest reduction in rate across all mobs
            int rateSaving = (int)((baseSpawnTicks / 100.0F) * setup.getMinRateValue());
            actualSpawnTicks -= rateSaving;
            LOGGER.debug("Calculator: {} @ {}% -> {}", baseSpawnTicks, setup.getMinRateValue(), actualSpawnTicks);
        }

        HeartTileEntity.Recipe recipe = new HeartTileEntity.Recipe(actualSpawnTicks, fluidCost);

        for (FakeMob fakeMob : setup.getAllMobs()) {
            ItemStack controller = ControllerTileEntity.getItemStack(fakeMob);
            List<FactoryRecipe> recipes = setup.getWorld().getRecipeManager().getRecipes(
                    FactoryRecipe.FACTORY_TYPE,
                    new Inventory(controller), setup.getWorld());
            for (FactoryRecipe factoryRecipe : recipes) {
                if (factoryRecipe.getFakeMob().equals(fakeMob)) {
                    for (ItemStack itemStack : factoryRecipe.getItems())
                        recipe.addItem(fakeMob, itemStack.copy());
                    for (FluidStack fluidStack : factoryRecipe.getFluids())
                        recipe.addFluid(fakeMob, fluidStack);
                    break;
                }
            }
        }

        return recipe;
    }
}
