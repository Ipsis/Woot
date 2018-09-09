package ipsis.woot.spawning;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakePlayerPool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class SpawnManager {

    private static void applyCustomConfig(EntityLiving entityLiving, FakeMobKey fakeMobKey, World world) {

        // TODO handle magmacube, slime, chargedcreeper, pink slime etc

    }

    private static @Nullable EntityLiving createEntity(FakeMobKey fakeMobKey, World world) {

        ResourceLocation rl = fakeMobKey.getResourceLocation();
        Entity entity = EntityList.createEntityByIDFromName(rl, world);

        if (entity instanceof EntityLiving) {
            applyCustomConfig((EntityLiving) entity, fakeMobKey, world);
            return (EntityLiving) entity;
        }

        return null;
    }

    public static void spawnKill(FakeMobKey fakeMobKey, int looting, World world, BlockPos origin) {

        FakePlayer fakePlayer = FakePlayerPool.getFakePlayer((WorldServer)world, looting);
        if (fakePlayer == null)
            return;

        EntityLiving entityLiving = createEntity(fakeMobKey, world);
        if (entityLiving == null)
            return;

        if (!ForgeEventFactory.doSpecialSpawn(entityLiving, world, origin.getX(), origin.getY(), origin.getZ()))
            entityLiving.onInitialSpawn(world.getDifficultyForLocation(origin), null);

        /***
         * Random loot drop needs a non-zero recentlyHit value
         */
        entityLiving.recentlyHit = 100;
        entityLiving.setPosition(origin.getX(), origin.getY(), origin.getZ());
        entityLiving.attackingPlayer = fakePlayer;
        entityLiving.onDeath(DamageSource.causePlayerDamage(fakePlayer));
    }
}
