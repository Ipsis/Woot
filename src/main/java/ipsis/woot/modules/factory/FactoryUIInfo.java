package ipsis.woot.modules.factory;

import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.simulation.DropRegistry;
import ipsis.woot.modules.simulation.MobDrop;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactoryUIInfo {

    public List<ItemStack> mobs = new ArrayList<>();
    public List<Mob> mobInfo = new ArrayList<>();
    public List<Perk> upgrades = new ArrayList<>();
    public int recipeEffort;
    public int recipeTicks;
    public int recipeCostPerTick;
    public int effortStored;
    public int mobCount;
    public boolean valid = false;

    public static class Mob {
        public boolean missingIngredients = false;
        public ItemStack controller;
        public List<ItemStack> itemIngredients = new ArrayList<>();
        public List<FluidStack> fluidIngredients = new ArrayList<>();
        public Mob(ItemStack itemStack) { controller = itemStack; }
    }

    public List<ItemStack> drops = new ArrayList<>();

    public static FactoryUIInfo create(Setup setup, HeartTileEntity.Recipe recipe) {
        FactoryUIInfo info = new FactoryUIInfo();
        if (setup != null) {
            info.mobCount = 65535;
            info.recipeEffort = recipe.getNumUnits();
            info.recipeTicks = recipe.getNumTicks();
            info.recipeCostPerTick = recipe.getUnitsPerTick();

            int looting = 0;
            for (Map.Entry<PerkType, Integer> entry : setup.getPerks().entrySet()) {
                info.upgrades.add(Perk.getPerks(entry.getKey(), entry.getValue()));
                if (entry.getKey() == PerkType.LOOTING)
                    looting = entry.getValue();
            }

            for (FakeMob fakeMob : setup.getMobs()) {
                Mob mob = new Mob(ControllerTileEntity.getItemStack(fakeMob));
                info.mobInfo.add(mob);
                info.mobs.add(ControllerTileEntity.getItemStack(fakeMob));
                List<MobDrop> mobDrops = DropRegistry.get().getMobDrops(new FakeMobKey(fakeMob, looting));
                for (MobDrop mobDrop : mobDrops) {
                    ItemStack itemStack = mobDrop.getDroppedItem().copy();
                    float dropChance = mobDrop.getDropChance();
                    itemStack.setCount((int)(dropChance * 100.0F));
                    info.drops.add(itemStack);
                }
            }
            info.valid = true;
        }
        return info;
    }

}
