package ipsis.woot.mod;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.factory.multiblock.MultiBlockTracker;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.server.command.WootCommand;
import ipsis.woot.simulation.FakePlayerPool;
import ipsis.woot.simulation.MobSimulator;
import ipsis.woot.simulation.Tartarus;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.ItemEntityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.util.List;

public class ModEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent event) {

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
        if (dimension.getType() != ModDimensions.tartarusDimensionType) {
            // TODO make this every 20 ticks
            MultiBlockTracker.get().run(event.world);
        } else {
            MobSimulator.get().tick(event.world);
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        new WootCommand(event.getCommandDispatcher());

        // Force load the simulation world
        Tartarus.get().setWorld(DimensionManager.getWorld(event.getServer(), ModDimensions.tartarusDimensionType, true, true));
    }

    @SubscribeEvent
    public void onServerStop(final FMLServerStoppingEvent event) {

        JsonObject jsonObject = DropRegistry.get().toJson();

    }
}
