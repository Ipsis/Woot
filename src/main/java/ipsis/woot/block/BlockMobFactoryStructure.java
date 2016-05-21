package ipsis.woot.block;

import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryStructure;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactoryStructure extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "structure";

    public static final PropertyBool FORMED = PropertyBool.create("formed");
    public static final PropertyEnum<EnumMobFactoryModule> MODULE = PropertyEnum.<EnumMobFactoryModule>create("module", EnumMobFactoryModule.class);

    public BlockMobFactoryStructure() {

        super (Material.ROCK, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(MODULE, EnumMobFactoryModule.BLOCK_1).withProperty(FORMED, false));
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    public EnumMobFactoryModule getModuleTypeFromState(IBlockState state) {

        return EnumMobFactoryModule.byMetadata(getMetaFromState(state));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryStructure();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryStructure te = (TileEntityMobFactoryStructure)worldIn.getTileEntity(pos);
        te.blockAdded();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { MODULE, FORMED });
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(MODULE).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (EnumMobFactoryModule m : EnumMobFactoryModule.values())
            list.add(new ItemStack(itemIn, 1, m.getMetadata()));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityMobFactoryStructure) {
            TileEntityMobFactoryStructure te = (TileEntityMobFactoryStructure) worldIn.getTileEntity(pos);
            boolean formed = false;
            if (te != null)
                formed = te.isClientFormed();
            return state.withProperty(FORMED, formed);
        }

        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(MODULE, EnumMobFactoryModule.byMetadata(meta)).withProperty(FORMED, false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(MODULE).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID_LOWER, BASENAME));

        for (int i = 0; i < EnumMobFactoryModule.values().length; i++) {

            EnumMobFactoryModule e = EnumMobFactoryModule.VALUES[i];
            ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(
                    Reference.MOD_ID + ":" + BASENAME + "_" + e, "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBlockVariants, i, itemModelResourceLocation);
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }
}
