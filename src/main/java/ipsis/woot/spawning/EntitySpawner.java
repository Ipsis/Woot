package ipsis.woot.spawning;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.FakePlayerPool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntitySpawner implements IEntitySpawner {

    private List<AbstractMobEntity> mobEntityList = new ArrayList<>();

    public EntitySpawner() {

        mobEntityList.add(new MobEntityMagmaCube());
        mobEntityList.add(new MobEntitySlime());
        mobEntityList.add(new MobEntityChargedCreeper());
        mobEntityList.add(new MobEntityPinkSlime());
    }

    private void applyCustomConfig(Entity entity, WootMobName wootMobName, World world) {

        for (AbstractMobEntity abstractMobEntity : mobEntityList)
            abstractMobEntity.runSetup(entity, wootMobName, world);
    }

    private @Nullable Entity createEntity(WootMobName wootMobName, World world) {

        ResourceLocation resourceLocation = wootMobName.getResourceLocation();
        Entity entity = EntityList.createEntityByIDFromName(resourceLocation, world);
        // Got to be a mob, not a bottle or other non-living entity
        if (entity != null && entity instanceof EntityLiving)
            applyCustomConfig(entity, wootMobName, world);
        else
            entity = null;

        return entity;
    }

    private @Nullable Entity spawnEntity(WootMobName wootMobName, World world, BlockPos pos) {

        Entity entity = createEntity(wootMobName, world);
        if (entity != null) {
                if (!ForgeEventFactory.doSpecialSpawn((EntityLiving)entity, world, pos.getX(), pos.getY(), pos.getZ()))
                    ((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(pos), null);

            /**
             * Random loot drop needs a non-zero recentlyHit value
             */
            ((EntityLivingBase) entity).recentlyHit = 100;

            entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
        }

        return entity;
    }

    public void spawn(WootMobName wootMobName, EnumEnchantKey key, World world, BlockPos pos) {

        Entity entity = spawnEntity(wootMobName, world, pos);
        if (entity == null)
            return;

        FakePlayer fakePlayer = FakePlayerPool.getFakePlayer((WorldServer)world, key);
        if (fakePlayer == null)
            return;

        if (Woot.debugSetup.areTracing(DebugSetup.EnumDebugType.SPAWN)) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.SPAWN, "spawn entity:", entity.getName());
            for (ItemStack itemStack : entity.getArmorInventoryList())
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.SPAWN, "spawn armour:", itemStack);
            for (ItemStack itemStack : entity.getHeldEquipment())
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.SPAWN, "spawn equipment", itemStack);
        }

        /**
         * BUG0022 - Need to set the attackingPlayer or the 1.9 loot tables will not
         * give us all the drops, as some are conditional on killed_by_player
         */
        ((EntityLivingBase)entity).attackingPlayer = fakePlayer;
        ((EntityLivingBase) entity).onDeath(DamageSource.causePlayerDamage(fakePlayer));
    }
}
