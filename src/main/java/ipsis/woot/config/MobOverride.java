package ipsis.woot.config;

import ipsis.woot.policy.PolicyConfiguration;
import ipsis.woot.util.FakeMob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static String RATE_1_TAG = "rate1Increase";
    public static String RATE_2_TAG = "rate2Increase";
    public static String RATE_3_TAG = "rate3Increase";
    public static String MASS_COUNT_1_TAG = "mass1MobCount";
    public static String MASS_COUNT_2_TAG = "mass2MobCount";
    public static String MASS_COUNT_3_TAG = "mass3MobCount";
    public static String EFFICIENCY_1_TAG = "efficiency1Reduction";
    public static String EFFICIENCY_2_TAG = "efficiency2Reduction";
    public static String EFFICIENCY_3_TAG = "efficiency3Reduction";

    public static String MASS_COUNT_TAG = "massCount";
    public static String SPAWN_TICKS_TAG = "spawnTicks";
    public static String MOB_HEALTH_TAG = "mobHealth";
    public static String UNITS_PER_HEALTH_TAG = "unitsPerHealth";
    public static String MOB_TIER_TAG = "mobTier";
    public static String MOB_SHARD_KILLS_TAG = "mobShardKill";

    private static final List<String> VALID_OVERRIDE_TAGS = new ArrayList<String>(){{
        add(RATE_1_TAG);
        add(RATE_2_TAG);
        add(RATE_3_TAG);
        add(MASS_COUNT_1_TAG);
        add(MASS_COUNT_2_TAG);
        add(MASS_COUNT_3_TAG);
        add(EFFICIENCY_1_TAG);
        add(EFFICIENCY_2_TAG);
        add(EFFICIENCY_3_TAG);
        add(MASS_COUNT_TAG);
        add(SPAWN_TICKS_TAG);
        add(MOB_HEALTH_TAG);
        add(UNITS_PER_HEALTH_TAG);
        add(MOB_TIER_TAG);
        add(MOB_SHARD_KILLS_TAG);
    }};

    public void loadFromConfig() {

        LOGGER.info("Valid tags {}", VALID_OVERRIDE_TAGS.toString());

        LOGGER.debug("Loading configuration");
        for (String s : PolicyConfiguration.MOB_OVERRIDES.get()) {
            String[] parts = s.split(",");
            if (parts.length != 3) {
                LOGGER.error(s + " == INVALID");
            } else {
                String mob = parts[0];
                String param = parts[1];
                if (VALID_OVERRIDE_TAGS.contains(param)) {
                    try {
                        int v = Integer.valueOf(parts[2]);
                        FakeMob fakeMob = new FakeMob(mob);
                        if (fakeMob.isValid()) {
                            MobOverride.get().addIntOverride(fakeMob, WootConfig.ConfigKey.getByString(param), v);
                            //addIntMapping(fakeMob, param, v);
                        }
                        else
                            LOGGER.error(s + " == INVALID (invalid mob)");
                    } catch (NumberFormatException e) {
                        LOGGER.error(s + " == INVALID (invalid value)");
                    }
                } else {
                    LOGGER.error(s + " == INVALID (unknown param)");
                }
            }
        }
    }


}
