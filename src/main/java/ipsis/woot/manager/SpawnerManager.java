package ipsis.woot.manager;

import ipsis.oss.LogHelper;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.DamageSourceWoot;
import ipsis.woot.util.FakePlayerUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class SpawnerManager {

    public static class SpawnReq {

        int totalRf;
        int spawnTime;
        int rfPerTick;

        public SpawnReq(int totalRf, int spawnTime) {
            this.totalRf = totalRf;
            this.spawnTime = spawnTime;

            this.rfPerTick = calcRfPerTick();
        }

        public int calcRfPerTick() {

            if (spawnTime > 0) {
                float rfpertick = (float)totalRf / spawnTime;
                return MathHelper.ceiling_float_int(rfpertick);
            }
            return 1;
        }

        static final String NBT_SPAWN_REQ_TOTAL_RF = "reqTotalRf";
        static final String NBT_SPAWN_REQ_SPAWN_TIME = "reqSpawnTime";
        public void writeToNBT(NBTTagCompound compound) {

            compound.setInteger(NBT_SPAWN_REQ_TOTAL_RF, totalRf);
            compound.setInteger(NBT_SPAWN_REQ_SPAWN_TIME, spawnTime);
        }

        public static SpawnReq readFromNBT(NBTTagCompound compound) {

            int rf = compound.getInteger(NBT_SPAWN_REQ_TOTAL_RF);
            int time = compound.getInteger(NBT_SPAWN_REQ_SPAWN_TIME);
            return new SpawnReq(rf, time);
        }

        public int getTotalRf() {
            return totalRf;
        }

        public int getSpawnTime() { return spawnTime; }

        public int getRfPerTick() { return rfPerTick; }

        @Override
        public String toString() {
            return totalRf + "RF " + spawnTime + " ticks @ " + rfPerTick + "RF/tick";
        }
    }

    public SpawnReq getSpawnReq(String mobName, UpgradeSetup upgradeSetup, int xpLevel, EnumMobFactoryTier tier) {

        if (!MobManager.isValidMobName(mobName))
            return null;

        int baseRF;
        if (tier == EnumMobFactoryTier.TIER_ONE)
            baseRF = Settings.tierIRF;
        else if (tier == EnumMobFactoryTier.TIER_TWO)
            baseRF = Settings.tierIIRF;
        else
            baseRF = Settings.tierIIIRF;

        int totalRf = baseRF * xpLevel;
        int spawnTime = Settings.baseRateTicks;

        // The baseRF is per Mob
        if (upgradeSetup.hasMassUpgrade())
            totalRf += (baseRF * UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass());

        if (upgradeSetup.hasRateUpgrade())
            spawnTime = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getRateUpgrade()).getSpawnRate();

        totalRf += (spawnTime * upgradeSetup.getRfPerTickCost());
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

    public int getXp(String mobName) {

        if (!xpMap.containsKey(mobName))
            return 1;
        else
            return xpMap.get(mobName);
    }

    public int getXp(String mobName, TileEntity te) {

        if (!xpMap.containsKey(mobName)) {
            /* spawn and store */
            Entity entity = spawnEntity(mobName, te.getWorld(), te.getPos());
            if (entity != null) {
                try {
                /* TODO Dev only version - change to access tranformer */
                    Field f = ReflectionHelper.findField(EntityLiving.class, "experienceValue");
                    int xp = f.getInt(entity);
                    if (xp <= 0)
                        xp = 1;
                    xpMap.put(mobName, xp);
                    LogHelper.info("getXP: " + mobName + "->" + xp);
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

    public boolean isEmpty(String mobName, EnumEnchantKey enchantKey) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            return e.dropMap.get(enchantKey).isEmpty();

        /* No entry yet so it is empty */
        return true;
    }

    public boolean isFull(String mobName, EnumEnchantKey enchantKey) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            return e.dropMap.get(enchantKey).size() == Settings.sampleSize;

        /* No entry yet so it cannot be full */
        return false;
    }


    public void addDrops(String mobName, EnumEnchantKey enchantKey, List<EntityItem> entityItemList) {

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

    public List<ItemStack> getDrops(String mobName, EnumEnchantKey enchantKey) {

        if (isEmpty(mobName, enchantKey))
           return new ArrayList<ItemStack>();

        SpawnerEntry e = getSpawnerEntry(mobName);
        return e.getDrops(enchantKey);
    }

    private Entity spawnEntity(String mobName, World world, BlockPos blockPos) {

        String spawnMobName = MobManager.getBaseMobName(mobName);
        Entity entity = EntityList.createEntityByName(spawnMobName, world);

        if (entity != null) {
            if (MobManager.isWitherSkeleton(mobName))
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
        } else {
            LogHelper.info("spawnEntity: failed for " + spawnMobName);
        }

        return entity;
    }

    public void spawn(String mobName, EnumEnchantKey enchantKey, World world, BlockPos blockPos) {

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

    int getXp(String mobName, SpawnerUpgrade upgrade) {

        // Require the XP upgrade to get XP
        if (upgrade == null)
            return 0;

        int base = getXp(mobName);
        float boost = (float)upgrade.getXpBoost();
        return base + (int)((base / 100.0F) * boost);
    }

    public SpawnLoot getSpawnerLoot(String mobName, UpgradeSetup upgradeSetup) {

        SpawnLoot spawnLoot = new SpawnLoot();
        int mobCount = Settings.Spawner.DEF_BASE_MOB_COUNT;
        if (upgradeSetup.hasMassUpgrade())
            mobCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();

        for (int i = 0; i < mobCount; i++) {
            List<ItemStack> dropList = getDrops(mobName, upgradeSetup.getEnchantKey());
            spawnLoot.drops.addAll(dropList);
            if (upgradeSetup.hasXpUpgrade())
                spawnLoot.xp += getXp(mobName, UpgradeManager.getSpawnerUpgrade(upgradeSetup.getXpUpgrade()));
            // TODO beheading
        }

        return spawnLoot;
    }

    public class SpawnLoot {

        List<ItemStack> drops;
        int xp;

        public SpawnLoot() {

            this.xp = 0;
            this.drops = new ArrayList<ItemStack>();
        }

        public int getXp() { return this.xp; }
        public List<ItemStack> getDropList() { return this.drops; }
    }

}
