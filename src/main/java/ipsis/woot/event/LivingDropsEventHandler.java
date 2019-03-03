package ipsis.woot.event;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LivingDropsEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent event) {

        if (!(event.getEntity() instanceof EntityLiving))
            return;

        EntityLiving entityLiving = (EntityLiving)event.getEntity();
        DamageSource damageSource = event.getSource();
        if (damageSource == null)
            return;

        // We only care about factory kills

    }
}
