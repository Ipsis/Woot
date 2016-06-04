package ipsis.woot.block;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.manager.UpgradeSetup;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.top.ITOPInfoProvider;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class BlockMobFactory extends BlockWoot implements ITooltipInfo, ITileEntityProvider, ITOPInfoProvider {

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
            if (te.isFormed()) {

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
                            out.add(f + StringHelper.localize(Lang.TOOLTIP_UPGRADE + upgrade));
                        }
                    }
                }

                for (String s : out)
                    playerIn.addChatComponentMessage(new TextComponentString(s));
            } else {
                te.manualValidate(playerIn);
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockFactory, BASENAME);
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

        return state.getValue(FACING).getIndex();
    }

    protected BlockStateContainer createBlockState()
    {

        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityMobFactory) {
            TileEntityMobFactory factoryTE = (TileEntityMobFactory)te;

            if (factoryTE.isFormed()) {

                PluginTooltipInfo info = new PluginTooltipInfo(factoryTE);

                probeInfo.text(TextFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_FACTORY_TIER),
                        (info.tier == EnumMobFactoryTier.TIER_ONE ? "I" : info.tier == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));
                probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB), info.displayName));
                probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RATE), info.maxMass, info.spawnTime));
                probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_COST), info.spawnRF, info.spawnTickRF));

                if (info.isRunning)
                    probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RUNNING)));
                else
                    probeInfo.text(TextFormatting.RED + String.format(StringHelper.localize(Lang.WAILA_FACTORY_STOPPED)));

                if (mode == ProbeMode.EXTENDED) {

                    if (factoryTE.getUpgradeSetup() != null) {

                        for (EnumSpawnerUpgrade e : factoryTE.getUpgradeSetup().getUpgradeList()) {

                            TextFormatting f;
                            SpawnerUpgrade u = UpgradeManager.getSpawnerUpgrade(e);

                            if (u.getUpgradeTier() == 1)
                                f = TextFormatting.GRAY;
                            else if (u.getUpgradeTier() == 2)
                                f = TextFormatting.GOLD;
                            else
                                f = TextFormatting.AQUA;

                            probeInfo.text(f + StringHelper.localize(Lang.TOOLTIP_UPGRADE + e));
                        }
                    } else {
                        probeInfo.text(StringHelper.localize(Lang.WAILA_NO_UPGRADES));
                    }

                } else {
                    probeInfo.text(StringHelper.localize(Lang.WAILA_EXTRA_UPGRADE));
                }
            }
        }
    }

    public static class PluginTooltipInfo {

        public String displayName;
        public EnumMobFactoryTier tier;
        public int spawnTime;
        public int spawnTickRF;
        public int spawnRF;
        public int storedRF;
        public int totalRF;
        public boolean isRunning;
        public int maxMass;

        public PluginTooltipInfo(TileEntityMobFactory te) {

            displayName = te.getDisplayName();
            tier = te.getFactoryTier();
            spawnTime = te.getSpawnReq().getSpawnTime();
            spawnTickRF = te.getSpawnReq().getRfPerTick();
            spawnRF = te.getSpawnReq().getTotalRf();
            storedRF = te.getEnergyStored(EnumFacing.DOWN);
            totalRF = te.getMaxEnergyStored(EnumFacing.DOWN);
            isRunning = te.isRunning();

            maxMass = Settings.baseMobCount;
            UpgradeSetup upgradeSetup = te.getUpgradeSetup();
            if (upgradeSetup != null && upgradeSetup.hasMassUpgrade())
                maxMass = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();
        }

        private PluginTooltipInfo() { }

        public void toNBT(NBTTagCompound tag) {

            tag.setString("displayName", displayName);
            tag.setByte("tier", (byte)tier.ordinal());
            tag.setInteger("spawnTicks", spawnTime);
            tag.setInteger("spawnRf", spawnRF);
            tag.setInteger("rfPerTick", spawnTickRF);
            tag.setBoolean("running", isRunning);
            tag.setInteger("energy", storedRF);
            tag.setInteger("maxEnergy", totalRF);
            tag.setInteger("mobCount", maxMass);
        }

        public static PluginTooltipInfo fromNBT(NBTTagCompound tag) {

            PluginTooltipInfo info = new PluginTooltipInfo();

            info.displayName = tag.getString("displayName");
            info.tier = EnumMobFactoryTier.getTier(tag.getByte("tier"));
            info.spawnTime = tag.getInteger("spawnTicks");
            info.spawnRF = tag.getInteger("spawnRf");
            info.spawnTickRF = tag.getInteger("rfPerTick");
            info.isRunning = tag.getBoolean("running");
            info.storedRF = tag.getInteger("energy");
            info.totalRF = tag.getInteger("maxEnergy");
            info.maxMass = tag.getInteger("mobCount");
            return info;
        }
    }

}
