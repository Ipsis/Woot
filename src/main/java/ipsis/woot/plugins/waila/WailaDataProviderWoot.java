package ipsis.woot.plugins.waila;

import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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

            currenttip.add(EnumChatFormatting.BLUE + I18n.format("waila." + Reference.MOD_ID + ":factory.tier") +
                    ": " + EnumMobFactoryTier.getTier(tag.getByte("tier")));
            currenttip.add(EnumChatFormatting.BLUE + I18n.format("waila." + Reference.MOD_ID + ":factory.mob") +
                    ": " + tag.getString("displayName"));
            currenttip.add(EnumChatFormatting.GREEN + I18n.format("waila." + Reference.MOD_ID + ":factory.mobCount") +
                    ": " + tag.getInteger("mobCount"));
            currenttip.add(EnumChatFormatting.GREEN + I18n.format("waila." + Reference.MOD_ID + ":factory.spawnTime") +
                    ": " + tag.getInteger("spawnTicks") + " ticks");
            currenttip.add(EnumChatFormatting.GREEN + I18n.format("waila." + Reference.MOD_ID + ":factory.totalRf") +
                    ": " + tag.getInteger("spawnRf") + " RF");
            currenttip.add(EnumChatFormatting.GREEN + I18n.format("waila." + Reference.MOD_ID + ":factory.tickRf") +
                    ": " + tag.getInteger("rfPerTick") + " RF/tick");
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
            tag.setString("displayName", tile.getMobName());
            tag.setByte("tier", (byte)tile.getFactoryTier().ordinal());
            tag.setInteger("spawnTicks", tile.getSpawnReq().getSpawnTime());
            tag.setInteger("spawnRf", tile.getSpawnReq().getTotalRf());
            tag.setInteger("rfPerTick", tile.getSpawnReq().getRfPerTick());

            SpawnerUpgrade upgradeMass = UpgradeManager.getMassUpgrade(tile.getUpgradeList());
            int maxMass = Settings.massBaseMobs;
            if (upgradeMass != null)
                maxMass = upgradeMass.getMass();
            tag.setInteger("mobCount", maxMass);
        }
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {

        registrar.registerBodyProvider(new WailaDataProviderWoot(), TileEntityMobFactory.class);
        registrar.registerNBTProvider(new WailaDataProviderWoot(), TileEntityMobFactory.class);
    }
}
