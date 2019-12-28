package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.FactoryUpgrade;
import ipsis.woot.modules.factory.FactoryUpgradeType;
import ipsis.woot.modules.factory.items.UpgradeItem;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.modules.factory.multiblock.MultiBlockTracker;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeTileEntity extends MultiBlockTileEntity implements WootDebug {

    public UpgradeTileEntity() {
        super(FactorySetup.FACTORY_UPGRADE_BLOCK_TILE.get());
    }

    public void tryAddUpgrade(BlockState state, FactoryUpgrade type) {

        if (state.get(UpgradeBlock.UPGRADE) == FactoryUpgrade.EMPTY) {
            // You can add any level 1 upgrade when it is empty
                world.setBlockState(pos,
                        state.with(UpgradeBlock.UPGRADE, type), 2);
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(pos);
                Woot.LOGGER.info("tryAddUpgrade: added {}", type);
                return;
        } else {
            FactoryUpgrade upgrade = getBlockState().get(UpgradeBlock.UPGRADE);
            FactoryUpgradeType currType = FactoryUpgrade.getType(upgrade);
            FactoryUpgradeType addType = FactoryUpgrade.getType(type);
            int currLevel = FactoryUpgrade.getLevel(upgrade);
            int addLevel = FactoryUpgrade.getLevel(type);

            // Can only add the same type and cannot exceed the max level
            if (currType == addType && currLevel < 3 && addLevel == currLevel + 1) {
                world.setBlockState(pos,
                        state.with(UpgradeBlock.UPGRADE, type), 2);
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(pos);
                Woot.LOGGER.info("tryAddUpgrade: added {}", type);
                return;
            }
        }
    }

    public void dropItems(BlockState state, World world, BlockPos pos) {
        FactoryUpgrade upgrade = state.get(UpgradeBlock.UPGRADE);
        if (upgrade == FactoryUpgrade.EMPTY)
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

    public @Nullable FactoryUpgrade getUpgrade(BlockState state) {
        return state.get(UpgradeBlock.UPGRADE);
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> UpgradeTileEntity");
        debug.add("      hasMaster: " + glue.hasMaster());
        debug.add("      upgrade: " + world.getBlockState(pos).get(UpgradeBlock.UPGRADE));
        return debug;
    }
}
