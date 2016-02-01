package ipsis.woot.event;

import ipsis.oss.LogHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class HandlerLivingDropsEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent e) {

        LogHelper.info(e);
        if (e.source.damageType == "inSpawner") {
//        if (e.source == DamageSourceWoot.inSpawner) {
            LogHelper.info("Killed by spawner @ " + e.entity.getPosition());
            LogHelper.info("Drops @ " + e.drops.size());
            for (EntityItem entityItem : e.drops)
                LogHelper.info(entityItem.getName());

            try {
                /* Dev only version - change to access tranformer */
                Field f = ReflectionHelper.findField(EntityLiving.class, "experienceValue");
                LogHelper.info("Experience " + f.getInt(e.entityLiving));
            } catch (ReflectionHelper.UnableToFindFieldException ex) {
                LogHelper.info("No such field");
            } catch (IllegalAccessException ex) {
                LogHelper.info("No access");
            }

            e.setCanceled(true);
        }
    }
}
