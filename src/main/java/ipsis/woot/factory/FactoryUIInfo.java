package ipsis.woot.factory;

import ipsis.woot.factory.blocks.ControllerTileEntity;
import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.loot.MobDrop;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactoryUIInfo {

    public List<ItemStack> mobs = new ArrayList<>();
    public List<FactoryUpgrade> upgrades = new ArrayList<>();
    public int recipeEffort;
    public int recipeTicks;
    public int recipeCostPerTick;
    public int effortStored;
    public int mobCount;
    public boolean valid = false;

    // Stacksize is drop chance
    // TODO handle float drop chance
    public List<ItemStack> drops = new ArrayList<>();

    public static FactoryUIInfo create(Setup setup, HeartTileEntity.Recipe recipe) {
        FactoryUIInfo info = new FactoryUIInfo();
        if (setup != null) {
            info.mobCount = setup.getMaxMobCount();
            info.recipeEffort = recipe.getNumUnits();
            info.recipeTicks = recipe.getNumTicks();
            info.recipeCostPerTick = recipe.getUnitsPerTick();
            for (Map.Entry<FactoryUpgradeType, Integer> entry : setup.getUpgrades().entrySet())
                info.upgrades.add(FactoryUpgrade.getUpgrade(entry.getKey(), entry.getValue()));


            for (FakeMob fakeMob : setup.getMobs()) {
                info.mobs.add(ControllerTileEntity.getItemStack(fakeMob));

                // TODO handle looting upgrade
                List<MobDrop> mobDrops = DropRegistry.get().getMobDrops(new FakeMobKey(fakeMob, 0));
                for (MobDrop mobDrop : mobDrops) {
                    ItemStack itemStack = mobDrop.getDroppedItem().copy();
                    itemStack.setCount((int)mobDrop.getDropChance());
                    info.drops.add(itemStack);
                }
            }
            info.valid = true;
        }
        return info;
    }

}
