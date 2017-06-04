package ipsis.woot.manager;

import ipsis.woot.tileentity.ng.configuration.EnumConfigKey;
import ipsis.woot.util.WootMob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final ConfigManager INSTANCE = new ConfigManager();
    public static ConfigManager instance() { return INSTANCE; }

    private ConfigManager() { loadDefaults(); }

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

    public void loadDefaults() {

        List<EnumConfigKey> keys = EnumConfigKey.getBooleanKeys();
        for (EnumConfigKey k : keys)
            booleanMap.put(k, k.getDefaultBoolean());

        keys = EnumConfigKey.getIntegerKeys();
        for (EnumConfigKey k : keys)
            integerMap.put(k, k.getDefaultInteger());

        WootMob.addToInternalModBlacklist("cyberware");
        WootMob.addToInternalModBlacklist("botania");
        WootMob.addToInternalModBlacklist("withercrumbs");
        WootMob.addToInternalModBlacklist("draconicevolution");

        WootMob.addToInternalMobBlacklist("arsmagica2.Dryad");
        WootMob.addToInternalMobBlacklist("abyssalcraft.lesserdreadbeast");
        WootMob.addToInternalMobBlacklist("abyssalcraft.greaterdreadspawn");
        WootMob.addToInternalMobBlacklist("abyssalcraft.chagaroth");
        WootMob.addToInternalMobBlacklist("abyssalcraft.shadowboss");
        WootMob.addToInternalMobBlacklist("abyssalcraft.Jzahar");
        WootMob.addToInternalMobBlacklist("roots.spriteGuardian");
    }


}
