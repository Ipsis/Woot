package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.simulation.FakePlayerPool;
import ipsis.woot.simulation.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.ItemEntityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.util.List;

public class ModEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent event) {

        Woot.LOGGER.info("onLivingDropsEvent {}", event);

        if (!(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity livingEntity = (LivingEntity)event.getEntity();
        DamageSource damageSource = event.getSource();
        if (damageSource == null)
            return;

        if (!FakePlayerPool.isFakePlayer(damageSource.getTrueSource()))
            return;

        // Cancel our fake spawns
        event.setCanceled(true);

        Woot.LOGGER.info("onLivingDropsEvent fake kill {}", event.getDrops());
        List<ItemStack> drops = ItemEntityHelper.convertToItemStacks(event.getDrops());
        FakeMobKey fakeMobKey = new FakeMobKey(new FakeMob(livingEntity.getEntityString()), event.getLootingLevel());
        if (fakeMobKey.getMob().isValid())
            DropRegistry.get().learn(fakeMobKey, drops);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        Dimension dimension = event.world.getDimension();
        if (dimension.getType() != ModDimensions.tartarusDimensionType)
            return;

        MobSimulator.get().tick(event.world);
    }
}
