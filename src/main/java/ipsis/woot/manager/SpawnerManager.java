package ipsis.woot.manager;

import ipsis.oss.LogHelper;
import ipsis.woot.reference.Settings;
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
import net.minecraft.util.MathHelper;
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
 * There are two 'costs'
 *
 * The amount of power required to perform the spawn
 * The rate of spawning
 *
 *
 */

public class SpawnerManager {

    List<Upgrade> upgradeList = new ArrayList<Upgrade>();

    public void init() {

        upgradeList.add(new Upgrade(Upgrade.Type.RATE_I, Upgrade.Group.RATE, Settings.rateIMulti, Settings.rateITicks));
        upgradeList.add(new Upgrade(Upgrade.Type.RATE_II, Upgrade.Group.RATE, Settings.rateIIMulti, Settings.rateIITicks));
        upgradeList.add(new Upgrade(Upgrade.Type.RATE_III, Upgrade.Group.RATE, Settings.rateIIIMulti, Settings.rateIIITicks));
        upgradeList.add(new Upgrade(Upgrade.Type.LOOTING_I, Upgrade.Group.LOOTING, Settings.lootingIMulti, 0));
        upgradeList.add(new Upgrade(Upgrade.Type.LOOTING_II, Upgrade.Group.LOOTING, Settings.lootingIIMulti, 0));
        upgradeList.add(new Upgrade(Upgrade.Type.LOOTING_III, Upgrade.Group.LOOTING, Settings.lootingIIIMulti, 0));
        upgradeList.add(new Upgrade(Upgrade.Type.XP_I, Upgrade.Group.XP, Settings.xpIMulti, 0));
    }

    public Upgrade getUpgrade(Upgrade.Type type) {

        for (Upgrade upgrade : upgradeList)
            if (upgrade.getType() == type)
                return upgrade;

        /* Should never happen */
        LogHelper.warn("getUpgrade: unmapped upgrade requested " + type);
        return upgradeList.get(0);
    }

    public class SpawnReq {

        int totalRf;
        int spawnTime;

        public SpawnReq(int totalRf, int spawnTime) {
            this.totalRf = totalRf;
            this.spawnTime = spawnTime;
        }

        public int getRfPerTick() {

            if (spawnTime > 0) {
                float rfpertick = (float)totalRf / spawnTime;
                return MathHelper.ceiling_float_int(rfpertick);
            }
            return 1;
        }

        public int getTotalRf() {
            return totalRf;
        }

        public int getSpawnTime() {
            return spawnTime;
        }

        @Override
        public String toString() {
            return "totalRf:" + totalRf + " spawnTime:" + spawnTime + " rfPerTick:" + getRfPerTick();
        }
    }

    public SpawnReq getSpawnReq(String mobName, List<Upgrade> upgrades, int xpLevel) {

        int totalRf = Settings.baseRf * xpLevel;
        int spawnTime = Settings.rateBaseTicks;

        for (Upgrade upgrade : upgrades) {
            if (upgrade.hasPowerMult())
                totalRf *= upgrade.getPowerMult();
            if (upgrade.hasSpawnTicks())
                spawnTime = upgrade.getSpawnTicks();
        }

        return new SpawnReq(totalRf, spawnTime);
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

    public boolean isEmpty(String mobName, EnchantKey enchantKey) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            return e.dropMap.get(enchantKey).isEmpty();

        /* No entry yet so it is empty */
        return true;
    }

    public boolean isFull(String mobName, EnchantKey enchantKey) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            return e.dropMap.get(enchantKey).size() == Settings.sampleSize;

        /* No entry yet so it cannot be full */
        return false;
    }


    public void addDrops(String mobName, EnchantKey enchantKey, List<EntityItem> entityItemList) {

        SpawnerEntry e = getSpawnerEntry(mobName);
        if (e.isFull(enchantKey))
            return;

        List<ItemStack> dropList = new ArrayList<ItemStack>();
        for (EntityItem entityItem : entityItemList) {
            ItemStack itemStack = entityItem.getEntityItem();
            dropList.add(ItemStack.copyItemStack(itemStack));
        }

        e.addDrops(enchantKey, dropList);
    }

    public List<ItemStack> getDrops(String mobName, EnchantKey enchantKey) {

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

    public void spawn(String mobName, EnchantKey enchantKey, World world, BlockPos blockPos) {

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

    public enum EnchantKey {
        NO_ENCHANT,
        LOOTING_I,
        LOOTING_II,
        LOOTING_III;
    }
}
