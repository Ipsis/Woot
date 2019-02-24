package ipsis.woot.layout;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.FactoryTier;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLayout extends WootBlock implements IWootDebug {

    public static final String BASENAME = "layout";
    public BlockLayout() {
        super(Block.Properties.create(Material.ROCK), BASENAME);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityLayout();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityLayout) {
            TileEntityLayout layout = (TileEntityLayout)te;
            layout.setFacing(placer.getHorizontalFacing().getOpposite());
        }
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (WorldHelper.isClientWorld(worldIn))
            return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);

        if (hand == EnumHand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityLayout) {
                TileEntityLayout layout = (TileEntityLayout) te;

                if (player.isSneaking()) {
                    int layer = layout.setNextLevel();
                    PlayerHelper.sendActionBarMessage(player, "Layer: " + Integer.toString(layer));
                } else {
                    FactoryTier tier = layout.setNextTier();
                    PlayerHelper.sendActionBarMessage(player, "Tier: " + Integer.toString(tier.ordinal()));
                }
                WorldHelper.updateClient(worldIn, pos);
            }
        }

        return true;
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext context) {
        TileEntity te = context.getWorld().getTileEntity(context.getPos());
        if (te instanceof IWootDebug)
            ((IWootDebug)te).getDebugText(debug, context);
        return debug;
    }
}
