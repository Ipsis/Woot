package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactoryUpgrade extends BlockContainerWoot implements ITooltipInfo {

    public static final String BASENAME = "upgrade";

    public static final PropertyBool ACTIVE = PropertyBool.create("active");
    public static final PropertyEnum<EnumSpawnerUpgrade> VARIANT = PropertyEnum.<EnumSpawnerUpgrade>create("variant", EnumSpawnerUpgrade.class);
    public BlockMobFactoryUpgrade() {

        super(Material.rock, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumSpawnerUpgrade.RATE_I).withProperty(ACTIVE, false));
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {VARIANT, ACTIVE});
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (EnumSpawnerUpgrade u : EnumSpawnerUpgrade.values())
            list.add(new ItemStack(itemIn, 1, u.getMetadata()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(VARIANT, EnumSpawnerUpgrade.getFromMetadata(meta)).withProperty(ACTIVE, false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ResourceLocation[] locations = new ResourceLocation[EnumSpawnerUpgrade.values().length];
        for (int i = 0; i < EnumSpawnerUpgrade.values().length; i++)
            locations[i] = new ResourceLocation(Reference.MOD_NAME_LOWER + ":" + BASENAME + "_" + EnumSpawnerUpgrade.getFromMetadata(i));

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.blockUpgrade), locations);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryUpgrade();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryUpgrade te = (TileEntityMobFactoryUpgrade) worldIn.getTileEntity(pos);
        te.blockAdded();
    }

    @Override
    public int getRenderType() {

        return 3;
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        EnumSpawnerUpgrade type = EnumSpawnerUpgrade.getFromMetadata(meta);
        switch (type) {
            case RATE_I:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_RATE_EFFECT), Settings.rateITicks));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.rateIRfTick));
                break;
            case RATE_II:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_RATE_EFFECT), Settings.rateIITicks));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.rateIIRfTick));
                break;
            case RATE_III:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_RATE_EFFECT), Settings.rateIIITicks));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.rateIIIRfTick));
                break;
            case LOOTING_I:
                toolTip.add(StringHelper.localize(Lang.TOOLTIP_LOOTING_I_EFFECT));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.lootingIRfTick));
                break;
            case LOOTING_II:
                toolTip.add(StringHelper.localize(Lang.TOOLTIP_LOOTING_II_EFFECT));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.lootingIIRfTick));
                break;
            case LOOTING_III:
                toolTip.add(StringHelper.localize(Lang.TOOLTIP_LOOTING_III_EFFECT));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.lootingIIIRfTick));
                break;
            case XP_I:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_XP_EFFECT), Settings.xpIBoost));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.xpIRfTick));
                break;
            case XP_II:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_XP_EFFECT), Settings.xpIIBoost));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.xpIIRfTick));
                break;
            case XP_III:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_XP_EFFECT), Settings.xpIIIBoost));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.xpIIIRfTick));
                break;
            case MASS_I:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_MASS_EFFECT), Settings.massIMobs));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.massIRfTick));
                break;
            case MASS_II:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_MASS_EFFECT), Settings.massIIMobs));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.massIIRfTick));
                break;
            case MASS_III:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_MASS_EFFECT), Settings.massIIIMobs));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.massIIIRfTick));
                break;
            case DECAPITATE_I:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_DECAP_EFFECT), Settings.decapitateIChance));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.decapitateIRfTick));
                break;
            case DECAPITATE_II:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_DECAP_EFFECT), Settings.decapitateIIChance));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.decapitateIIRfTick));
                break;
            case DECAPITATE_III:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_DECAP_EFFECT), Settings.decapitateIIIChance));
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_UPGRADE_COST), Settings.decapitateIIIRfTick));
                break;
            default:
                break;
        }
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
}
