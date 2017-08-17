package ipsis.woot.block;

import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryCell;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * The cell stores power, however it can only be used as part of the formed farm
 */
public class BlockMobFactoryCell extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "cell";

    public static final PropertyBool FORMED = PropertyBool.create("formed");
    public static final PropertyEnum<EnumCellTier> TIER = PropertyEnum.<EnumCellTier>create("tier", EnumCellTier.class);

    public BlockMobFactoryCell() {

        super(Material.ROCK, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TIER, EnumCellTier.TIER_I).withProperty(FORMED, false));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMobFactoryCell(EnumCellTier.byMetadata(meta));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { TIER, FORMED } );
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryCell te = (TileEntityMobFactoryCell) worldIn.getTileEntity(pos);
        te.blockAdded();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityMobFactoryCell) {
            TileEntityMobFactoryCell te = (TileEntityMobFactoryCell) worldIn.getTileEntity(pos);
            boolean formed = false;
            if (te != null)
                formed = te.isClientFormed();
            return state.withProperty(FORMED, formed);
        }

        return state;
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(TIER).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {

        for (EnumCellTier t : EnumCellTier.values())
            items.add(new ItemStack(this, 1, t.getMetadata()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(TIER, EnumCellTier.byMetadata(meta)).withProperty(FORMED, false);
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
    public int getMetaFromState(IBlockState state) {

        return state.getValue(TIER).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID, BASENAME));

        for (int i = 0; i < EnumCellTier.VALUES.length; i++) {

            EnumCellTier e = EnumCellTier.VALUES[i];
            ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(
                    Reference.MOD_ID + ":" + BASENAME + "_" + e, "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBlockVariants, i, itemModelResourceLocation);
        }
    }

    public enum EnumCellTier implements IStringSerializable {
        TIER_I("tier_i"),
        TIER_II("tier_ii"),
        TIER_III("tier_iii");

        String name;
        EnumCellTier(String name) {
            this.name = name;
        }

        public static EnumCellTier[] VALUES = { TIER_I, TIER_II, TIER_III };


        @Override
        public String getName() {

            return this.name;
        }

        int getMetadata() {

            return this.ordinal();
        }

        public static EnumCellTier byMetadata(int metadata) {

            if (metadata < 0 || metadata >= VALUES.length)
                return TIER_I;

            return VALUES[metadata];
        }
    }
}
