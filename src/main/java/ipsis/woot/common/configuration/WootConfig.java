package ipsis.woot.common.configuration;

import ipsis.woot.util.FakeMob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class WootConfig {

    private static final Logger LOGGER = LogManager.getLogger();
    private static WootConfig INSTANCE = new WootConfig();
    public static WootConfig get() { return INSTANCE; }

    private static HashMap<ConfigKey, Integer> intMappings = new HashMap<>();
    public enum ConfigKey {
        INVALID_KEY(""),
        MASS_COUNT(Config.COMMON.MASS_COUNT_TAG),
        SPAWN_TICKS(Config.COMMON.SPAWN_TICKS_TAG),
        UNITS_PER_HEALTH(Config.COMMON.UNITS_PER_HEALTH_TAG),
        MOB_SHARD_KILLS(Config.COMMON.MOB_SHARD_KILLS_TAG),
        MOB_HEALTH(Config.COMMON.MOB_HEALTH_TAG),
        MOB_TIER(Config.COMMON.MOB_TIER_TAG),

        TIER_1_MAX_UNITS(Config.COMMON.TIER_1_MAX_UNITS_TAG),
        TIER_2_MAX_UNITS(Config.COMMON.TIER_2_MAX_UNITS_TAG),
        TIER_3_MAX_UNITS(Config.COMMON.TIER_3_MAX_UNITS_TAG),
        TIER_4_MAX_UNITS(Config.COMMON.TIER_4_MAX_UNITS_TAG),
        TIER_5_MAX_UNITS(Config.COMMON.TIER_5_MAX_UNITS_TAG),

        TIER_1_UNITS_PER_TICK(Config.COMMON.TIER_1_UNITS_PER_TICK_TAG),
        TIER_2_UNITS_PER_TICK(Config.COMMON.TIER_2_UNITS_PER_TICK_TAG),
        TIER_3_UNITS_PER_TICK(Config.COMMON.TIER_3_UNITS_PER_TICK_TAG),
        TIER_4_UNITS_PER_TICK(Config.COMMON.TIER_4_UNITS_PER_TICK_TAG),
        TIER_5_UNITS_PER_TICK(Config.COMMON.TIER_5_UNITS_PER_TICK_TAG),

        CELL_1_CAPACITY(Config.COMMON.CELL_1_CAPACITY_TAG),
        CELL_2_CAPACITY(Config.COMMON.CELL_2_CAPACITY_TAG),
        CELL_3_CAPACITY(Config.COMMON.CELL_3_CAPACITY_TAG),
        CELL_1_MAX_TRANSFER(Config.COMMON.CELL_1_MAX_TRANSFER_TAG),
        CELL_2_MAX_TRANSFER(Config.COMMON.CELL_2_MAX_TRANSFER_TAG),
        CELL_3_MAX_TRANSFER(Config.COMMON.CELL_3_MAX_TRANSFER_TAG),

        CAPACITY_1(Config.COMMON.CAPACITY_1_TAG),
        CAPACITY_2(Config.COMMON.CAPACITY_2_TAG),
        CAPACITY_3(Config.COMMON.CAPACITY_3_TAG),
        EFFICIENCY_1(Config.COMMON.EFFICIENCY_1_TAG),
        EFFICIENCY_2(Config.COMMON.EFFICIENCY_2_TAG),
        EFFICIENCY_3(Config.COMMON.EFFICIENCY_3_TAG),
        MASS_1(Config.COMMON.MASS_COUNT_1_TAG),
        MASS_2(Config.COMMON.MASS_COUNT_2_TAG),
        MASS_3(Config.COMMON.MASS_COUNT_3_TAG),
        RATE_1(Config.COMMON.RATE_1_TAG),
        RATE_2(Config.COMMON.RATE_2_TAG),
        RATE_3(Config.COMMON.RATE_3_TAG)
        ;

        String key;
        ConfigKey(String key) { this.key = key; }
        public static ConfigKey getByString(String key) {
            for (ConfigKey v: ConfigKey.values()) {
                if (key.equalsIgnoreCase(v.key))
                    return v;
            }
            return INVALID_KEY;
        }

    }

    /**
     * Config access interface
     */
    public void putIntConfig(ConfigKey configKey, int v) {
        intMappings.put(configKey, v);
    }
    public int getIntConfig(ConfigKey configKey) {
        return intMappings.getOrDefault(configKey, 65535);
    }

    public int getIntConfig(FakeMob fakeMob, ConfigKey configKey) {
        if (MobOverride.get().hasIntMobOverride(fakeMob, configKey))
            return MobOverride.get().getIntMobOverride(fakeMob, configKey);

        return intMappings.getOrDefault(configKey, 65535);
    }

    public boolean hasMobOverride(FakeMob fakeMob, ConfigKey configKey) {
        return MobOverride.get().hasIntMobOverride(fakeMob, configKey);
    }

    /**
     * Debug only
     */
    public String getConfigAsString(FakeMob fakeMob, String key) {
        ConfigKey configKey = ConfigKey.getByString(key);
        if (configKey == ConfigKey.INVALID_KEY)
            return "";

        /**
         * Integer values
         */
        if (MobOverride.get().hasIntMobOverride(fakeMob, configKey))
            return Integer.toString(MobOverride.get().getIntMobOverride(fakeMob, configKey));

        return Integer.toString(getIntConfig(configKey));
    }

}
