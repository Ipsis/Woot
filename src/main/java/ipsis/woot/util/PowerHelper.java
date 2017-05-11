package ipsis.woot.util;

import ipsis.woot.manager.*;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import net.minecraft.util.math.MathHelper;

public class PowerHelper {

    public static int tierToPower(EnumMobFactoryTier factoryTier) {

        if (factoryTier == EnumMobFactoryTier.TIER_ONE)
            return ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.T1_POWER_TICK);
        else if (factoryTier == EnumMobFactoryTier.TIER_TWO)
            return ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.T2_POWER_TICK);
        else if (factoryTier == EnumMobFactoryTier.TIER_THREE)
            return ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.T3_POWER_TICK);
        else
            return ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.T4_POWER_TICK);
    }

    private static int calculateUpgradePower(WootMobName wootMobName, UpgradeSetup upgradeSetup) {

        int powerPerTick = 0;

        if (upgradeSetup.getMassUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getMassUpgrade());

        if (upgradeSetup.getRateUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getRateUpgrade());

        if (upgradeSetup.getDecapitateUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getDecapitateUpgrade());

        if (upgradeSetup.getLootingUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getLootingUpgrade());

        if (upgradeSetup.getBmUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getBmUpgrade());

        if (upgradeSetup.getEfficiencyUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getEfficiencyUpgrade());

        if (upgradeSetup.getXpUpgrade() != null)
            powerPerTick += MobSpawnerManager.instance().getUpgradePower(wootMobName, upgradeSetup.getXpUpgrade());

        return powerPerTick;
    }

    public static SpawnerManager.SpawnReq calculatePower(WootMobName wootMobName, UpgradeSetup upgradeSetup, EnumMobFactoryTier factoryTier) {

        int spawnTicks = MobSpawnerManager.instance().getSpawnTicks(wootMobName);
        int spawnXp = MobSpawnerManager.instance().getSpawnXp(wootMobName);
        int tierPowerPerTick = tierToPower(factoryTier);
        int xpPowerPerTick = MobSpawnerManager.instance().getXpPowerTick(wootMobName);

        int mobCount = ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.NUM_MOBS);
        if (upgradeSetup.hasMassUpgrade())
            mobCount = MobSpawnerManager.instance().getUpgradeParameter(wootMobName, upgradeSetup.getMassUpgrade());

        int totalPower = (tierPowerPerTick * spawnTicks) + ((spawnXp * spawnTicks * xpPowerPerTick) * mobCount) + (calculateUpgradePower(wootMobName, upgradeSetup) * spawnTicks);

        if (upgradeSetup.hasEfficiencyUpgrade()) {
            int f = MobSpawnerManager.instance().getUpgradeParameter(wootMobName, upgradeSetup.getEfficiencyUpgrade());
            int saving = (int)((totalPower / 100.0F) * f);
            totalPower -= saving;
            totalPower = MathHelper.clamp(totalPower, 1, Integer.MAX_VALUE);
        }

        return new SpawnerManager.SpawnReq(totalPower, spawnTicks);
    }
}
