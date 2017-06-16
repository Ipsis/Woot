package ipsis.woot.block;

import ipsis.Woot;
import ipsis.woot.manager.*;
import ipsis.woot.manager.loot.FullDropInfo;
import ipsis.woot.manager.loot.LootTableManager;
import ipsis.woot.manager.spawnreq.ExtraSpawnReq;
import ipsis.woot.manager.spawnreq.SpawnReqManager;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.top.ITOPInfoProvider;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.TileEntityMobFarm;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
//import mcjty.theoneprobe.api.IProbeHitData;
//import mcjty.theoneprobe.api.IProbeInfo;
//import mcjty.theoneprobe.api.ProbeMode;
//import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
//import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
//import mcjty.theoneprobe.config.Config;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
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
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFarm();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        EnumFacing f = placer.getHorizontalFacing().getOpposite();
        worldIn.setBlockState(pos, state.withProperty(FACING, f), 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (!heldItem.isEmpty())
            return false;

        if (worldIn.isRemote)
            return true;

        if (worldIn.getTileEntity(pos) instanceof  TileEntityMobFactory) {

            TileEntityMobFactory te = (TileEntityMobFactory)worldIn.getTileEntity(pos);
            if (te.isFormed()) {

                List<String> out = new ArrayList<String>();
                out.add(TextFormatting.BLUE + String.format( te.getFactoryTier().getTranslated(Lang.WAILA_FACTORY_TIER)));
                out.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB), te.getMobDisplayName()));

                int maxMass = Settings.baseMobCount;
                UpgradeSetup upgradeSetup = te.getUpgradeSetup();
                if (upgradeSetup != null) {
                    if (upgradeSetup.hasMassUpgrade())
                        maxMass = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getUpgradeType().getParam();
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
                            if (u.getUpgradeType().getTier() == 1)
                                f = TextFormatting.GRAY;
                            else if (u.getUpgradeType().getTier() == 2)
                                f = TextFormatting.GOLD;
                            else
                                f = TextFormatting.AQUA;
                            out.add(f + StringHelper.localize(Lang.TOOLTIP_UPGRADE + upgrade));
                        }
                    }
                }

                List<ItemStack> requiredItems = Woot.SPAWN_REQ_MANAGER.getItems(te.getMobName(), upgradeSetup);
                FluidStack fluidStack = Woot.SPAWN_REQ_MANAGER.getFluid(te.getMobName(), upgradeSetup);
                if (!requiredItems.isEmpty()) {
                    for (ItemStack itemStack : requiredItems)
                        out.add(TextFormatting.GOLD + String.format("%d of %s", itemStack.getCount(), itemStack.getDisplayName()));
                } else if (fluidStack != null) {
                    out.add(TextFormatting.GOLD + String.format("%d mb of %s", fluidStack.amount, fluidStack.getLocalizedName()));
                }

                for (String s : out)
                    playerIn.sendStatusMessage(new TextComponentString(s), false);
            } else {
                if (!Woot.mobRegistry.isPrismValid(te.getMobName()))
                    playerIn.sendStatusMessage(new TextComponentString(String.format(
                            StringHelper.localize(Lang.CHAT_MOB_INVALID), te.getMobDisplayName(), te.getMobName())), false);
                else
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

        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "I", Settings.tierIRFtick));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "II", Settings.tierIIRFtick));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "III", Settings.tierIIIRFtick));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "IV", Settings.tierIVRFtick));
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

//    @Override
//    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
//
//        TileEntity te = world.getTileEntity(data.getPos());
//        if (te instanceof TileEntityMobFactory) {
//            TileEntityMobFactory factoryTE = (TileEntityMobFactory)te;
//
//
//            if (factoryTE.isFormed()) {
//
//                PluginTooltipInfo info = new PluginTooltipInfo(factoryTE);
//
//                /**
//                 * Progress
//                 * Configuration
//                 */
//
//                /**
//                 * Power & Redstone state
//                 */
//                if (Config.getDefaultConfig().getRFMode() == 1) {
//                    probeInfo.progress(info.storedRF, info.totalRF,
//                            probeInfo.defaultProgressStyle().
//                                    suffix("RF").
//                                    filledColor(Config.rfbarFilledColor).
//                                    alternateFilledColor(Config.rfbarAlternateFilledColor).
//                                    borderColor(Config.rfbarBorderColor).
//                                    numberFormat(Config.rfFormat));
//                } else {
//                    probeInfo.text(TextFormatting.RED + "RF: " + ElementProgress.format(info.storedRF, Config.rfFormat, "RF"));
//                }
//
//                probeInfo.horizontal().item(new ItemStack(Items.REDSTONE), probeInfo.defaultItemStyle().width(14).height(14)).text("State: " + (info.isRunning ? "On" : "Off"));
//
//                /**
//                 * Progress
//                 */
//                if (info.isRunning) {
//                    int percentage = (int)((100.0F / (float)info.spawnRF) * (float)info.consumedRF);
//                    TextFormatting form = TextFormatting.GREEN;
//                    if (percentage > 100)
//                        form = TextFormatting.RED;
//
//                    percentage = Math.min(percentage, 100);
//                    probeInfo.horizontal().item(new ItemStack(Items.COMPASS), probeInfo.defaultItemStyle().width(14).height(14)).text(form + "Progress: " + percentage + "%");
//                }
//
//                /**
//                 * Recipe
//                 */
//                probeInfo.text(TextFormatting.BLUE + String.format( info.tier.getTranslated(Lang.WAILA_FACTORY_TIER)));
//                probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB), info.displayName));
//                probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RATE), info.maxMass, info.spawnTime));
//                probeInfo.text(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_COST), info.spawnRF, info.spawnTickRF));
//
//                if (mode == ProbeMode.EXTENDED) {
//
//                    if (factoryTE.getUpgradeSetup() != null) {
//
//                        for (EnumSpawnerUpgrade e : factoryTE.getUpgradeSetup().getUpgradeList()) {
//
//                            TextFormatting f;
//                            SpawnerUpgrade u = UpgradeManager.getSpawnerUpgrade(e);
//
//                            if (u.getUpgradeType().getTier() == 1)
//                                f = TextFormatting.GRAY;
//                            else if (u.getUpgradeType().getTier() == 2)
//                                f = TextFormatting.GOLD;
//                            else
//                                f = TextFormatting.AQUA;
//
//                            probeInfo.text(f + StringHelper.localize(Lang.TOOLTIP_UPGRADE + e));
//                        }
//
//                        // Show the drop info
//                        EnumEnchantKey key = factoryTE.getUpgradeSetup().getEnchantKey();
//                        List<FullDropInfo> drops = Woot.LOOT_TABLE_MANAGER.getFullDropInfo(factoryTE.getMobName(), key);
//                        if (drops != null) {
//
//                            IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(0xffffffff).spacing(0));
//                            IProbeInfo horizontal = null;
//                            int rows = 0;
//                            int idx = 0;
//                            for (FullDropInfo drop : drops) {
//                                if (Woot.LOOT_TABLE_MANAGER.isBlacklisted(drop.getItemStack()))
//                                    continue;
//
//                                if (drop.getItemStack().getItem() == Item.getItemFromBlock(Blocks.BEDROCK))
//                                    continue;
//
//                                if (idx % 10 == 0) {
//                                    horizontal = vertical.horizontal(probeInfo.defaultLayoutStyle().spacing(0));
//                                    rows++;
//                                    if (rows > 4)
//                                        break;
//                                }
//
//                                // TOP allows the itemstack to be > 64
//                                // So use that renderer to display the chance of getting a drop.
//                                ItemStack fakeStack = drop.getItemStack().copy();
//                                fakeStack.setCount((int)Math.ceil(drop.getDropChance()));
//                                horizontal.item(fakeStack);
//                                idx++;
//                            }
//                        }
//
//                    } else {
//                        probeInfo.text(StringHelper.localize(Lang.WAILA_NO_UPGRADES));
//                    }
//
//
//                } else {
//                    probeInfo.text(StringHelper.localize(Lang.WAILA_EXTRA_UPGRADE));
//                }
//            }
//        }
//    }

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
        public boolean strict;
        public int consumedRF;

        public PluginTooltipInfo(TileEntityMobFactory te) {

            displayName = te.getMobDisplayName();
            tier = te.getFactoryTier();
            spawnTime = te.getSpawnReq().getSpawnTime();
            spawnTickRF = te.getSpawnReq().getRfPerTick();
            spawnRF = te.getSpawnReq().getTotalRf();
            storedRF = te.getEnergyManager().getEnergyStored();
            totalRF = te.getEnergyManager().getMaxEnergyStored();
            isRunning = te.isRunning();
            consumedRF = te.getConsumedRf();

            maxMass = Settings.baseMobCount;
            UpgradeSetup upgradeSetup = te.getUpgradeSetup();
            if (upgradeSetup != null && upgradeSetup.hasMassUpgrade())
                maxMass = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getUpgradeType().getParam();
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
            tag.setInteger("consumedEnergy", consumedRF);
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
            info.consumedRF = tag.getInteger("consumedEnergy");
            return info;
        }
    }

}
