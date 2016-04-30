package ipsis.woot.block;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.manager.UpgradeSetup;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class BlockMobFactory extends BlockWoot implements ITooltipInfo, ITileEntityProvider {

    public static final String BASENAME = "factory";
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockMobFactory() {

        super(Material.ROCK, BASENAME);
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote && (worldIn.getTileEntity(pos) instanceof  TileEntityMobFactory)) {

            TileEntityMobFactory te = (TileEntityMobFactory)worldIn.getTileEntity(pos);

            List<String> out = new ArrayList<String>();
            out.add(TextFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_FACTORY_TIER),
                    (te.getFactoryTier() == EnumMobFactoryTier.TIER_ONE ? "I" : te.getFactoryTier() == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));

            out.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB), te.getDisplayName()));


            int maxMass = Settings.baseMobCount;
            UpgradeSetup upgradeSetup = te.getUpgradeSetup();
            if (upgradeSetup != null) {
                if (upgradeSetup.hasMassUpgrade())
                    maxMass = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();
            }
            out.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RATE),
                    maxMass, te.getSpawnReq().getSpawnTime()));
            out.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_COST),
                    te.getSpawnReq().getTotalRf(), te.getSpawnReq().getRfPerTick()));
            //out.add(TextFormatting.RED + String.format("%d / %d RF", te.getEnergyStored(EnumFacing.DOWN), te.getMaxEnergyStored(EnumFacing.DOWN)));


            if (upgradeSetup != null) {
                List<EnumSpawnerUpgrade> upgradeList = te.getUpgradeSetup().getUpgradeList();
                if (!upgradeList.isEmpty()) {

                    for (EnumSpawnerUpgrade upgrade : upgradeList) {
                        SpawnerUpgrade u = UpgradeManager.getSpawnerUpgrade(upgrade);
                        TextFormatting f;
                        if (u.getUpgradeTier() == 1)
                            f = TextFormatting.GRAY;
                        else if (u.getUpgradeTier() == 2)
                            f = TextFormatting.GOLD;
                        else
                            f = TextFormatting.AQUA;
                        out.add(f + StringHelper.localize( Lang.TOOLTIP_UPGRADE + upgrade));
                    }
                }
            }

            for (String s : out)
                playerIn.addChatComponentMessage(new TextComponentString(s));
        }
        return true;
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
