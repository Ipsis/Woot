package ipsis.woot.factory.blocks;

import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LayoutBlock extends WootBlock {

    public static final String REGNAME = "layout";

    public LayoutBlock() {
        super(Block.Properties.create(Material.GLASS), REGNAME);
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
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof LayoutTileEntity)
            ((LayoutTileEntity)te).setFacing(placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote)
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);

        if (handIn == Hand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof LayoutTileEntity) {
                LayoutTileEntity layout = (LayoutTileEntity)te;
                if (player.isSneaking()) {
                    layout.setNextLevel();
                } else {
                    layout.setNextTier();
                }
                WorldHelper.updateClient(worldIn, pos);
            }
        }

        return true;
    }
}
