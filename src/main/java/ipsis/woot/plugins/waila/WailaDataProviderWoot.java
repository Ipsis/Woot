package ipsis.woot.plugins.waila;

import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
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

            EnumMobFactoryTier t = EnumMobFactoryTier.getTier(tag.getByte("tier"));

            currenttip.add(EnumChatFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_FACTORY_TIER),
                    (t == EnumMobFactoryTier.TIER_ONE ? "I" : t == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));
            currenttip.add(EnumChatFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_MOB),
                    tag.getString("displayName")));
            currenttip.add(EnumChatFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_RATE),
                    tag.getInteger("mobCount"), tag.getInteger("spawnTicks")));
            currenttip.add(EnumChatFormatting.GREEN + String.format(StringHelper.localize(Lang.WAILA_FACTORY_COST),
                    tag.getInteger("spawnRf"), tag.getInteger("rfPerTick")));
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
            int maxMass = Settings.baseMobCount;
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
