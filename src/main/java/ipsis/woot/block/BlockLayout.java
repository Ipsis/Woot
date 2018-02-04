package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.tileentity.TileEntityLayout;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockLayout extends BlockWoot implements ITileEntityProvider, ITooltipInfo {

    public static final String BASENAME = "layout";

    public BlockLayout() {

        super(Material.ROCK, BASENAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockLayout, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityLayout();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityLayout) {
            EnumFacing f = placer.getHorizontalFacing().getOpposite();
            ((TileEntityLayout) te).setFacing(f);
            ((TileEntityLayout) te).refreshLayout();
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        /**
         * Activate with redstone in creative - builds the factory
         * Activate step through different tiers
         * Activate while sneaking change y-level all->1->2->....->all
         */

        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileEntityLayout) {
                if (playerIn.isCreative() && !playerIn.getHeldItem(hand).isEmpty() && playerIn.getHeldItem(hand).getItem() == Items.REDSTONE) {
                    ((TileEntityLayout) te).buildFactory();
                } else if (playerIn.isSneaking()) {
                    ((TileEntityLayout) te).setNextLevel();
                    worldIn.notifyBlockUpdate(pos, state, state, 4);
                } else {
                    ((TileEntityLayout) te).setNextTier();
                    EnumMobFactoryTier tier = ((TileEntityLayout) te).getTier();
                    playerIn.sendStatusMessage(new TextComponentString(tier.getTranslated("info.woot.tier")), false);
                    worldIn.notifyBlockUpdate(pos, state, state, 4);
                }
            }
        }

        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        /* This stops the TESR rendering really dark! */
        return false;
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        toolTip.add(StringHelper.localize("info.woot.guide.rclick"));
        toolTip.add(StringHelper.localize("info.woot.guide.srclick"));
    }
}
