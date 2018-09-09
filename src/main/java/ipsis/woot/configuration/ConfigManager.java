package ipsis.woot.configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Configuration is set a
 *
 * Default value
 * death_xp = 500
 *
 * Custom value
 * death_xp.minecraft:ender_dragon = 6000
 */

public class ConfigManager {

    private static HashMap<String, Integer> integerHashMap = new HashMap<>();
    private static HashMap<String, Boolean> booleanHashMap = new HashMap<>();

    private static boolean canOverride(String key) {
        return false;
    }

    public static void addConfigValue(String key, int value) {
        integerHashMap.put(key.toLowerCase(), value);
    }

    public static void addConfigValue(String key, boolean value) {
        booleanHashMap.put(key.toLowerCase(), value);
    }

    public static int getConfigValue(String key, int value) {

        String[] s = splitKey(key);
        if (s.length == 1) {
            return integerHashMap.get(s[0]);
        } else {
            if (integerHashMap.containsKey(key)) {
                return integerHashMap.get(key);
            } else {
                return integerHashMap.get(s[0]);
            }
        }
    }

    private static void setDefaultValue(String key, int value, String desc, boolean allowMobOverride) {
    }

    private static void setDefaultValue(String key, boolean value, String desc, boolean allowMobOverride) {
    }

    private static final String SPLIT_CHAR = ".";
    private static String getKey(String s) {
        return s;
    }

    private static @Nullable String getMob(String s) {
        return null;
    }

    private static @Nonnull String[] splitKey(String key) {

        String s = key.toLowerCase();
        if (s.contains(".")) {
            return s.split(".");
        }

        return new String[] { key };

    }

    public enum ConfigKey {
        TARTARUS_ID,
        SAMPLE_SIZE,
        LEARN_TICKS,
        SPAWN_TICKS;

        public String getKey() {
            return this.name().toLowerCase();
        }

    }

    static {

        setDefaultValue(ConfigKey.TARTARUS_ID.getKey(), 418, "Dimension ID of tartarus", false);
        setDefaultValue(ConfigKey.SAMPLE_SIZE.getKey(), 500, "Number of mobs deaths to learn from", false);
        setDefaultValue(ConfigKey.LEARN_TICKS.getKey(), 20, "Number of ticks between learning kills", false);
        setDefaultValue(ConfigKey.SPAWN_TICKS.getKey(), 16 * 20, "Number of ticks between mob spawns", true);
    }


}
