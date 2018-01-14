package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.tileentity.TileEntityMobFactoryImporter;
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

import javax.annotation.Nullable;

public class BlockMobFactoryImporter extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "importer";

    public static final PropertyBool FORMED = PropertyBool.create("formed");

    public BlockMobFactoryImporter() {

        super (Material.ROCK, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FORMED, false));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryImporter();
    }


    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryImporter)
            ((TileEntityMobFactoryImporter) te).onBlockAdded();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FORMED });
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityMobFactoryImporter) {
            TileEntityMobFactoryImporter te = (TileEntityMobFactoryImporter) worldIn.getTileEntity(pos);
            boolean formed = false;
            if (te != null)
                formed = te.isClientFormed();
            return state.withProperty(FORMED, formed);
        }

        return state;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return true;
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
        ModelHelper.registerBlock(ModBlocks.blockImporter, BASENAME);
    }
}
