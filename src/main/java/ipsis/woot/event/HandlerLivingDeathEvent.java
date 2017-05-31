package ipsis.woot.event;

import ipsis.woot.item.ItemPrism;
import ipsis.woot.item.ItemPrism2;
import ipsis.woot.util.WootMobName;
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
        if (world.isRemote || !(event.getSource().getEntity() instanceof EntityPlayer))
            return;

        if (!(event.getEntityLiving() instanceof EntityLivingBase))
            return;

        EntityPlayer entityPlayer = (EntityPlayer)event.getSource().getEntity();
        EntityLivingBase entityLivingBase = event.getEntityLiving();

        WootMobName wootMobName = WootMobName.createFromEntity(entityLivingBase);
        if (wootMobName == null)
            return;

        ItemStack itemStack = findPrism(entityPlayer, wootMobName);
        if (!itemStack.isEmpty())
            ItemPrism2.incrementDeaths(itemStack, 1);
    }

    private static ItemStack findPrism(EntityPlayer entityPlayer, WootMobName wootMobName) {

        ItemStack foundStack = ItemStack.EMPTY;

        /**
         * Hotbar only
         */
        for (int i = 0; i <= 8; i++) {

            ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

            if (itemStack.isEmpty())
                continue;;

            if (!ItemPrism2.isMob(itemStack, wootMobName))
                continue;;

            foundStack = itemStack;
            break;
        }

        return foundStack;
    }
}
