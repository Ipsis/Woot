package ipsis.woot.event;

import ipsis.Woot;
import ipsis.woot.util.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LivingDropsEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent event) {

        if (Woot.debugging.isEnabled(Debug.Group.EVENT))
            Woot.debugging.trace(Debug.Group.EVENT, "onLivingDropsEvent {} {} {}", event.getEntity(), event.getSource(), event.getLootingLevel());

        if (!(event.getEntity() instanceof EntityLiving))
            return;

        EntityLiving entityLiving = (EntityLiving)event.getEntity();
        DamageSource damageSource = event.getSource();
        if (damageSource == null)
            return;

        if (!FakePlayerPool.isWootFakePlayer(damageSource.getTrueSource())) {
            // TODO handle non-factory kills
            return;
        }

        // Cancel our fake spawns
        event.setCanceled(true);

        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromEntity(entityLiving);
        if (fakeMobKey.isValid()) {
            int looting = event.getLootingLevel();
            looting = MiscUtils.clampLooting(looting);

            // TODO do we need to flatten on livingdropsevent
            List<ItemStack> drops = new ArrayList<>();
            for (EntityItem entityItem : event.getDrops())
                drops.add(entityItem.getItem().copy());
            Woot.DROP_MANAGER.learn(fakeMobKey, looting, drops);
        }
    }
}
