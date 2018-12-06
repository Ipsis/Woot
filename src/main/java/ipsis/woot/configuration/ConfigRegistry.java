package ipsis.woot.configuration;

import ipsis.woot.configuration.vanilla.FactoryConfig;
import ipsis.woot.configuration.vanilla.GeneralConfig;
import ipsis.woot.configuration.vanilla.UpgradeConfig;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helpers.LogHelper;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * This handles accessing any of the mob specific configuration.
 * By default the standard config value will be returned, however
 * if there is an override value for that mob, then it will be used.
 *
 * All access must be through this
 */

public class ConfigRegistry {

    public static final ConfigRegistry INSTANCE = new ConfigRegistry();
    static {
        INSTANCE.addOverride(Key.SPAWN_TIME, "minecraft:creeper", 1000);
        INSTANCE.addOverride(Key.MOB_TIER, "minecraft:blaze", 4);
    }


    public enum Key {
        HEADHUNTER_1_DROP,
        HEADHUNTER_2_DROP,
        HEADHUNTER_3_DROP,
        DECAPITATE_1_DROP,
        DECAPITATE_2_DROP,
        DECAPITATE_3_DROP,
        MASS_1_NUM_MOBS,
        MASS_2_NUM_MOBS,
        MASS_3_NUM_MOBS,
        RATE_1_REDUCTION,
        RATE_2_REDUCTION,
        RATE_3_REDUCTION,
        XP_1_AMOUNT,
        XP_2_AMOUNT,
        XP_3_AMOUNT,

        NUM_MOBS,
        SPAWN_TIME,
        UNITS_PER_HEALTH,
        MOB_TIER
    }

    public int getIntegerConfig(Key key) {
        return getIntegerDefault(key);
    }

    public int getIntegerConfig(Key key, FakeMobKey fakeMobKey) {

        if (integerMobConfigMap.containsKey(key)) {
            if (integerMobConfigMap.get(key).containsKey(fakeMobKey))
                return integerMobConfigMap.get(key).get(fakeMobKey);
        }

        return getIntegerConfig(key);
    }

    private int getIntegerDefault(Key key) {

        switch (key) {
            case DECAPITATE_1_DROP:
                return UpgradeConfig.DECAPITATE_1_DROP;
            case DECAPITATE_2_DROP:
                return UpgradeConfig.DECAPITATE_2_DROP;
            case DECAPITATE_3_DROP:
                return UpgradeConfig.DECAPITATE_3_DROP;
            case HEADHUNTER_1_DROP:
                return GeneralConfig.HEADHUNTER_1_DROP;
            case HEADHUNTER_2_DROP:
                return GeneralConfig.HEADHUNTER_2_DROP;
            case HEADHUNTER_3_DROP:
                return GeneralConfig.HEADHUNTER_3_DROP;
            case MASS_1_NUM_MOBS:
                return UpgradeConfig.MASS_1_NUM_MOBS;
            case MASS_2_NUM_MOBS:
                return UpgradeConfig.MASS_2_NUM_MOBS;
            case MASS_3_NUM_MOBS:
                return UpgradeConfig.MASS_3_NUM_MOBS;
            case RATE_1_REDUCTION:
                return UpgradeConfig.RATE_1_REDUCTION;
            case RATE_2_REDUCTION:
                return UpgradeConfig.RATE_2_REDUCTION;
            case RATE_3_REDUCTION:
                return UpgradeConfig.RATE_3_REDUCTION;
            case XP_1_AMOUNT:
                return UpgradeConfig.XP_1_AMOUNT;
            case XP_2_AMOUNT:
                return UpgradeConfig.XP_2_AMOUNT;
            case XP_3_AMOUNT:
                return UpgradeConfig.XP_3_AMOUNT;
            case NUM_MOBS:
                return FactoryConfig.NUM_MOBS;
            case SPAWN_TIME:
                return FactoryConfig.SPAWN_TIME;
            case UNITS_PER_HEALTH:
                return FactoryConfig.UNITS_PER_HEALTH;
            default:
                LogHelper.error("ConfigRegistry unhandled key " + key);
                return 1;
        }
    }

    public int getFactoryTier(FakeMobKey fakeMobKey, int mobHealth) {
        // TODO mob specific override
        Integer override = getOverrideInteger(Key.MOB_TIER, fakeMobKey);
        if (override != null)
            return override;

        /**
         * If there is no mob override then we return the lowest tier than can handle the health
         */
        if (mobHealth <= FactoryConfig.TIER_1_MAX_MOB_HEALTH)
            return 1;
        if (mobHealth <= FactoryConfig.TIER_2_MAX_MOB_HEALTH)
            return 2;
        if (mobHealth <= FactoryConfig.TIER_3_MAX_MOB_HEALTH)
            return 3;
        return 4;

    }

    /**
     * Overrides
     */
    private HashMap<Key, HashMap<String, Integer>> integerMobConfigMap = new HashMap<>();
    private HashMap<Key, HashMap<String, Boolean>> booleanMobConfigMap = new HashMap<>();
    public void addOverride(Key key, String mob, int value) {
        if (!integerMobConfigMap.containsKey(key))
            integerMobConfigMap.put(key, new HashMap<>());
        integerMobConfigMap.get(key).put(mob, value);
    }

    public @Nullable Integer getOverrideInteger(Key key, FakeMobKey fakeMobKey) {
        if (!integerMobConfigMap.containsKey(key))
            return null;
        return integerMobConfigMap.get(key).get(fakeMobKey.getEntityKey());
    }

    public void addOverride(Key key, String mob, boolean value) {
        if (!booleanMobConfigMap.containsKey(key))
            booleanMobConfigMap.put(key, new HashMap<>());
        booleanMobConfigMap.get(key).put(mob, value);
    }


}
