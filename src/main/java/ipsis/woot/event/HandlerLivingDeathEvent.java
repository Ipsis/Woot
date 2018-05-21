package ipsis.woot.event;

import ipsis.woot.item.ItemEnderShard;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HandlerLivingDeathEvent {

    private final int MAX_UUID_CACHE_SIZE = 10;
    private List<String> uuidList = new ArrayList<>();

    private boolean ignoreDeathEvent(Entity entity) {

        String uuid = entity.getCachedUniqueIdString();
        if (uuidList.contains(uuid))
            return true;

        uuidList.add(uuid);
        if (uuidList.size() > MAX_UUID_CACHE_SIZE)
            uuidList.remove(0);

        return false;
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {

        World world = event.getEntity().getEntityWorld();
        if (world.isRemote)
            return;

        // Only player kills
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        if (event.getEntityLiving() == null)
            return;

        EntityPlayer entityPlayer = (EntityPlayer)event.getSource().getTrueSource();
        EntityLivingBase entityLivingBase = event.getEntityLiving();

        // player on player kill would cause invalid cast for this method
        if (!(entityLivingBase instanceof EntityLiving))
            return;

        // Filter out possible extra death events from things like the EnderDragon
        if (ignoreDeathEvent(event.getEntity()))
            return;

        WootMobName wootMobName = WootMobNameBuilder.create((EntityLiving)entityLivingBase);
        if (!wootMobName.isValid())
            return;

        ItemStack itemStack = findPrism(entityPlayer, wootMobName);
        if (!itemStack.isEmpty())
            ItemEnderShard.incrementDeaths(itemStack, 1);
    }

    private static ItemStack findPrism(EntityPlayer entityPlayer, WootMobName wootMobName) {

        ItemStack foundStack = ItemStack.EMPTY;

        /**
         * Hotbar only
         */
        for (int i = 0; i <= 8; i++) {

            ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

            if (itemStack.isEmpty())
                continue;

            if (!ItemEnderShard.isMob(itemStack, wootMobName))
                continue;

            foundStack = itemStack;
            break;
        }

        return foundStack;
    }
}
