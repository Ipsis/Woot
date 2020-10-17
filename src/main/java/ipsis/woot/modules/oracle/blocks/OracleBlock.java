package ipsis.woot.modules.oracle.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class OracleBlock extends Block {

    public OracleBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.5F));
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
        if (worldIn.isRemote)
            return super.onBlockActivated(state, worldIn, pos, player, handIn, blockRayTraceResult);

        TileEntity te = worldIn.getTileEntity((pos));
        if (te instanceof INamedContainerProvider)
            NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)te, te.getPos());
        else
            throw new IllegalStateException("Named container provider is missing");

        return ActionResultType.SUCCESS; // Block was activated
    }
}
