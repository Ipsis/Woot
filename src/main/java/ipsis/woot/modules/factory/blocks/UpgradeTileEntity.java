package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.Perk;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.modules.factory.multiblock.MultiBlockTracker;
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

    public boolean tryAddUpgrade(BlockState state, Perk type) {

        if (state.get(UpgradeBlock.UPGRADE) == Perk.EMPTY) {
            // You can add any level 1 upgrade when it is empty
                world.setBlockState(pos,
                        state.with(UpgradeBlock.UPGRADE, type), 2);
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(pos);
                Woot.setup.getLogger().info("tryAddUpgrade: added {}", type);
                return true;
        } else {
            Perk upgrade = getBlockState().get(UpgradeBlock.UPGRADE);
            PerkType currType = Perk.getType(upgrade);
            PerkType addType = Perk.getType(type);
            int currLevel = Perk.getLevel(upgrade);
            int addLevel = Perk.getLevel(type);

            // Can only add the same type and cannot exceed the max level
            if (currType == addType && currLevel < 3 && addLevel == currLevel + 1) {
                world.setBlockState(pos,
                        state.with(UpgradeBlock.UPGRADE, type), 2);
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(pos);
                Woot.setup.getLogger().info("tryAddUpgrade: added {}", type);
                return true;
            }
        }
        return false;
    }

    public void dropItems(BlockState state, World world, BlockPos pos) {
        Perk upgrade = state.get(UpgradeBlock.UPGRADE);
        if (upgrade == Perk.EMPTY)
            return;

        int currLevel = Perk.getLevel(upgrade);
        for (int i = 1; i <= 3; i++) {
            if (i <= currLevel) {
                PerkType type = Perk.getType(upgrade);
                ItemStack itemStack = PerkItem.getItemStack(type, i);
                if (!itemStack.isEmpty()) {
                    itemStack.setCount(1);
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                }
            }
        }
    }

    public @Nullable
    Perk getUpgrade(BlockState state) {
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
