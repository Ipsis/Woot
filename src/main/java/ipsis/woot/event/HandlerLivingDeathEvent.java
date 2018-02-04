package ipsis.woot.event;

import ipsis.woot.item.ItemEnderShard;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDeathEvent {

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {

        World world = event.getEntity().getEntityWorld();

        // Only player kills
        if (world.isRemote || !(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        if (event.getEntityLiving() == null)
            return;

        EntityPlayer entityPlayer = (EntityPlayer)event.getSource().getTrueSource();
        EntityLivingBase entityLivingBase = event.getEntityLiving();

        // player on player kill would cause invalid cast for this method
        if (!(entityLivingBase instanceof EntityLiving))
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
