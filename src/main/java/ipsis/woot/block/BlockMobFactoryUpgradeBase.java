package ipsis.woot.block;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class BlockMobFactoryUpgradeBase extends BlockWoot implements ITileEntityProvider{

    public BlockMobFactoryUpgradeBase(String basename) {
        super(Material.ROCK, basename);
        setRegistryName(Reference.MOD_ID_LOWER, basename);
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

    public void getUpgradeTooltip(EnumSpawnerUpgrade u, List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        switch (u) {
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
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_XP_BASE_EFFECT), Settings.xpIBoost));
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
            case EFFICIENCY_I:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_EFFICIENCY_EFFECT), Settings.efficiencyI));
                break;
            case EFFICIENCY_II:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_EFFICIENCY_EFFECT), Settings.efficiencyII));
                break;
            case EFFICIENCY_III:
                toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_EFFICIENCY_EFFECT), Settings.efficiencyIII));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        if (blockAccess.getTileEntity(pos) instanceof TileEntityMobFactoryUpgrade) {
            TileEntityMobFactoryUpgrade te = (TileEntityMobFactoryUpgrade)blockAccess.getTileEntity(pos);
            boolean validBlock =  !isAir(blockState, blockAccess, pos.offset(side.getOpposite()));

            if (validBlock && !te.isClientFormed())
                return true;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    /**
     * The enum is split over multiple blocks because of metadata limits
     * This function returns the metadata value for the enum , depending on the block it is on
     */
    public static int getBlockSplitMeta(EnumSpawnerUpgrade u) {

        if (u.ordinal() >= EnumSpawnerUpgrade.RATE_I.ordinal() && u.ordinal() <= EnumSpawnerUpgrade.DECAPITATE_III.ordinal())
            return u.ordinal();

        return u.ordinal() - (EnumSpawnerUpgrade.DECAPITATE_III.ordinal() + 1);
    }
}
