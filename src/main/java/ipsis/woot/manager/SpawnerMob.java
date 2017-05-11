package ipsis.woot.manager;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMobName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnerMob {

    private WootMobName mobName;
    private int spawnXP;
    private int deathXP;
    private int spawnTicks;
    private int killCount;
    private int xpPowerTick;
    private boolean canCapture;
    private EnumMobFactoryTier factoryTier;
    private List<EnumSpawnerUpgrade> upgradeBlacklist;
    private Map<EnumSpawnerUpgrade, Integer> upgradeCostMap;
    private Map<EnumSpawnerUpgrade, Integer> upgradeParameterMap;

    public SpawnerMob(WootMobName mobName) {
        this.mobName = mobName;
        spawnXP = 1;
        deathXP = 1;
        spawnTicks = 1;
        xpPowerTick = 1;
        killCount = 1;
        canCapture = true;
        factoryTier = EnumMobFactoryTier.TIER_ONE;
        upgradeBlacklist = new ArrayList<>();
        upgradeCostMap = new HashMap<>();
        upgradeParameterMap = new HashMap<>();
    }

    public int getSpawnXP() { return spawnXP; }
    public int getDeathXP() { return deathXP; }
    public int getSpawnTicks() { return spawnTicks; }
    public int getXpPowerTick() { return xpPowerTick; }
    public boolean isCanCapture() { return canCapture; };
    public int getKillCount() { return killCount; }
    public EnumMobFactoryTier getFactoryTier() { return factoryTier; }

    public void setSpawnXP(int v) { spawnXP = v; }
    public void setDeathXP(int v) { deathXP = v; }
    public void setCanCapture(boolean v) { canCapture = v; }
    public void setSpawnTicks(int v) { spawnTicks = v; }
    public void setXpPowerTick(int v) { xpPowerTick = v; }
    public void setKillCount(int v) { killCount = v; }
    public void setFactoryTier(EnumMobFactoryTier v) { factoryTier = v;  }
    public void addUpgradeToBlacklist(EnumSpawnerUpgrade u) { upgradeBlacklist.add(u); }
    public void addUpgradeCost(EnumSpawnerUpgrade u, int i) { upgradeCostMap.put(u, i); }
    public void addUpgradeParameter(EnumSpawnerUpgrade u, int i) { upgradeParameterMap.put(u, i); }

    public boolean isUpgradeBlacklisted(EnumSpawnerUpgrade upgrade) {

        for (EnumSpawnerUpgrade c : upgradeBlacklist)
            if (c.equals(upgrade))
                return true;

        return false;
    }

    public boolean hasUpgradePowerPerTick(EnumSpawnerUpgrade upgrade) {

        return upgradeCostMap.containsKey(upgrade);
    }

    public int getUpgradePowerPerTick(EnumSpawnerUpgrade upgrade) {

        return upgradeCostMap.get(upgrade);
    }

    public boolean hasUpgradeParameter(EnumSpawnerUpgrade upgrade) {

        return upgradeParameterMap.containsKey(upgrade);
    }

    public int getUpgradeParameter(EnumSpawnerUpgrade upgrade) {

        return upgradeParameterMap.get(upgrade);
    }
}
