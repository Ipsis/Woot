package ipsis.woot.layout;

import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helpers.PlayerHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClientWorld(worldIn))
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);


        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityLayout) {
            TileEntityLayout layout = (TileEntityLayout)te;

            if (playerIn.isSneaking()) {
                int layer = layout.setNextFactoryLevel();
                PlayerHelper.sendActionBarMessage(playerIn, Integer.toString(layer));
            } else {
                FactoryTier tier = layout.setNextFactoryTier();
                // TODO log the tier to the screen
                PlayerHelper.sendActionBarMessage(playerIn, tier.toString());
            }
            WorldHelper.updateClient(worldIn,pos);
        }

        return true;
    }
}
