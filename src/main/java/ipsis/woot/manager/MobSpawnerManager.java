package ipsis.woot.manager;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.EntityLiving;

import java.util.HashMap;
import java.util.Map;

public class MobSpawnerManager {

    private static final MobSpawnerManager INSTANCE = new MobSpawnerManager();
    public static MobSpawnerManager instance() { return INSTANCE; }

    private MobSpawnerManager() { };

    private Map<WootMobName, SpawnerMob> mobMap = new HashMap<>();

    private SpawnerMob addFromWootMobName(WootMobName name) {

        SpawnerMob spawnerMob = new SpawnerMob(name);

        /**
         * Initialise from the ConfigRegistry
         */
        spawnerMob.setSpawnTicks(ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.SPAWN_TICKS));
        spawnerMob.setXpPowerTick(ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.XP_POWER_TICK));

        if (ConfigManager.instance().getBoolean(ConfigManager.EnumConfigKey.MOB_WHITELIST))
            spawnerMob.setCanCapture(false);

        mobMap.put(name, spawnerMob);
        return spawnerMob;
    }

    /**
     * Add
     */
    public WootMobName onWootString(String name) {

        WootMobName mobName = WootMobName.createFromString(name);
        if (mobName == null)
            return null;

        if (!mobMap.containsKey(mobName))
            addFromWootMobName(mobName);

        return mobName;
    }

    public WootMobName onEntityLiving(EntityLiving entityLiving) {

        WootMobName mobName = WootMobName.createFromEntity(entityLiving);
        if (mobName == null)
            return null;

        if (!mobMap.containsKey(mobName)) {
            SpawnerMob spawnerMob = addFromWootMobName(mobName);
            spawnerMob.setSpawnXP(entityLiving.experienceValue);
        }

        return mobName;
    }

    /**
     * Lookup
     */
    public int getSpawnTicks(WootMobName wootMobName) {
        if (mobMap.containsKey(wootMobName))
            return mobMap.get(wootMobName).getSpawnTicks();
        return ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.SPAWN_TICKS);
    }

    public int getXpPowerTick(WootMobName wootMobName) {
        if (mobMap.containsKey(wootMobName))
            return mobMap.get(wootMobName).getXpPowerTick();
        return ConfigManager.instance().getInteger(ConfigManager.EnumConfigKey.XP_POWER_TICK);
    }

    public EnumMobFactoryTier getFactoryTier(WootMobName wootMobName) {
        if (mobMap.containsKey(wootMobName))
            return mobMap.get(wootMobName).getFactoryTier();
        return EnumMobFactoryTier.TIER_ONE;
    }

    public int getSpawnXp(WootMobName wootMobName) {
        if (mobMap.containsKey(wootMobName))
            return mobMap.get(wootMobName).getSpawnXP();
        return 5;
    }

    public int getDeathXp(WootMobName wootMobName) {
        if (mobMap.containsKey(wootMobName))
            return mobMap.get(wootMobName).getDeathXP();
        return 5;
    }

    public boolean canCapture(WootMobName wootMobName) {
        if (mobMap.containsKey(wootMobName))
            return mobMap.get(wootMobName).isCanCapture();

        return true;
    }

    public int getUpgradePower(WootMobName wootMobName, EnumSpawnerUpgrade upgrade) {

        if (mobMap.containsKey(wootMobName) && mobMap.get(wootMobName).hasUpgradePowerPerTick(upgrade))
            return mobMap.get(wootMobName).getUpgradePowerPerTick(upgrade);

        /* TODO */
        return 1;
    }

    public int getUpgradeParameter(WootMobName wootMobName, EnumSpawnerUpgrade upgrade) {

        if (mobMap.containsKey(wootMobName) && mobMap.get(wootMobName).hasUpgradeParameter(upgrade))
            return mobMap.get(wootMobName).getUpgradeParameter(upgrade);

        /* TODO */
        return 1;
    }

    /**
     * Config overrides
     */
    public void updateMobConfig(String name, String tag, boolean v) {

        WootMobName wootMobName = onWootString(name);
        if (wootMobName == null)
            return;

        SpawnerMob spawnerMob = mobMap.get(wootMobName);

        switch (tag) {
            case "CAPTURE":
                spawnerMob.setCanCapture(v);
                break;
            default:
                break;
        }
    }

    public void updateMobConfig(String name, String tag, int v) {

        WootMobName wootMobName = onWootString(name);
        if (wootMobName == null)
            return;

        SpawnerMob spawnerMob = mobMap.get(wootMobName);

        switch (tag) {
            case "SPAWN_TICKS":
                spawnerMob.setSpawnTicks(v);
                break;
            case "SPAWN_XP":
                spawnerMob.setSpawnXP(v);
                break;
            case "DEATH_XP":
                spawnerMob.setDeathXP(v);
                break;
            case "FACTORY_TIER":
                spawnerMob.setFactoryTier(EnumMobFactoryTier.getTier(v));
                break;
            case "KILL_COUNT":
                spawnerMob.setKillCount(v);
                break;
            default:
                break;
        }
    }
}
