package ipsis.woot.plugins.waila;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.manager.UpgradeSetup;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class WailaDataProviderWoot implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        NBTTagCompound tag = accessor.getNBTData();
        if (tag.hasKey("displayName")) {

            EnumMobFactoryTier t = EnumMobFactoryTier.getTier(tag.getByte("tier"));

            currenttip.add(EnumChatFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_FACTORY_TIER),
                    (t == EnumMobFactoryTier.TIER_ONE ? "I" : t == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));
            currenttip.add(EnumChatFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB),
                    tag.getString("displayName")));
            currenttip.add(EnumChatFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RATE),
                    tag.getInteger("mobCount"), tag.getInteger("spawnTicks")));
            currenttip.add(EnumChatFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_COST),
                    tag.getInteger("spawnRf"), tag.getInteger("rfPerTick")));

            if (tag.hasKey("upgrades")) {
                byte[] a = tag.getByteArray("upgrades");
                for (int i = 0; i < a.length; i++) {
                    EnumSpawnerUpgrade e = EnumSpawnerUpgrade.getFromMetadata(a[i]);
                    SpawnerUpgrade u = UpgradeManager.getSpawnerUpgrade(e);
                    EnumChatFormatting f;
                    if (u.getUpgradeTier() == 1)
                        f = EnumChatFormatting.GRAY;
                    else if (u.getUpgradeTier() == 2)
                        f = EnumChatFormatting.GOLD;
                    else
                        f = EnumChatFormatting.AQUA;
                    currenttip.add(f + StringHelper.localize( Lang.TOOLTIP_UPGRADE + EnumSpawnerUpgrade.getFromMetadata(a[i])));
                }
            }
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {

        TileEntityMobFactory tile = (TileEntityMobFactory)te;
        if (tile.isFormed()) {
            tag.setString("displayName", tile.getDisplayName());
            tag.setByte("tier", (byte)tile.getFactoryTier().ordinal());
            tag.setInteger("spawnTicks", tile.getSpawnReq().getSpawnTime());
            tag.setInteger("spawnRf", tile.getSpawnReq().getTotalRf());
            tag.setInteger("rfPerTick", tile.getSpawnReq().getRfPerTick());

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

        registrar.registerBodyProvider(new WailaDataProviderWoot(), TileEntityMobFactory.class);
        registrar.registerNBTProvider(new WailaDataProviderWoot(), TileEntityMobFactory.class);
    }
}
