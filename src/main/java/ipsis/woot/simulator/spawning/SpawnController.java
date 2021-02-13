package ipsis.woot.simulator.spawning;

import ipsis.woot.Woot;
import ipsis.woot.config.Config;
import ipsis.woot.config.ConfigOverride;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SpawnController {

    public static SpawnController get() { return INSTANCE; }
    static SpawnController INSTANCE;
    static { INSTANCE = new SpawnController(); }

    public void spawnKill(@Nonnull FakeMobKey fakeMobKey, @Nonnull ServerWorld world, @Nonnull BlockPos spawnPos) {

        if (!fakeMobKey.getMob().isValid())
            return;

        FakePlayer fakePlayer = FakePlayerPool.getFakePlayer(world, fakeMobKey.getLooting());
        if (fakePlayer == null)
            return;

        Entity entity = createEntity(fakeMobKey.getMob(), world, spawnPos);
        if (entity == null || !(entity instanceof MobEntity))
            return;

        MobEntity mobEntity = (MobEntity)entity;

        mobEntity.onInitialSpawn(world,
                world.getDifficultyForLocation(new BlockPos(entity.getPosition())),
                SpawnReason.SPAWNER,
                null, null);

        mobEntity.recentlyHit = 100;
        mobEntity.attackingPlayer = fakePlayer;

        CustomSpawnController.get().apply(mobEntity, fakeMobKey.getMob(), world);
        mobEntity.onDeath(DamageSource.causePlayerDamage(fakePlayer));
    }

    private @Nullable Entity createEntity(@Nonnull FakeMob fakeMob, @Nonnull World world, @Nonnull BlockPos pos) {

        ResourceLocation rl = fakeMob.getResourceLocation();
        if (!ForgeRegistries.ENTITIES.containsKey(rl)) {
            Woot.setup.getLogger().debug("createEntity: {} not in entity list", rl);
            return null;
        }

        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("id", rl.toString());
        Entity entity = EntityType.loadEntityAndExecute(nbt, world, (xc) -> {
            xc.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), xc.rotationYaw, xc.rotationPitch);
            return xc;
        });

        return entity;
    }

    private static final int UNKNOWN_MOB_HEALTH = 100;
    public int getMobHealth(@Nonnull FakeMob fakeMob, @Nonnull World world) {
        if (world.isRemote)
            return UNKNOWN_MOB_HEALTH;

        if (!fakeMob.isValid())
            return UNKNOWN_MOB_HEALTH;

        if (!isCached(fakeMob)) {
            if (!updateCache(fakeMob, world))
                return UNKNOWN_MOB_HEALTH;
        }

        // Configuration value has priority
        if (Config.OVERRIDE.hasOverride(fakeMob, ConfigOverride.OverrideKey.HEALTH))
            return Config.OVERRIDE.getInteger(fakeMob, ConfigOverride.OverrideKey.HEALTH);

        return mobCacheEntryHashMap.get(fakeMob.toString()).health;
    }

    private static final int UNKNOWN_MOB_EXP = 1;
    public int getMobExperience(@Nonnull FakeMob fakeMob, @Nonnull World world) {
        if (world.isRemote)
            return UNKNOWN_MOB_EXP;

        if (!fakeMob.isValid())
            return UNKNOWN_MOB_EXP;

        if (!isCached(fakeMob)) {
            if (!updateCache(fakeMob, world))
                return UNKNOWN_MOB_EXP;
        }

        // Configuration value has priority
        if (Config.OVERRIDE.hasOverride(fakeMob, ConfigOverride.OverrideKey.XP))
            return Config.OVERRIDE.getInteger(fakeMob, ConfigOverride.OverrideKey.XP);

        return mobCacheEntryHashMap.get(fakeMob.toString()).xp;
    }

    public boolean isAnimal(@Nonnull FakeMob fakeMob, @Nonnull World world) {

        if (!isCached(fakeMob)) {
            if (!updateCache(fakeMob, world))
                return false;
        }

        return mobCacheEntryHashMap.get(fakeMob.toString()).isAnimal;
    }

    private boolean isCached(@Nonnull FakeMob fakeMob) {
        return mobCacheEntryHashMap.containsKey(fakeMob.toString());
    }

    private boolean updateCache(@Nonnull FakeMob fakeMob, @Nonnull World world) {
        // Cache miss, create the entity
        Entity entity = createEntity(fakeMob, world, new BlockPos(0, 0, 0));
        if (entity == null || !(entity instanceof LivingEntity))
            return false;

        int health = (int)((LivingEntity) entity).getMaxHealth();

        FakePlayer fakePlayer = FakePlayerPool.getFakePlayer((ServerWorld)world, 0);

        int xp = 1;
        try {
            Method getExp = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "func_70693_a", PlayerEntity.class);
            getExp.setAccessible(true);
            xp = (int)getExp.invoke(entity, fakePlayer);
        } catch (Throwable e) {
            Woot.setup.getLogger().debug("Reflection of getExperiencePoints failed {}", e);
        }

        Woot.setup.getLogger().debug("updateCache: caching mob:{} xp:{} health:{}", fakeMob, xp, health);
        addToCache(fakeMob, entity instanceof AnimalEntity, xp, health);
        return true;
    }


    public boolean isLivingEntity(FakeMob fakeMob, World world) {
        Entity entity = createEntity(fakeMob, world, new BlockPos(0, 0, 0));
        return entity != null && entity instanceof MobEntity;
    }

    private static Map<String, MobCacheEntry> mobCacheEntryHashMap = new HashMap<>();
    private void addToCache(FakeMob fakeMob, boolean isAnimal, int xp, int health) {
        mobCacheEntryHashMap.put(fakeMob.toString(), new MobCacheEntry(isAnimal, xp, health));
    }

    class MobCacheEntry {
        public boolean isAnimal = false;
        public int xp = 1;
        public int health = 1;

        public MobCacheEntry(boolean isAnimal, int xp, int health) {
            this.isAnimal = isAnimal;
            this.xp = xp;
            this.health = health;
        }
    }
}
