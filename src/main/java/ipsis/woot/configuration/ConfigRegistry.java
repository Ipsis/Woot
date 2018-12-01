package ipsis.woot.configuration;

import ipsis.woot.configuration.vanilla.UpgradeConfig;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helpers.LogHelper;

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

    private static HashMap<String, Integer> integerConfigMap = new HashMap<>();

    public enum Key {
        HEADHUNTER_1_DROP,
        HEADHUNTER_2_DROP,
        HEADHUNTER_3_DROP,
        MASS_1_NUM_MOBS,
        MASS_2_NUM_MOBS,
        MASS_3_NUM_MOBS,
        RATE_1_REDUCTION,
        RATE_2_REDUCTION,
        RATE_3_REDUCTION,
        XP_1_AMOUNT,
        XP_2_AMOUNT,
        XP_3_AMOUNT
    }

    public int getIntegerConfig(Key key) {
        return getIntegerDefault(key);
    }

    public int getIntegerConfig(Key key, FakeMobKey fakeMobKey) {
        return 1;
    }

    private int getIntegerDefault(Key key) {

        switch (key) {
            case HEADHUNTER_1_DROP:
                return UpgradeConfig.HEADHUNTER_1_DROP;
            case HEADHUNTER_2_DROP:
                return UpgradeConfig.HEADHUNTER_2_DROP;
            case HEADHUNTER_3_DROP:
                return UpgradeConfig.HEADHUNTER_3_DROP;
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
            default:
                LogHelper.error("ConfigRegistry unhandled key " + key);
                return 1;
        }
    }

}
