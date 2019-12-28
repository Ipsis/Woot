package ipsis.woot.config;

import ipsis.woot.common.configuration.Config;
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
        MASS_COUNT(MobOverride.MASS_COUNT_TAG),
        SPAWN_TICKS(MobOverride.SPAWN_TICKS_TAG),
        UNITS_PER_HEALTH(MobOverride.UNITS_PER_HEALTH_TAG),
        MOB_SHARD_KILLS(MobOverride.MOB_SHARD_KILLS_TAG),
        MOB_HEALTH(MobOverride.MOB_HEALTH_TAG),
        MOB_TIER(MobOverride.MOB_TIER_TAG),

        //CAPACITY_1(MobOverride.CAPACITY_1_TAG),
        //CAPACITY_2(MobOverride.CAPACITY_2_TAG),
        //CAPACITY_3(MobOverride.CAPACITY_3_TAG),
        EFFICIENCY_1(MobOverride.EFFICIENCY_1_TAG),
        EFFICIENCY_2(MobOverride.EFFICIENCY_2_TAG),
        EFFICIENCY_3(MobOverride.EFFICIENCY_3_TAG),
        MASS_1(MobOverride.MASS_COUNT_1_TAG),
        MASS_2(MobOverride.MASS_COUNT_2_TAG),
        MASS_3(MobOverride.MASS_COUNT_3_TAG),
        RATE_1(MobOverride.RATE_1_TAG),
        RATE_2(MobOverride.RATE_2_TAG),
        RATE_3(MobOverride.RATE_3_TAG)
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
