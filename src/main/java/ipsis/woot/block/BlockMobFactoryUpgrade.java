package ipsis.woot.block;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactoryUpgrade extends BlockMobFactoryUpgradeBase implements  ITooltipInfo {

    public static final String BASENAME = "upgrade";

    public static final PropertyBool ACTIVE = PropertyBool.create("active");
    public static final PropertyEnum<EnumVariantUpgrade> VARIANT =
            PropertyEnum.<EnumVariantUpgrade>create("variant", EnumVariantUpgrade.class);

    public BlockMobFactoryUpgrade() {

        super(BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumVariantUpgrade.RATE_I).withProperty(ACTIVE, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, ACTIVE});
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (EnumVariantUpgrade u : EnumVariantUpgrade.values())
            list.add(new ItemStack(itemIn, 1, u.getMetadata()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(VARIANT, EnumVariantUpgrade.getFromMetadata(meta)).withProperty(ACTIVE, false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID_LOWER, BASENAME));

        for (int i = 0; i < EnumVariantUpgrade.values().length; i++) {

            EnumVariantUpgrade e = EnumVariantUpgrade.getFromMetadata(i);
            ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(
                    Reference.MOD_ID + ":" + BASENAME + "_" + e, "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBlockVariants, i, itemModelResourceLocation);
        }
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        EnumSpawnerUpgrade type = EnumSpawnerUpgrade.getFromVariant(EnumVariantUpgrade.getFromMetadata(meta));
        getUpgradeTooltip(type, toolTip, showAdvanced, meta, detail);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityMobFactoryUpgrade) {
            TileEntityMobFactoryUpgrade te = (TileEntityMobFactoryUpgrade) worldIn.getTileEntity(pos);
            boolean formed = false;
            if (te != null)
                formed = te.isClientFormed();
            return state.withProperty(ACTIVE, formed);
        }

        return state;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }
}
