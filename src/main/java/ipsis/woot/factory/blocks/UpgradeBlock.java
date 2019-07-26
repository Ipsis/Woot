package ipsis.woot.factory.blocks;

import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.items.UpgradeItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UpgradeBlock extends FactoryBlock {

    public UpgradeBlock(FactoryComponent component) {
        super(component);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isRemote) {
            ItemStack itemStack = player.getHeldItem(handIn);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof UpgradeItem) {
                UpgradeItem upgradeItem = (UpgradeItem)itemStack.getItem();

                TileEntity te = worldIn.getTileEntity(pos);
                if (te instanceof UpgradeTileEntity) {
                    if (((UpgradeTileEntity) te).tryAddUpgrade(upgradeItem.getFactoryUpgrade())) {

                    }
                }
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        // This is how the chest, hopper etc drop their contents
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof UpgradeTileEntity) {
                ((UpgradeTileEntity) te).dropItems(worldIn, pos);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new UpgradeTileEntity();
    }
}
