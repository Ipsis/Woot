package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityLayout;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLayout extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "layout";

    public BlockLayout() {

        super(Material.ROCK, BASENAME);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
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
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileEntityLayout) {
                ((TileEntityLayout) te).setNextTier();
                EnumMobFactoryTier tier = ((TileEntityLayout) te).getTier();
                playerIn.addChatComponentMessage(new TextComponentString(String.format("Tier %s", (tier == EnumMobFactoryTier.TIER_ONE ? "I" : tier == EnumMobFactoryTier.TIER_TWO ? "II" : "III"))));
                worldIn.notifyBlockUpdate(pos, state, state, 4);
            }
        }

        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {

        return false;
    }
}
