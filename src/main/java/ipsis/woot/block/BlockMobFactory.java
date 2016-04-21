package ipsis.woot.block;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactory extends BlockContainerWoot implements ITooltipInfo {

    public static final String BASENAME = "factory";
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockMobFactory() {

        super(Material.rock, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactory();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        EnumFacing f = placer.getHorizontalFacing().getOpposite();
        worldIn.setBlockState(pos, state.withProperty(FACING, f), 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockFactory, BASENAME);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "I", Settings.tierIRF));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "II", Settings.tierIIRF));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "III", Settings.tierIIIRF));
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {

        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    protected BlockStateContainer createBlockState()
    {

        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
}
