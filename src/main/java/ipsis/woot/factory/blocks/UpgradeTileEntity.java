package ipsis.woot.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.FactoryUpgradeType;
import ipsis.woot.factory.items.UpgradeItem;
import ipsis.woot.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.factory.multiblock.MultiBlockTracker;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeTileEntity extends MultiBlockTileEntity implements WootDebug {

    public UpgradeTileEntity() {
        super(ModBlocks.FACTORY_UPGRADE_BLOCK_TILE);
    }

    FactoryUpgrade upgrade = null;

    public boolean tryAddUpgrade(FactoryUpgrade type) {

        if (upgrade == null) {
            // You can add any level 1 upgrade when it is empty
            if (FactoryUpgrade.LEVEL_1_UPGRADES.contains(type)) {
                upgrade = type;
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(pos);
                Woot.LOGGER.info("tryAddUpgrade: added {}", upgrade);
                return true;
            }
        } else {
            FactoryUpgradeType currType = FactoryUpgrade.getType(upgrade);
            FactoryUpgradeType addType = FactoryUpgrade.getType(type);
            int currLevel = FactoryUpgrade.getLevel(upgrade);
            int addLevel = FactoryUpgrade.getLevel(type);

            // Can only add the same type and cannot exceed the max level
            if (currType == addType && currLevel < 3 && addLevel == currLevel + 1) {
                upgrade = type;
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(pos);
                Woot.LOGGER.info("tryAddUpgrade: added {}", upgrade);
                return true;
            }
        }
        return false;
    }

    public void dropItems(World world, BlockPos pos) {
        if (upgrade == null)
            return;

        int currLevel = FactoryUpgrade.getLevel(upgrade);
        for (int i = 1; i <= 3; i++) {
            if (i <= currLevel) {
                FactoryUpgradeType type = FactoryUpgrade.getType(upgrade);
                ItemStack itemStack = UpgradeItem.getItemStack(type, i);
                if (!itemStack.isEmpty()) {
                    itemStack.setCount(1);
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                }
            }
        }
    }

    public @Nullable FactoryUpgrade getUpgrade() { return this.upgrade; }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> UpgradeTileEntity");
        debug.add("      hasMaster: " + glue.hasMaster());
        debug.add("      type: " + upgrade);
        return debug;
    }
}
