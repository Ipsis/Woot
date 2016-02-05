package ipsis.woot.manager;

import ipsis.oss.LogHelper;
import ipsis.woot.tileentity.TileEntityFactory;
import ipsis.woot.util.DamageSourceWoot;
import ipsis.woot.util.FakePlayerUtil;
import ipsis.woot.util.MobUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Spawner mechanics
 *
 * Vanilla spawner spawns @ minimum of 10 seconds
 *
 * EnergyBaseCost = (PowerAcceptRate * 200) = 12000RF for a exp 1 mob
 *
 * SpawnCost = (EnergyBaseCost * ExperienceLevel) * LootingModifier
 * SpawnMass = 1->4 mobs
 *
 * PowerAcceptRate = 60 RF/tick
 * PowerAcceptRate POWER_I = 120 RF/tick
 * PowerAcceptRate POWER_II = 240 RF/tick
 * PowerAcceptRate POWER_III = 480 RF/tick
 *
 * SpawnRate = SpawnCost / PowerAcceptRate
 *
 *
 *
 *
 */

public class SpawnerManager2 {

    int getPowerAcceptRate(SpawnerUpgrade spawnerUpgrade) {

        int rate;
        switch (spawnerUpgrade) {
            case BASE:
                rate = 60;
                break;
            case POWER_I:
                rate = 120;
                break;
            case POWER_II:
                rate = 240;
                break;
            case POWER_III:
                rate = 480;
                break;
            default:
                /* Rate does not apply to any other upgrade */
                rate = 1;
                break;
        }

        return rate;
    }

    /**
     * Spawner costings
     */
    static final int ENERGY_BASE_COST = 12000; /* RF */

    public int getSpawnCost(TileEntityFactory te, String mobName, int expLevel) {

        /* TODO get expLevel from the SpawnerEntry */

        int rf = ENERGY_BASE_COST * expLevel;
        for (SpawnerUpgrade upgrade : te.getUpgrades())
            rf *= upgrade.getPowerFactor();

        return rf;
    }


    public static Random random = new Random();
    HashMap<String, SpawnerEntry> spawnerMap = new HashMap<String, SpawnerEntry>();
    HashMap<String, Integer> xpMap = new HashMap<String, Integer>();

    SpawnerEntry getSpawnerEntry(String mobName) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e == null) {
            e = new SpawnerEntry();
            spawnerMap.put(mobName, e);
        }

        return e;
    }

    public int getXp(String mobName, TileEntity te) {

        if (!xpMap.containsKey(mobName)) {
            /* spawn and store */
            Entity entity = spawnEntity(mobName, te.getWorld(), te.getPos());
            if (entity != null) {
                try {
                /* TODO Dev only version - change to access tranformer */
                    Field f = ReflectionHelper.findField(EntityLiving.class, "experienceValue");
                    xpMap.put(mobName, f.getInt(entity));
                } catch (Exception e) {
                    LogHelper.error("Reflection of recentlyHit failed: " + e);
                }
            }
        }

        Integer xp = xpMap.get(mobName);
        if (xp != null)
            return xp;
        else
            return 1; /* This should not happen */
    }

    public boolean isEmpty(String mobName, SpawnerManager.EnchantKey enchantKey) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            return e.dropMap.get(enchantKey).isEmpty();

        /* No entry yet so it is empty */
        return true;
    }

    public boolean isFull(String mobName, SpawnerManager.EnchantKey enchantKey) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            return e.dropMap.get(enchantKey).size() == SpawnerEntry.SAMPLE_SIZE;

        /* No entry yet so it cannot be full */
        return false;
    }


    public void addDrops(String mobName, SpawnerManager.EnchantKey enchantKey, List<EntityItem> entityItemList) {

        SpawnerEntry e = getSpawnerEntry(mobName);
        if (e.isFull(enchantKey))
            return;

        List<ItemStack> dropList = new ArrayList<ItemStack>();
        for (EntityItem entityItem : entityItemList)
            dropList.add(entityItem.getEntityItem().copy());

        e.addDrops(enchantKey, dropList);
    }

    public List<ItemStack> getDrops(String mobName, SpawnerManager.EnchantKey enchantKey) {

        if (isEmpty(mobName, enchantKey))
           return new ArrayList<ItemStack>();

        SpawnerEntry e = getSpawnerEntry(mobName);
        return e.getDrops(enchantKey);
    }

    private Entity spawnEntity(String mobName, World world, BlockPos blockPos) {

        String spawnMobName = MobUtil.getBaseMobName(mobName);
        Entity entity = EntityList.createEntityByName(spawnMobName, world);

        if (entity != null) {
            if (MobUtil.isWitherSkeleton(mobName))
                ((EntitySkeleton) entity).setSkeletonType(1);

            ((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(blockPos), (IEntityLivingData) null);
            entity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            /**
             * Random loot drop needs a non-zero recentlyHit value
             */
            try {
            /* TODO Dev only version - change to access tranformer */
                Field f = ReflectionHelper.findField(EntityLivingBase.class, "recentlyHit");
                f.setInt(entity, 100);
            } catch (Exception e) {
                LogHelper.error("Reflection of recentlyHit failed: " + e);
            }
        }

        return entity;
    }

    public void spawn(String mobName, SpawnerManager.EnchantKey enchantKey, World world, BlockPos blockPos) {

        Entity entity = spawnEntity(mobName, world, blockPos);
        if (entity == null)
            return;

        FakePlayer fakePlayer = FakePlayerUtil.getFakePlayer((WorldServer)world);
        if (fakePlayer == null)
            return;
        FakePlayerUtil.setLooting(fakePlayer, enchantKey);

        DamageSourceWoot damageSourceWoot = DamageSourceWoot.getDamageSource(enchantKey);
        if (damageSourceWoot == null)
            return;
        EntityDamageSource entityDamageSource = new EntityDamageSource(damageSourceWoot.getDamageType(), fakePlayer);

        ((EntityLivingBase) entity).onDeath(entityDamageSource);
    }

}
