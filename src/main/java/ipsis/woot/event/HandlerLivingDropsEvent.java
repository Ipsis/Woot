package ipsis.woot.event;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.manager.SpawnerManager;
import ipsis.woot.tileentity.TileEntityFactory;
import ipsis.woot.util.DamageSourceWoot;
import ipsis.woot.util.MobUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDropsEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent e) {

        DamageSourceWoot damageSourceWoot = DamageSourceWoot.getDamageSource(e.source.damageType);
        if (damageSourceWoot != null) {

            /**
             *  Killed in one of our spawners, but we dont care which one.
             *  We only store the drop information.
             */
            String mobID = MobUtil.getMobName((EntityLiving)e.entity);
            if (!Woot.spawnerManager2.isFull(mobID, damageSourceWoot.getEnchantKey())) {
                Woot.spawnerManager2.addDrops(mobID, damageSourceWoot.getEnchantKey(), e.drops);
            }
            /*
            if (!SpawnerManager.isFull(mobID, damageSourceWoot.getEnchantKey())) {
                StringBuilder sb = new StringBuilder();
                for (EntityItem entityItem : e.drops) {
                    sb.append(" " );
                    sb.append(entityItem.getName());
                }
                LogHelper.info("Adding drop " + sb);
                SpawnerManager.addSpawnerDrops(mobID, damageSourceWoot.getEnchantKey(), e.drops);
            } else {
                LogHelper.info("Spawner generating entities with full manager");
            } */

            e.setCanceled(true);
        }
    }
}
