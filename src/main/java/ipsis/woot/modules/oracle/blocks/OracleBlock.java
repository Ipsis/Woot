package ipsis.woot.modules.oracle.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class OracleBlock extends Block {

    public OracleBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new OracleTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote)
            return super.onBlockActivated(blockState, world, pos, playerEntity, hand, blockRayTraceResult);

        TileEntity te = world.getTileEntity((pos));
        if (te instanceof INamedContainerProvider)
            NetworkHooks.openGui((ServerPlayerEntity)playerEntity, (INamedContainerProvider)te, te.getPos());
        else
            throw new IllegalStateException("Named container provider is missing");

        return true; // Block was activated
    }
}
