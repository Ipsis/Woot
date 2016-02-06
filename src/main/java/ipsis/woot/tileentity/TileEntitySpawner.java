package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.manager.SpawnerManager;
import ipsis.woot.manager.Upgrade;
import ipsis.woot.reference.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileEntitySpawner extends TileEntity implements ITickable {

    int currLearnTicks;
    int currSpawnTicks;
    boolean isRunning;
    boolean isFormed;
    String mobName;
    SpawnerManager.SpawnReq spawnReq;
    SpawnerManager.EnchantKey enchantKey;
    int consumedRf;

    HashMap<Upgrade.Group, Upgrade> upgradeMap = new HashMap<Upgrade.Group, Upgrade>();

    public TileEntitySpawner() {
        reset();
    }

    public void onFormed() {
        /* TODO get the mobName */
        reset();

        addUpgrade(Woot.spawnerManager.getUpgrade(Upgrade.Type.LOOTING_I));
        mobName = "Skeleton";
        enchantKey = getEnchantKey();
        isFormed = true;
        spawnReq = Woot.spawnerManager.getSpawnReq(mobName, getUpgrades(), Woot.spawnerManager.getXp(mobName, this));
        isRunning = true;

        LogHelper.info("onFormed: " + mobName + " " + enchantKey + " " + spawnReq);
    }

    void reset() {
        this.mobName = null;
        this.upgradeMap.clear();
        this.isFormed = false;
        this.isRunning = false;
        this.currLearnTicks = 0;
        this.currSpawnTicks = 0;
        this.consumedRf = 0;
        this.spawnReq = null;
        this.enchantKey = null;
    }

    public List<Upgrade> getUpgrades() {
        return new ArrayList<Upgrade>(upgradeMap.values());
    }

    public boolean addUpgrade(Upgrade upgrade) {

        if (!upgradeMap.containsKey(upgrade.getGroup())) {
            upgradeMap.put(upgrade.getGroup(), upgrade);
            return true;
        }
        return false;
    }

    public void delUpgrade(Upgrade upgrade) {

        if (upgradeMap.containsKey(upgrade.getGroup()))
            upgradeMap.remove(upgrade.getGroup());
    }

    SpawnerManager.EnchantKey getEnchantKey() {

        Upgrade upgrade = upgradeMap.get(Upgrade.Group.LOOTING);
        if (upgrade != null) {
            for (Upgrade u : upgradeMap.values()) {
                /* TODO move this to an Upgrade method */
                if (u.getGroup() == Upgrade.Group.LOOTING) {
                    if (u.getType() == Upgrade.Type.LOOTING_I)
                        return SpawnerManager.EnchantKey.LOOTING_I;
                    else if (u.getType() == Upgrade.Type.LOOTING_I)
                        return SpawnerManager.EnchantKey.LOOTING_II;
                    if (u.getType() == Upgrade.Type.LOOTING_II)
                        return SpawnerManager.EnchantKey.LOOTING_III;
                }
            }
        }

        return SpawnerManager.EnchantKey.NO_ENCHANT;
    }

    @Override
    public void update() {

        if (worldObj.isRemote || !isRunning || !isFormed)
            return;

        currLearnTicks++;
        if (currLearnTicks >= Settings.learnTicks) {
            if (!Woot.spawnerManager.isFull(mobName, enchantKey)) {
                /* Not full so fake another spawn */
                Woot.spawnerManager.spawn(mobName, enchantKey, this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen once per mob */
        if (Woot.spawnerManager.isEmpty(mobName, enchantKey))
            return;

        int drawnRf = spawnReq.getRfPerTick() * 1;
        if (drawnRf == spawnReq.getRfPerTick()) {
            consumedRf += drawnRf;
        } else {
            consumedRf += drawnRf;
            /* TODO hardmode => consumedRf = 0; */
        }

        currSpawnTicks++;
        if (currSpawnTicks == spawnReq.getSpawnTime()) {
           if (consumedRf >= spawnReq.getTotalRf()) {
               List<ItemStack> dropList = Woot.spawnerManager.getDrops(mobName, enchantKey);
               LogHelper.info("Generating drops: " + dropList);
           } else {
               LogHelper.info("Not enough power provided " + consumedRf + ":" + spawnReq.getTotalRf());
           }
            currSpawnTicks = 0;
            consumedRf = 0;
        }
    }
}
