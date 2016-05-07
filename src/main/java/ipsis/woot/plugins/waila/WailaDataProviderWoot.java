package ipsis.woot.plugins.waila;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.manager.UpgradeSetup;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.TileEntityMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.StringHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class WailaDataProviderWoot implements IWailaDataProvider {
    private static WailaDataProviderWoot INSTANCE = new WailaDataProviderWoot();

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {

        Block block = accessor.getBlock();

        if (block == ModBlocks.blockStructure) {
            int meta = accessor.getMetadata();
            return new ItemStack(ModBlocks.blockStructure, 1, meta);
        } else if (block == ModBlocks.blockUpgrade) {
            int meta = accessor.getMetadata();
            return new ItemStack(ModBlocks.blockUpgrade, 1, meta);
        }
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        if (accessor.getTileEntity() instanceof TileEntityMobFactory)
            return getWailaBodyFactory(itemStack, currenttip, accessor, config);
        else if (accessor.getTileEntity() instanceof TileEntityMobFactoryController)
            return getWailaBodyController(itemStack, currenttip, accessor, config);

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {

        if (te instanceof TileEntityMobFactory)
            return getNBTDataFactory(player, te, tag, world, pos);
        else if (te instanceof TileEntityMobFactoryController)
            return getNBTDataController(player, te, tag, world, pos);

        return tag;
    }

    public List<String> getWailaBodyController(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        NBTTagCompound tag = accessor.getNBTData();
        if (tag.hasKey("displayName") && tag.hasKey("xpCost")) {
            EnumMobFactoryTier t = MobFactoryMultiblockLogic.getTier(tag.getInteger("xpCost"));
            currenttip.add(TextFormatting.GREEN + String.format("%s : %s XP", tag.getString("displayName"), tag.getInteger("xpCost")));
            currenttip.add(TextFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_CONTROLLER_TIER),
                            (t == EnumMobFactoryTier.TIER_ONE ? "I" : t == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));

        }
        return currenttip;
    }

    public NBTTagCompound getNBTDataController(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {

        TileEntityMobFactoryController controller = (TileEntityMobFactoryController)te;
        String displayName = controller.getDisplayName();
        if (!displayName.equals(""))
            tag.setString("displayName", displayName);
        tag.setInteger("xpCost", controller.getXpValue());
        return tag;
    }

    public List<String> getWailaBodyFactory(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        NBTTagCompound tag = accessor.getNBTData();
        if (tag.hasKey("displayName")) {

            EnumMobFactoryTier t = EnumMobFactoryTier.getTier(tag.getByte("tier"));

            currenttip.add(TextFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_FACTORY_TIER),
                    (t == EnumMobFactoryTier.TIER_ONE ? "I" : t == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));
            currenttip.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB),
                    tag.getString("displayName")));
            currenttip.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RATE),
                    tag.getInteger("mobCount"), tag.getInteger("spawnTicks")));
            currenttip.add(TextFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_COST),
                    tag.getInteger("spawnRf"), tag.getInteger("rfPerTick")));
            boolean running = tag.getBoolean("running");
            currenttip.add(TextFormatting.GREEN + String.format(
                    running ? StringHelper.localize(Lang.WAILA_FACTORY_RUNNING) : StringHelper.localize(Lang.WAILA_FACTORY_STOPPED)));

            int energy    = accessor.getNBTInteger(accessor.getNBTData(), "Energy");
            int maxEnergy = accessor.getNBTInteger(accessor.getNBTData(), "MaxStorage");
            currenttip.add(TextFormatting.RED + String.format("%d / %d RF", energy, maxEnergy));

            if (tag.hasKey("upgrades")) {
                byte[] a = tag.getByteArray("upgrades");
                for (int i = 0; i < a.length; i++) {
                    EnumSpawnerUpgrade e = EnumSpawnerUpgrade.getFromMetadata(a[i]);
                    SpawnerUpgrade u = UpgradeManager.getSpawnerUpgrade(e);
                    TextFormatting f;
                    if (u.getUpgradeTier() == 1)
                        f = TextFormatting.GRAY;
                    else if (u.getUpgradeTier() == 2)
                        f = TextFormatting.GOLD;
                    else
                        f = TextFormatting.AQUA;
                    currenttip.add(f + StringHelper.localize( Lang.TOOLTIP_UPGRADE + EnumSpawnerUpgrade.getFromMetadata(a[i])));
                }
            }
        }
        return currenttip;
    }

    public NBTTagCompound getNBTDataFactory(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {

        TileEntityMobFactory tile = (TileEntityMobFactory)te;
        if (tile.isFormed()) {
            tag.setString("displayName", tile.getDisplayName());
            tag.setByte("tier", (byte)tile.getFactoryTier().ordinal());
            tag.setInteger("spawnTicks", tile.getSpawnReq().getSpawnTime());
            tag.setInteger("spawnRf", tile.getSpawnReq().getTotalRf());
            tag.setInteger("rfPerTick", tile.getSpawnReq().getRfPerTick());
            tag.setBoolean("running", tile.isRunning());

            tag.setInteger("Energy",     tile.getEnergyStored(EnumFacing.DOWN));
            tag.setInteger("MaxStorage", tile.getMaxEnergyStored(EnumFacing.DOWN));

            int maxMass = Settings.baseMobCount;
            UpgradeSetup upgradeSetup = tile.getUpgradeSetup();
            if (upgradeSetup != null) {
                if (upgradeSetup.hasMassUpgrade())
                    maxMass = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();
            }
            tag.setInteger("mobCount", maxMass);

            if (upgradeSetup != null) {
                List<EnumSpawnerUpgrade> upgradeList = tile.getUpgradeSetup().getUpgradeList();
                if (!upgradeList.isEmpty()) {
                    byte[] u = new byte[upgradeList.size()];
                    int i = 0;
                    for (EnumSpawnerUpgrade upgrade : upgradeList) {
                        u[i] = (byte)upgrade.ordinal();
                        i++;
                    }

                    tag.setTag("upgrades", new NBTTagByteArray(u));
                }
            }
        }
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {

        registrar.registerStackProvider(INSTANCE, TileEntityMobFactoryStructure.class);
        registrar.registerStackProvider(INSTANCE, TileEntityMobFactoryUpgrade.class);
        registrar.registerBodyProvider(INSTANCE, TileEntityMobFactory.class);
        registrar.registerBodyProvider(INSTANCE, TileEntityMobFactoryController.class);
        registrar.registerNBTProvider(INSTANCE, TileEntityMobFactory.class);
        registrar.registerNBTProvider(INSTANCE, TileEntityMobFactoryController.class);
    }
}
