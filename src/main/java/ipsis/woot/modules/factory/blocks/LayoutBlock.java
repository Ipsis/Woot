package ipsis.woot.modules.factory.blocks;

import ipsis.woot.base.WootBlockHFacing;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LayoutBlock extends WootBlockHFacing {

    public LayoutBlock() {
        super(FactoryModule.getDefaultBlockProperties());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LayoutTileEntity();
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {

        if (world.isClientSide)
            return ActionResultType.SUCCESS;

        // Empty hand to change
        if (playerEntity.getMainHandItem().isEmpty()) {
            TileEntity te = world.getBlockEntity(blockPos);
            if (te instanceof LayoutTileEntity) {
                if (playerEntity.isCrouching())
                    ((LayoutTileEntity) te).nextDisplayLevel();
                else
                    ((LayoutTileEntity) te).nextFactoryTier();
            }
        }

        return ActionResultType.CONSUME;
    }

}
