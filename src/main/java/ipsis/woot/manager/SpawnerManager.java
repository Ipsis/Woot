package ipsis.woot.manager;

import ipsis.Woot;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.DamageSourceWoot;
import ipsis.woot.util.FakePlayerUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        if (!Woot.mobRegistry.isValidMobName(mobName))
            return null;

        int baseRF;
        if (tier == EnumMobFactoryTier.TIER_ONE)
            baseRF = Settings.tierIRF;
        else if (tier == EnumMobFactoryTier.TIER_TWO)
            baseRF = Settings.tierIIRF;
        else
            baseRF = Settings.tierIIIRF;

        int mobRf = baseRF * xpLevel;
        int mobCount = upgradeSetup.hasMassUpgrade() ? UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass() : 1;
        int spawnTime = upgradeSetup.hasRateUpgrade() ? UpgradeManager.getSpawnerUpgrade(upgradeSetup.getRateUpgrade()).getSpawnRate() : Settings.baseRateTicks;

        int totalRf = mobRf * mobCount;
        totalRf += (spawnTime * upgradeSetup.getRfPerTickCost());
        return new SpawnReq(totalRf, spawnTime);
    }


    HashMap<String, SpawnerEntry> spawnerMap = new HashMap<String, SpawnerEntry>();

    SpawnerEntry getSpawnerEntry(String mobName) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e == null) {
            e = new SpawnerEntry();
            spawnerMap.put(mobName, e);
        }

        return e;
    }

    public int getSpawnXp(String mobName, TileEntity te) {

        if (!Woot.mobRegistry.hasXp(mobName)) {
            Entity entity = spawnEntity(mobName, te.getWorld(), te.getPos());
            if (entity != null) {
                int xp = ((EntityLiving)entity).experienceValue;
                Woot.mobRegistry.addMapping(mobName, xp);
            }
            entity = null;
        }

        return Woot.mobRegistry.getSpawnXp(mobName);
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

        Entity entity = Woot.mobRegistry.createEntity(mobName, world);

        if (entity != null) {
            ((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(blockPos), null);
            entity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            /**
             * Random loot drop needs a non-zero recentlyHit value
             */
            ((EntityLivingBase)entity).recentlyHit = 100;
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

        /**
         * BUG0022 - Need to set the attackingPlayer or the 1.9 loot tables will not
         * give us all the drops, as some are conditional on killed_by_player
         */
        ((EntityLivingBase)entity).attackingPlayer = fakePlayer;
        ((EntityLivingBase) entity).onDeath(entityDamageSource);
    }

    int calcDeathXp(String mobName, SpawnerUpgrade upgrade) {

        if (upgrade == null)
            return 0;

        int base = Woot.mobRegistry.getDeathXp(mobName);
        if (upgrade.getUpgradeType() == EnumSpawnerUpgrade.XP_II)
            return base;

        float boost = (float)upgrade.getXpBoost();
        int extra = (int)((base / 100.0F) * boost);
        if (extra < 1)
            extra = 1;
        return base + extra;
    }

    void enchantItemStack(ItemStack itemStack, DifficultyInstance difficulty) {

        /* Same way as vanilla setEnchantmentBasedOnDifficulty */
        float f = difficulty.getClampedAdditionalDifficulty();

        boolean allowTreasure = false;
        EnchantmentHelper.addRandomEnchantment(Woot.random, itemStack, (int)(5.0F + f * (float)Woot.random.nextInt(18)), allowTreasure);
    }

    boolean shouldEnchant(DifficultyInstance difficulty) {

        return  Woot.random.nextFloat() < (0.25F  * difficulty.getClampedAdditionalDifficulty());
    }

    public SpawnLoot getSpawnerLoot(String mobName, UpgradeSetup upgradeSetup, DifficultyInstance difficulty) {

        SpawnLoot spawnLoot = new SpawnLoot();
        int mobCount = Settings.Spawner.DEF_BASE_MOB_COUNT;
        if (upgradeSetup.hasMassUpgrade())
            mobCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();

        SpawnerUpgrade xpUpgrade = null;
        if (upgradeSetup.hasXpUpgrade())
            xpUpgrade = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getXpUpgrade());

        for (int i = 0; i < mobCount; i++) {

            List<ItemStack> dropList = getDrops(mobName, upgradeSetup.getEnchantKey());
            if (!dropList.isEmpty()) {

                boolean shouldEnchant = shouldEnchant(difficulty);

                /* cycle a present enchant */
                for (ItemStack stack : dropList) {
                    if (stack.isItemEnchanted()) {
                        shouldEnchant = false;
                        stack.getTagCompound().removeTag("ench");
                        enchantItemStack(stack, difficulty);
                    }
                }

                /* force add an enchant */
                if (shouldEnchant) {
                    for (ItemStack stack : dropList) {
                        if (stack.isItemEnchantable()) {
                            enchantItemStack(stack, difficulty);
                            break;
                        }
                    }
                }
            }

            spawnLoot.drops.addAll(dropList);
            spawnLoot.xp += calcDeathXp(mobName, xpUpgrade);
            if (upgradeSetup.hasDecapitateUpgrade()) {
                ItemStack headStack = Woot.headRegistry.handleDecap(mobName, upgradeSetup.getDecapitateUpgrade());
                if (headStack != null)
                    spawnLoot.drops.add(headStack);
            }
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

    /**
     * Command interface
     */
    public void cmdDumpTable(ICommandSender sender) {

        for (String mobName : spawnerMap.keySet()) {
            SpawnerEntry e = spawnerMap.get(mobName);

            for (EnumEnchantKey key : EnumEnchantKey.values())
                    sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.summary",
                            mobName, key, e.dropMap.get(key).size()));
        }
    }

    public void cmdDumpMobs(ICommandSender sender) {

        for (String mobName : spawnerMap.keySet()) {
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.mobs.summary", mobName));
        }
    }

    public void cmdFlushTableEntry(ICommandSender sender, String mobName, EnumEnchantKey key) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null) {
            int size = e.dropMap.get(key).size();
            flushTableEntry(mobName, key);
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.flush.type.summary", mobName, key, size));
        }
    }

    public void cmdFlushTables(ICommandSender sender) {

        spawnerMap.clear();
        sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.flush.all.summary"));
    }

    public void flushTableEntry(String mobName, EnumEnchantKey key) {

        SpawnerEntry e = spawnerMap.get(mobName);
        if (e != null)
            e.dropMap.get(key).clear();
    }

}
