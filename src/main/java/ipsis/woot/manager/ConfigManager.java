package ipsis.woot.manager;

import ipsis.woot.reference.Lang;
import ipsis.woot.util.StringHelper;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobName;

import java.util.ArrayList;
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


    /**
     * User configurable settings
     */
    public enum EnumConfigKey {

        SAMPLE_SIZE("sampleSize", Integer.class, 500),
        LEARN_TICKS("learnTicks", Integer.class, 20),
        SPAWN_TICKS("spawnTicks", Integer.class, 16 * 20),
        NUM_MOBS("numMobs", Integer.class, 1),
        KILL_COUNT("killCount", Integer.class, 1),
        SPAWN_XP("spawnXp", Integer.class, 1),
        DEATH_XP("deathXp", Integer.class, 1),
        FACTORY_TIER("factoryTier", Integer.class, -1), // calculated but can override
        T1_XP_MAX("t1XpMax", Integer.class, 5),
        T2_XP_MAX("t2XpMax", Integer.class, 20),
        T3_XP_MAX("t3XpMax", Integer.class, 49),
        T4_XP_MAX("t4XpMax", Integer.class, 65535),
        STRICT_POWER("strictPower", Boolean.class, false),
        STRICT_SPAWN("strictSpawn", Boolean.class, false),
        MOB_WHITELIST("mobWhitelist", Boolean.class, false),
        ALLOW_ENDER_DRAGON("allowEnderDragon", Boolean.class, true),
        ALLOW_XP_GENERATION("allowXpGeneration", Boolean.class, true),
        MAX_POWER("maxPower", Integer.class, 10000000),
        T1_POWER_TICK("t1PowerTick", Integer.class, 80),
        T2_POWER_TICK("t2PowerTick", Integer.class, 160),
        T3_POWER_TICK("t3PowerTick", Integer.class, 240),
        T4_POWER_TICK("t4PowerTick", Integer.class, 320),
        XP_POWER_TICK("xpPowerTick", Integer.class, 16),
        RATE_1_POWER_TICK("rate1PowerTick", Integer.class, 80),
        RATE_2_POWER_TICK("rate2PowerTick", Integer.class, 160),
        RATE_3_POWER_TICK("rate3PowerTick", Integer.class, 240),
        MASS_1_POWER_TICK("mass1PowerTick", Integer.class, 80),
        MASS_2_POWER_TICK("mass2PowerTick", Integer.class, 160),
        MASS_3_POWER_TICK("mass3PowerTick", Integer.class, 240),
        LOOTING_1_POWER_TICK("looting1PowerTick", Integer.class, 80),
        LOOTING_2_POWER_TICK("looting2PowerTick", Integer.class, 160),
        LOOTING_3_POWER_TICK("looting3PowerTick", Integer.class, 240),
        DECAP_1_POWER_TICK("decap1PowerTick", Integer.class, 80),
        DECAP_2_POWER_TICK("decap2PowerTick", Integer.class, 160),
        DECAP_3_POWER_TICK("decap3PowerTick", Integer.class, 240),
        XP_1_POWER_TICK("xp1PowerTick", Integer.class, 80),
        XP_2_POWER_TICK("xp2PowerTick", Integer.class, 160),
        XP_3_POWER_TICK("xp3PowerTick", Integer.class, 240),
        EFF_1_POWER_TICK("eff1PowerTick", Integer.class, 80),
        EFF_2_POWER_TICK("eff2PowerTick", Integer.class, 160),
        EFF_3_POWER_TICK("eff3PowerTick", Integer.class, 240),
        BM_1_POWER_TICK("bm1PowerTick", Integer.class, 80),
        BM_2_POWER_TICK("bm2PowerTick", Integer.class, 160),
        BM_3_POWER_TICK("bm3PowerTick", Integer.class, 240),
        RATE_1_PARAM("rate1Param", Integer.class, 25),
        RATE_2_PARAM("rate2Param", Integer.class, 30),
        RATE_3_PARAM("rate3Param", Integer.class, 50),
        MASS_1_PARAM("mass1Param", Integer.class, 2),
        MASS_2_PARAM("mass2Param", Integer.class, 4),
        MASS_3_PARAM("mass3Param", Integer.class, 6),
        LOOTING_1_PARAM("looting1Param", Integer.class, 1),
        LOOTING_2_PARAM("looting2Param", Integer.class, 2),
        LOOTING_3_PARAM("looting3Param", Integer.class, 3),
        DECAP_1_PARAM("decap1Param", Integer.class, 20),
        DECAP_2_PARAM("decap1Param", Integer.class, 40),
        DECAP_3_PARAM("decap1Param", Integer.class, 80),
        XP_1_PARAM("xp1Param", Integer.class, 20),
        XP_2_PARAM("xp2Param", Integer.class, 40),
        XP_3_PARAM("xp3Param", Integer.class, 80),
        EFF_1_PARAM("eff1Param", Integer.class, 15),
        EFF_2_PARAM("eff2Param", Integer.class, 25),
        EFF_3_PARAM("eff3Param", Integer.class, 30),
        BM_1_PARAM1("bm1Param1", Integer.class, 10),
        BM_2_PARAM1("bm2Param1", Integer.class, 20),
        BM_3_PARAM1("bm3Param1", Integer.class, 30),
        BM_1_PARAM2("bm1Param2", Integer.class, 20),
        BM_2_PARAM2("bm2Param2", Integer.class, 30),
        BM_3_PARAM2("bm3Param2", Integer.class, 40);

        private String text;
        private Class clazz;
        private int defaultInteger;
        private boolean defaultBoolean;
        EnumConfigKey(String text, Class clazz, int defaultValue) {

            this.text = text;
            this.clazz = clazz;
            this.defaultInteger = defaultValue;
            this.defaultBoolean = false;
        }

        EnumConfigKey(String text, Class clazz, boolean defaultValue) {

            this.text = text;
            this.clazz = clazz;
            this.defaultInteger = 1;
            this.defaultBoolean = defaultValue;
        }

        public String getText() { return this.text; }
        public Class getClazz() { return this.clazz; }
        public int getDefaultInteger() { return this.defaultInteger; }
        public boolean getDefaultBoolean() { return this.defaultBoolean; }
        public String getTranslated() { return StringHelper.localize(Lang.TAG_CONFIG + this.text); }

        public static List<EnumConfigKey> getIntegerKeys() {

            List<EnumConfigKey> keys = new ArrayList<>();
            for (EnumConfigKey k : values())
                if (k.getClazz() == Integer.class)
                    keys.add(k);

            return keys;
        }

        public static List<EnumConfigKey> getBooleanKeys() {

            List<EnumConfigKey> keys = new ArrayList<>();
            for (EnumConfigKey k : values())
                if (k.getClazz() == Boolean.class)
                    keys.add(k);

            return keys;
        }
    }
}
