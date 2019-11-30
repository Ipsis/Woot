package ipsis.woot.common.configuration;

import ipsis.woot.util.FakeMob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class MobOverride {

    private static final Logger LOGGER = LogManager.getLogger();
    private static MobOverride INSTANCE = new MobOverride();
    public static MobOverride get() { return INSTANCE; }

    private static HashMap<FakeMob, HashMap<WootConfig.ConfigKey, Integer>> intMappings = new HashMap<>();

    public void addIntOverride(FakeMob fakeMob, WootConfig.ConfigKey configKey, int value) {
        if (!intMappings.containsKey(fakeMob))
            intMappings.put(fakeMob, new HashMap<>());

        intMappings.get(fakeMob).put(configKey, value);
        LOGGER.info("Added mob override {}:{} -> {}", fakeMob, configKey, value);
    }

    public boolean hasIntMobOverride(FakeMob fakeMob, WootConfig.ConfigKey configKey) {
        if (!intMappings.containsKey(fakeMob))
            return false;
        return intMappings.get(fakeMob).containsKey(configKey);
    }

    public int getIntMobOverride(FakeMob fakeMob, WootConfig.ConfigKey configKey) {
        if (!hasIntMobOverride(fakeMob, configKey))
            return 0;

        LOGGER.info("Get mob override {}:{}", fakeMob, configKey);
        return intMappings.get(fakeMob).get(configKey);
    }


}
