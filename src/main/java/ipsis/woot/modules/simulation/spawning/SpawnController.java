package ipsis.woot.modules.simulation.spawning;

import ipsis.woot.config.WootConfig;
import ipsis.woot.modules.simulation.FakePlayerPool;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SpawnController {

    public static SpawnController get() { return INSTANCE; }
    static SpawnController INSTANCE;
    static { INSTANCE = new SpawnController(); }

    public void spawnKill(@Nonnull FakeMobKey fakeMobKey, @Nonnull World world, @Nonnull BlockPos spawnPos) {

        if (!fakeMobKey.getMob().isValid())
            return;

        FakePlayer fakePlayer = FakePlayerPool.getFakePlayer((ServerWorld)world, fakeMobKey.getLooting());
        if (fakePlayer == null)
            return;

        Entity entity = createEntity(fakeMobKey.getMob(), world, spawnPos);
        if (entity == null || !(entity instanceof MobEntity))
            return;

        MobEntity mobEntity = (MobEntity)entity;

        mobEntity.onInitialSpawn(world,
                world.getDifficultyForLocation(new BlockPos(entity)),
                SpawnReason.SPAWNER,
                null, null);

        mobEntity.recentlyHit = 100;
        mobEntity.attackingPlayer = fakePlayer;

        CustomSpawnController.get().apply(mobEntity, fakeMobKey.getMob(), world);
        mobEntity.onDeath(DamageSource.causePlayerDamage(fakePlayer));
    }

    private @Nullable Entity createEntity(@Nonnull FakeMob fakeMob, @Nonnull World world, @Nonnull BlockPos pos) {

        ResourceLocation rl = fakeMob.getResourceLocation();
        if (!ForgeRegistries.ENTITIES.containsKey(rl))
            return null;

        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("id", rl.toString());
        Entity entity = EntityType.func_220335_a(nbt, world, (xc) -> {
            xc.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), xc.rotationYaw, xc.rotationPitch);
            return xc;
        });

        return entity;
    }

    Map<String, Integer> mobHealthCache = new HashMap<>();
    public int getMobHealth(@Nonnull FakeMob fakeMob, @Nonnull World world) {
        if (!fakeMob.isValid())
            return -1;

        // Configuration value has priority
        if (WootConfig.get().hasMobOverride(fakeMob, WootConfig.ConfigKey.MOB_HEALTH))
            return WootConfig.get().getIntConfig(fakeMob, WootConfig.ConfigKey.MOB_HEALTH);

        // Check the cache
        String key = fakeMob.toString();
        if (mobHealthCache.containsKey(key))
            return mobHealthCache.get(key);

        // Cache miss, create the entity
        Entity entity = createEntity(fakeMob, world, new BlockPos(0, 0, 0));
        if (entity == null || !(entity instanceof LivingEntity))
            return -1;

        int health = (int)((LivingEntity) entity).getMaxHealth();
        mobHealthCache.put(key, health);
        return health;
    }

    public boolean isLivingEntity(FakeMob fakeMob, World world) {
        Entity entity = createEntity(fakeMob, world, new BlockPos(0, 0, 0));
        return entity != null && entity instanceof MobEntity;
    }
}
