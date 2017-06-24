package ipsis.woot.configuration;

import ipsis.woot.reference.Lang;
import ipsis.woot.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * User configurable settings
 */
public enum EnumConfigKey {

    SAMPLE_SIZE("sampleSize", Integer.class, 500, false),
    LEARN_TICKS("learnTicks", Integer.class, 20, false),
    SPAWN_TICKS("spawnTicks", Integer.class, 16 * 20, false),
    NUM_MOBS("numMobs", Integer.class, 1, false),
    KILL_COUNT("killCount", Integer.class, 1, true),
    SPAWN_XP("spawnXp", Integer.class, 1, true),
    DEATH_XP("deathXp", Integer.class, 1, true),
    FACTORY_TIER("factoryTier", Integer.class, -1, true), // calculated but can override
    T1_XP_MAX("t1XpMax", Integer.class, 5, false),
    T2_XP_MAX("t2XpMax", Integer.class, 20, false),
    T3_XP_MAX("t3XpMax", Integer.class, 49, false),
    T4_XP_MAX("t4XpMax", Integer.class, 65535, false),
    STRICT_POWER("strictPower", Boolean.class, false, false),
    STRICT_SPAWN("strictSpawn", Boolean.class, false, false),
    MOB_WHITELIST("mobWhitelist", Boolean.class, false, false),
    ALLOW_ENDER_DRAGON("allowEnderDragon", Boolean.class, true, false),
    ALLOW_XP_GENERATION("allowXpGeneration", Boolean.class, true, false),
    T1_POWER_MAX("t1PowerMax", Integer.class, 5000000, false),
    T2_POWER_MAX("t2PowerMax", Integer.class, 5000000, false),
    T3_POWER_MAX("t3PowerMax", Integer.class, 5000000, false),
    T4_POWER_MAX("t4PowerMax", Integer.class, 5000000, false),
    T1_POWER_RX_TICK("t1PowerRxTick", Integer.class, 100, false),
    T2_POWER_RX_TICK("t2PowerRxTick", Integer.class, 1000, false),
    T3_POWER_RX_TICK("t3PowerRxTick", Integer.class, 10000, false),
    T4_POWER_RX_TICK("t4PowerRxTick", Integer.class, Integer.MAX_VALUE, false),
    T1_POWER_TICK("t1PowerTick", Integer.class, 80, true),
    T2_POWER_TICK("t2PowerTick", Integer.class, 160, true),
    T3_POWER_TICK("t3PowerTick", Integer.class, 240, true),
    T4_POWER_TICK("t4PowerTick", Integer.class, 320, true),
    XP_POWER_TICK("xpPowerTick", Integer.class, 16, true),
    RATE_1_POWER_TICK("rate1PowerTick", Integer.class, 80, true),
    RATE_2_POWER_TICK("rate2PowerTick", Integer.class, 160, true),
    RATE_3_POWER_TICK("rate3PowerTick", Integer.class, 240, true),
    MASS_1_POWER_TICK("mass1PowerTick", Integer.class, 80, true),
    MASS_2_POWER_TICK("mass2PowerTick", Integer.class, 160, true),
    MASS_3_POWER_TICK("mass3PowerTick", Integer.class, 240, true),
    LOOTING_1_POWER_TICK("looting1PowerTick", Integer.class, 80, true),
    LOOTING_2_POWER_TICK("looting2PowerTick", Integer.class, 160, true),
    LOOTING_3_POWER_TICK("looting3PowerTick", Integer.class, 240, true),
    DECAP_1_POWER_TICK("decap1PowerTick", Integer.class, 80, true),
    DECAP_2_POWER_TICK("decap2PowerTick", Integer.class, 160, true),
    DECAP_3_POWER_TICK("decap3PowerTick", Integer.class, 240, true),
    XP_1_POWER_TICK("xp1PowerTick", Integer.class, 80, true),
    XP_2_POWER_TICK("xp2PowerTick", Integer.class, 160, true),
    XP_3_POWER_TICK("xp3PowerTick", Integer.class, 240, true),
    EFF_1_POWER_TICK("eff1PowerTick", Integer.class, 80, true),
    EFF_2_POWER_TICK("eff2PowerTick", Integer.class, 160, true),
    EFF_3_POWER_TICK("eff3PowerTick", Integer.class, 240, true),
    BM_1_POWER_TICK("bm1PowerTick", Integer.class, 80, true),
    BM_2_POWER_TICK("bm2PowerTick", Integer.class, 160, true),
    BM_3_POWER_TICK("bm3PowerTick", Integer.class, 240, true),
    RATE_1_PARAM("rate1Param", Integer.class, 25, true),
    RATE_2_PARAM("rate2Param", Integer.class, 30, true),
    RATE_3_PARAM("rate3Param", Integer.class, 50, true),
    MASS_1_PARAM("mass1Param", Integer.class, 2, true),
    MASS_2_PARAM("mass2Param", Integer.class, 4, true),
    MASS_3_PARAM("mass3Param", Integer.class, 6, true),
    LOOTING_1_PARAM("looting1Param", Integer.class, 1, false),
    LOOTING_2_PARAM("looting2Param", Integer.class, 2, false),
    LOOTING_3_PARAM("looting3Param", Integer.class, 3, false),
    DECAP_1_PARAM("decap1Param", Integer.class, 20, true),
    DECAP_2_PARAM("decap1Param", Integer.class, 40, true),
    DECAP_3_PARAM("decap1Param", Integer.class, 80, true),
    XP_1_PARAM("xp1Param", Integer.class, 20, true),
    XP_2_PARAM("xp2Param", Integer.class, 40, true),
    XP_3_PARAM("xp3Param", Integer.class, 80, true),
    EFF_1_PARAM("eff1Param", Integer.class, 15, true),
    EFF_2_PARAM("eff2Param", Integer.class, 25, true),
    EFF_3_PARAM("eff3Param", Integer.class, 30, true),
    BM_1_PARAM1("bm1Param1", Integer.class, 10, true),
    BM_2_PARAM1("bm2Param1", Integer.class, 20, true),
    BM_3_PARAM1("bm3Param1", Integer.class, 30, true),
    BM_1_PARAM2("bm1Param2", Integer.class, 20, true),
    BM_2_PARAM2("bm2Param2", Integer.class, 30, true),
    BM_3_PARAM2("bm3Param2", Integer.class, 40, true);

    private String text;
    private Class clazz;
    private int defaultInteger;
    private boolean defaultBoolean;
    private boolean mobOverride;
    EnumConfigKey(String text, Class clazz, int defaultValue, boolean mobOverride) {

        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
    }

    EnumConfigKey(String text, Class clazz, boolean defaultValue, boolean mobOverride) {

        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
    }

    public String getText() { return text; }
    public Class getClazz() { return clazz; }
    public int getDefaultInteger() { return defaultInteger; }
    public boolean getDefaultBoolean() { return defaultBoolean; }
    public boolean canMobOverride() { return mobOverride; }
    public String getTranslated() { return StringHelper.localize(Lang.TAG_CONFIG + text); }

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

    public static EnumConfigKey get(String name) {

        for (EnumConfigKey k : values())
            if (k.toString().equals(name))
                return k;

        return null;
    }
}
