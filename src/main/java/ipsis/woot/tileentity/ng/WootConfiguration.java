package ipsis.woot.tileentity.ng;

import ipsis.woot.manager.ConfigManager;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class WootConfiguration implements IWootConfiguration {

    private Map<ConfigManager.EnumConfigKey, Integer> integerMap = new HashMap<>();
    private Map<ConfigManager.EnumConfigKey, Boolean> booleanMap = new HashMap<>();
    private Map<String, Integer> integerMobMap = new HashMap<>();
    private Map<String, Boolean> booleanMobMap = new HashMap<>();

    private String makeKey(WootMobName wootMobName, ConfigManager.EnumConfigKey key) {

        return wootMobName.toString() + ":" + key.toString();
    }

    @Override
    public boolean getBoolean(ConfigManager.EnumConfigKey key) {

        return booleanMap.get(key);
    }

    @Override
    public boolean getBoolean(WootMobName wootMobName, ConfigManager.EnumConfigKey key) {

        String mapKey = makeKey(wootMobName, key);
        if (booleanMobMap.containsKey(mapKey))
            return booleanMobMap.get(mapKey);

        return booleanMap.get(key);
    }

    @Override
    public int getInteger(ConfigManager.EnumConfigKey key) {

        return integerMap.get(key);
    }

    @Override
    public int getInteger(WootMobName wootMobName, ConfigManager.EnumConfigKey key) {

        String mapKey = makeKey(wootMobName, key);
        if (integerMobMap.containsKey(mapKey))
            return integerMobMap.get(mapKey);

        return integerMap.get(key);
    }

    @Override
    public void setBoolean(ConfigManager.EnumConfigKey key, boolean v) {

        booleanMap.put(key, v);
    }

    @Override
    public void setBoolean(WootMobName wootMobName, ConfigManager.EnumConfigKey key, boolean v) {

        booleanMobMap.put(makeKey(wootMobName, key), v);

    }

    @Override
    public void setInteger(ConfigManager.EnumConfigKey key, int v) {

        integerMap.put(key, v);
    }

    @Override
    public void setInteger(WootMobName wootMobName, ConfigManager.EnumConfigKey key, int v) {

        integerMobMap.put(makeKey(wootMobName, key), v);
    }

    @Override
    public void setInteger(WootMobName wootMobName, String key, int v) {

        ConfigManager.EnumConfigKey configKey = getKey(key);
        if (configKey != null) {
            integerMap.put(configKey, v);
        }
    }

    private static Map<String, ConfigManager.EnumConfigKey> keyMap = new HashMap<>();
    static {
        keyMap.put("SPAWN_TICK", ConfigManager.EnumConfigKey.SPAWN_TICKS);
        keyMap.put("SPAWN_XP", ConfigManager.EnumConfigKey.SPAWN_XP);
        keyMap.put("DEATH_XP", ConfigManager.EnumConfigKey.DEATH_XP);
        keyMap.put("FACTORY_TIER", ConfigManager.EnumConfigKey.FACTORY_TIER);
        keyMap.put("KILL_COUNT", ConfigManager.EnumConfigKey.KILL_COUNT);
    }

    private @Nullable ConfigManager.EnumConfigKey getKey(String key) {

        if (keyMap.containsKey(key))
            return keyMap.get(key);

        return null;
    }
}
