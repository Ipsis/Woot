package ipsis.woot.manager;

import ipsis.woot.tileentity.ng.configuration.EnumConfigKey;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final ConfigManager INSTANCE = new ConfigManager();
    public static ConfigManager instance() { return INSTANCE; }

    private ConfigManager() { }

    private Map<EnumConfigKey, Integer> integerMap = new HashMap<>();
    private Map<EnumConfigKey, Boolean> booleanMap = new HashMap<>();

    public void setInteger(EnumConfigKey key, int v) {
        if (key.getClazz() == Integer.class)
            integerMap.put(key, v);
    }

    public void setBoolean(EnumConfigKey key, boolean v) {
        if (key.getClazz() == Boolean.class)
            booleanMap.put(key, v);
    }

    public int getInteger(EnumConfigKey key) {

        if (key.getClazz() == Integer.class)
            return integerMap.get(key);

        return 1;
    }

    public boolean getBoolean(EnumConfigKey key) {

        if (key.getClazz() == Boolean.class)
            return booleanMap.get(key);

        return false;
    }


}
