package ipsis.woot.setup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.advancements.Advancements;
import ipsis.woot.commands.ModCommands;
import ipsis.woot.mod.ModFiles;
import ipsis.woot.modules.anvil.AnvilRecipes;
import ipsis.woot.modules.factory.multiblock.MultiBlockTracker;
import ipsis.woot.modules.fluidconvertor.FluidConvertorRecipes;
import ipsis.woot.modules.infuser.InfuserRecipes;
import ipsis.woot.simulator.CustomDropsLoader;
import ipsis.woot.simulator.MobSimulatorSetup;
import ipsis.woot.modules.squeezer.SqueezerRecipes;
import ipsis.woot.modules.factory.items.MobShardItem;
import ipsis.woot.simulator.spawning.FakePlayerPool;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.tartarus.TartarusChunkGenerator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.ItemEntityHelper;
import ipsis.woot.util.helper.SerializationHelper;
import ipsis.woot.util.oss.WootFakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ForgeEventHandlers {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent event) {

        /**
         * Entity->LivingEntity->MobEntity
         */

        if (!(event.getEntity() instanceof MobEntity))
            return;

        MobEntity mobEntity = (MobEntity)event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        if (damageSource == null)
            return;

        if (!FakePlayerPool.isFakePlayer(damageSource.getTrueSource()))
            return;

        // Cancel our fake spawns
        event.setCanceled(true);

        List<ItemStack> drops = ItemEntityHelper.convertToItemStacks(event.getDrops());
        FakeMobKey fakeMobKey = new FakeMobKey(new FakeMob(mobEntity), event.getLootingLevel());
        if (fakeMobKey.getMob().isValid()) {
            ipsis.woot.simulator.MobSimulator.getInstance().learnSimulatedDrops(fakeMobKey, drops);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onLivingDeathEvent(LivingDeathEvent event) {

        // Only player kills
        if (!(event.getSource().getTrueSource() instanceof PlayerEntity))
            return;

        if (event.getEntityLiving() == null)
            return;

        PlayerEntity killer = (PlayerEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();

        if (ignoreDeathEvent(event.getEntity())) {
            Woot.setup.getLogger().debug("onLivingDeathEvent: duplicate {} {}",
                    event.getEntity(), event.getEntity().getCachedUniqueIdString());
            return;
        }

        // Ignore fakeplayer
        if (killer instanceof FakePlayer)
            return;

        // Ignore player killing player
        if (victim instanceof PlayerEntity)
            return;

        if (!(victim instanceof MobEntity))
            return;

        FakeMob fakeMob = new FakeMob((MobEntity)victim);
        if (!fakeMob.isValid())
            return;

        MobShardItem.handleKill(killer, fakeMob);
    }

    private static final long MULTI_BLOCK_TRACKER_DELAY = 20;
    private static class TickTrack {
        public World world;
        public long lastWorldTick;
        public TickTrack(World world) {
            this.world = world;
            this.lastWorldTick = 0;
        }

        public boolean tick(long gameTime) {
            if (gameTime > lastWorldTick + MULTI_BLOCK_TRACKER_DELAY) {
                lastWorldTick = gameTime;
                return true;
            }
            return false;
        }
    }
    private static List<TickTrack> tickTracks = new ArrayList<>();
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        TickTrack currTick = null;
        for (TickTrack tickTrack : tickTracks) {
            if (tickTrack.world == event.world) {
                currTick = tickTrack;
                break;
            }
        }

        if (currTick == null) {
            currTick = new TickTrack(event.world);
            tickTracks.add(currTick);
        }

        if (event.world.getDimensionKey().equals(MobSimulatorSetup.TARTARUS)) {
            MobSimulator.getInstance().tick(event.world);
        } else {
            if (currTick.tick(event.world.getGameTime()))
                MultiBlockTracker.get().run(event.world);
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        Woot.setup.getLogger().info("onFileChange");
    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        Woot.setup.getLogger().info("onServerStarting");
        SqueezerRecipes.load(event.getServer().getRecipeManager());
        AnvilRecipes.load(event.getServer().getRecipeManager());
        InfuserRecipes.load(event.getServer().getRecipeManager());
        FluidConvertorRecipes.load(event.getServer().getRecipeManager());
        CustomDropsLoader.load(event.getServer().getRecipeManager());

        for (ServerWorld world : event.getServer().getWorlds())
            Woot.setup.getLogger().debug("onServerStarting: world {}", world.getDimensionKey());

        ServerWorld serverWorld = event.getServer().getWorld(MobSimulatorSetup.TARTARUS);
        if (serverWorld == null) {
            Woot.setup.getLogger().error("onServerStarting: tartarus not found");
        } else {
            Woot.setup.getLogger().info("onServerStarting: force load Tartarus Cells");
            serverWorld.forceChunk(TartarusChunkGenerator.WORK_CHUNK_X, TartarusChunkGenerator.WORK_CHUNK_Z, true);
        }
    }

    @SubscribeEvent
    public void onLivingExperienceDrop(final LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof WootFakePlayer) {
            // This is not a real kill, so dont spawn the XP
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRecipesUpdatedEvent(final RecipesUpdatedEvent event) {
        Woot.setup.getLogger().info("onRecipesUpdatedEvent");
        SqueezerRecipes.load(event.getRecipeManager());
        AnvilRecipes.load(event.getRecipeManager());
        InfuserRecipes.load(event.getRecipeManager());
        FluidConvertorRecipes.load(event.getRecipeManager());
    }

    @SubscribeEvent
    public void onRegisterCommandsEvent(final RegisterCommandsEvent event) {
        Woot.setup.getLogger().info("onRegisterCommandsEvent");
        ModCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStop(final FMLServerStoppingEvent event) {
        Woot.setup.getLogger().info("onServerStop");
        JsonObject jsonObject = MobSimulator.getInstance().toJson();
        File dropFile = ModFiles.INSTANCE.getLootFile();
        Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        SerializationHelper.writeJsonFile(dropFile, GSON.toJson(jsonObject));
    }

    /**
     * Death cache
     * Some entities like the EnderDragon generate multiple death events
     * so we cache the last X here and ignore any duplicates
     */
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
}
