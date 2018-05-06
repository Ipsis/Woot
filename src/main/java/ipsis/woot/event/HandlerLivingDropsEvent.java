package ipsis.woot.event;

import ipsis.Woot;
import ipsis.woot.enchantment.EnchantmentHeadhunter;
import ipsis.woot.util.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDropsEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent e) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.LOOT_EVENTS, "onLivingDropsEvent", e.getEntity() + "/" + e.getSource());

        if (!(e.getEntity() instanceof EntityLiving))
            return;

        DamageSource damageSource = e.getSource();
        if (damageSource == null)
            return;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.LOOT_EVENTS, "onLivingDropsEvent", damageSource.getTrueSource());
        if (!FakePlayerPool.isOurFakePlayer(damageSource.getTrueSource())) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.LOOT_EVENTS, "onLivingDropsEvent", "non-factory kill:");
            EnchantmentHeadhunter.handleLivingDrops(e);
            return;
        }

        // Cancel our fake spawns
        e.setCanceled(true);

        WootMobName wootMobName = WootMobNameBuilder.create((EntityLiving)e.getEntity());
        if (wootMobName.isValid()) {

            EnumEnchantKey key = EnumEnchantKey.getEnchantKey(e.getLootingLevel());
            Woot.lootRepository.learn(wootMobName, key, e.getDrops(), true);

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.LOOT_EVENTS, "onLivingDropsEvent", wootMobName + " " + key + " " + e.getDrops());
        } else {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.LOOT_EVENTS, "onLivingDropsEvent", "invalid mob " + e.getEntity());
        }
    }
}
