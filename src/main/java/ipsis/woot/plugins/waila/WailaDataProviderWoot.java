package ipsis.woot.plugins.waila;

import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.tileentity.TileEntityMobFarm;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
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

        if (accessor.getTileEntity() instanceof TileEntityMobFarm) {
            TileEntityMobFarm te = (TileEntityMobFarm)accessor.getTileEntity();

            if (!te.isFormed())
                return currenttip;

            /* Display the mob spawn, spawn rate, rf/t */
            currenttip.add(EnumChatFormatting.BLUE + te.getFactoryTier().toString());
            currenttip.add(EnumChatFormatting.BLUE + te.getMobName());
            if (te.getSpawnReq() != null) {
                currenttip.add(EnumChatFormatting.GREEN + Integer.toString(te.getSpawnReq().getSpawnTime()) + " ticks");
                currenttip.add(EnumChatFormatting.GREEN + Integer.toString(te.getSpawnReq().getRfPerTick()) + " RF/t");
            }

            for (SpawnerUpgrade u : te.getUpgradeList())
                currenttip.add(EnumChatFormatting.YELLOW + u.getUpgradeType().toString());

        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {

        registrar.registerBodyProvider(new WailaDataProviderWoot(), TileEntityMobFarm.class);
    }
}
