package ipsis.woot.blocks;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLayout extends WootBlock implements ITileEntityProvider {

    private static final String BASENAME = "layout";

    public BlockLayout() {
        super(Material.ROCK, BASENAME);
    }

    /**
     * ITileEntityProvider
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLayout();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityLayout) {
            TileEntityLayout layout = (TileEntityLayout)te;
            EnumFacing f = placer.getHorizontalFacing().getOpposite();
            layout.setFacing(f);
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        // This stops the TESR rendering really dark!
        return false;
    }
}
