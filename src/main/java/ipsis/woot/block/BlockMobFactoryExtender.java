package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryExtender;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactoryExtender extends BlockWoot implements ITooltipInfo, ITileEntityProvider {

    public static final String BASENAME = "extender";

    public static final PropertyBool FORMED = PropertyBool.create("formed");

    public BlockMobFactoryExtender() {

        super(Material.ROCK, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FORMED, false));
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryExtender();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryExtender te = (TileEntityMobFactoryExtender) worldIn.getTileEntity(pos);
        te.blockAdded();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FORMED });
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityMobFactoryExtender) {
            TileEntityMobFactoryExtender te = (TileEntityMobFactoryExtender) worldIn.getTileEntity(pos);
            boolean formed = false;
            if (te != null)
                formed = te.isClientFormed();
            return state.withProperty(FORMED, formed);
        }

        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(FORMED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(FORMED) ? 1 : 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockExtender, BASENAME);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        if (blockAccess.getTileEntity(pos) instanceof TileEntityMobFactoryExtender) {
            TileEntityMobFactoryExtender te = (TileEntityMobFactoryExtender)blockAccess.getTileEntity(pos);
            boolean validBlock =  !isAir(blockState, blockAccess, pos.offset(side.getOpposite()));

            if (validBlock && te != null && !te.isClientFormed())
                return true;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        toolTip.add(StringHelper.localize(Lang.TOOLTIP_EXTENDER));
    }
}
