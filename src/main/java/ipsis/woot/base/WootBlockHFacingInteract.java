package ipsis.woot.base;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WootBlockHFacingInteract extends WootBlockHFacing {

    public WootBlockHFacingInteract(AbstractBlock.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {

        if (world.isClientSide)
            return ActionResultType.SUCCESS;

        openContainer(world, blockPos, playerEntity);
        return ActionResultType.CONSUME;
    }

    private void openContainer(World world, BlockPos blockPos, PlayerEntity playerEntity) {

        TileEntity te = world.getBlockEntity(blockPos);
        if (te instanceof INamedContainerProvider)
            NetworkHooks.openGui((ServerPlayerEntity)playerEntity, (INamedContainerProvider)te, blockPos);
    }




}
