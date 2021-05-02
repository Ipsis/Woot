package ipsis.woot.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WootBlock extends Block {

    public WootBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState newBlockState, boolean isMoving) {

        if (!blockState.is(newBlockState.getBlock())) {
            TileEntity te = world.getBlockEntity(blockPos);
            // if implements IDropContents then do the Inventory.dropContents
        }

        super.onRemove(blockState, world, blockPos, newBlockState, isMoving);
    }
}
