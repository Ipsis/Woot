package ipsis.woot.configuration;

import ipsis.woot.reference.Lang;
import ipsis.woot.util.StringHelper;

import java.util.ArrayList;
import java.util.EnumSet;
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
    T1_XP_MAX("t1XpMax", Integer.class, 5, false, 1),
    T2_XP_MAX("t2XpMax", Integer.class, 20, false, 2),
    T3_XP_MAX("t3XpMax", Integer.class, 49, false, 3),
    T4_XP_MAX("t4XpMax", Integer.class, 65535, false, 4),
    POWER_PER_XP("powerPerXp", Integer.class, 1, true),
    STRICT_POWER("strictPower", Boolean.class, false, false),
    STRICT_SPAWN("strictSpawn", Boolean.class, false, false),
    MOB_WHITELIST("mobWhitelist", Boolean.class, false, false),
    ALLOW_ENDER_DRAGON("allowEnderDragon", Boolean.class, true, false),
    ALLOW_XP_GENERATION("allowXpGeneration", Boolean.class, true, false),
    T1_POWER_MAX("t1PowerMax", Integer.class, 10000, false),
    T2_POWER_MAX("t2PowerMax", Integer.class, 1000000, false),
    T3_POWER_MAX("t3PowerMax", Integer.class, 10000000, false),
    T1_POWER_RX_TICK("t1PowerRxTick", Integer.class, 1000, false),
    T2_POWER_RX_TICK("t2PowerRxTick", Integer.class, 10000, false),
    T3_POWER_RX_TICK("t3PowerRxTick", Integer.class, Integer.MAX_VALUE, false),
    T1_POWER_TICK("t1PowerTick", Integer.class, 80, true, 1),
    T2_POWER_TICK("t2PowerTick", Integer.class, 160, true, 2),
    T3_POWER_TICK("t3PowerTick", Integer.class, 240, true, 3),
    T4_POWER_TICK("t4PowerTick", Integer.class, 320, true, 4),
    XP_POWER_TICK("xpPowerTick", Integer.class, 16, true),
    RATE_1_POWER_TICK("rate1PowerTick", Integer.class, 80, true, 1),
    RATE_2_POWER_TICK("rate2PowerTick", Integer.class, 160, true, 2),
    RATE_3_POWER_TICK("rate3PowerTick", Integer.class, 240, true, 3),
    MASS_1_POWER_TICK("mass1PowerTick", Integer.class, 80, true, 1),
    MASS_2_POWER_TICK("mass2PowerTick", Integer.class, 160, true, 2),
    MASS_3_POWER_TICK("mass3PowerTick", Integer.class, 240, true, 3),
    LOOTING_1_POWER_TICK("looting1PowerTick", Integer.class, 80, true, 1),
    LOOTING_2_POWER_TICK("looting2PowerTick", Integer.class, 160, true, 2),
    LOOTING_3_POWER_TICK("looting3PowerTick", Integer.class, 240, true, 3),
    DECAP_1_POWER_TICK("decap1PowerTick", Integer.class, 80, true, 1),
    DECAP_2_POWER_TICK("decap2PowerTick", Integer.class, 160, true, 2),
    DECAP_3_POWER_TICK("decap3PowerTick", Integer.class, 240, true, 3),
    XP_1_POWER_TICK("xp1PowerTick", Integer.class, 80, true, 1),
    XP_2_POWER_TICK("xp2PowerTick", Integer.class, 160, true, 2),
    XP_3_POWER_TICK("xp3PowerTick", Integer.class, 240, true, 3),
    EFF_1_POWER_TICK("eff1PowerTick", Integer.class, 80, true, 1),
    EFF_2_POWER_TICK("eff2PowerTick", Integer.class, 160, true, 2),
    EFF_3_POWER_TICK("eff3PowerTick", Integer.class, 240, true, 3),
    BM_1_POWER_TICK("bm1PowerTick", Integer.class, 80, true, 1),
    BM_2_POWER_TICK("bm2PowerTick", Integer.class, 160, true, 2),
    BM_3_POWER_TICK("bm3PowerTick", Integer.class, 240, true, 3),
    RATE_1_PARAM("rate1Param", Integer.class, 25, true, 1),
    RATE_2_PARAM("rate2Param", Integer.class, 30, true, 2),
    RATE_3_PARAM("rate3Param", Integer.class, 50, true, 3),
    MASS_1_PARAM("mass1Param", Integer.class, 2, true, 1),
    MASS_2_PARAM("mass2Param", Integer.class, 4, true, 2),
    MASS_3_PARAM("mass3Param", Integer.class, 6, true, 3),
    LOOTING_1_PARAM("looting1Param", Integer.class, 1, false, 1),
    LOOTING_2_PARAM("looting2Param", Integer.class, 2, false, 2),
    LOOTING_3_PARAM("looting3Param", Integer.class, 3, false, 3),
    DECAP_1_PARAM("decap1Param", Integer.class, 20, true, 1),
    DECAP_2_PARAM("decap1Param", Integer.class, 40, true, 2),
    DECAP_3_PARAM("decap1Param", Integer.class, 80, true, 3),
    XP_1_PARAM("xp1Param", Integer.class, 20, true, 1),
    XP_2_PARAM("xp2Param", Integer.class, 40, true, 2),
    XP_3_PARAM("xp3Param", Integer.class, 80, true, 3),
    EFF_1_PARAM("eff1Param", Integer.class, 15, true, 1),
    EFF_2_PARAM("eff2Param", Integer.class, 25, true, 2),
    EFF_3_PARAM("eff3Param", Integer.class, 30, true, 3),
    BM_1_PARAM1("bm1Param1", Integer.class, 10, true, 1),
    BM_2_PARAM1("bm2Param1", Integer.class, 20, true, 2),
    BM_3_PARAM1("bm3Param1", Integer.class, 30, true, 3),
    BM_1_PARAM2("bm1Param2", Integer.class, 20, true, 1),
    BM_2_PARAM2("bm2Param2", Integer.class, 30, true, 2),
    BM_3_PARAM2("bm3Param2", Integer.class, 40, true, 3);

    private String text;
    private Class clazz;
    private int defaultInteger;
    private boolean defaultBoolean;
    private boolean mobOverride;
    private int level;
    EnumConfigKey(String text, Class clazz, int defaultValue, boolean mobOverride, int level) {

        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
        this.level = level;
    }

    EnumConfigKey(String text, Class clazz, int defaultValue, boolean mobOverride) {

        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
        this.level = -1;
    }

    EnumConfigKey(String text, Class clazz, boolean defaultValue, boolean mobOverride) {

        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
        this.level = -1;
    }

    EnumConfigKey(String text, Class clazz, boolean defaultValue, boolean mobOverride, int level) {

        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
        this.level = level;
    }

    public String getText() { return text; }
    public Class getClazz() { return clazz; }
    public int getDefaultInteger() { return defaultInteger; }
    public boolean getDefaultBoolean() { return defaultBoolean; }
    public boolean canMobOverride() { return mobOverride; }
    public String getTranslated() { return StringHelper.localize(Lang.TAG_CONFIG + text); }
    public int getLevel() { return level; }

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
            if (k.toString().equalsIgnoreCase(name))
                return k;

        return null;
    }

    public static EnumSet<EnumConfigKey> POWER_PER_TICK = EnumSet.of(T1_POWER_TICK, T2_POWER_TICK, T3_POWER_TICK, T4_POWER_TICK);
    public static EnumSet<EnumConfigKey> RATE_POWER_PER_TICK = EnumSet.of(RATE_1_POWER_TICK, RATE_2_POWER_TICK, RATE_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> MASS_POWER_PER_TICK = EnumSet.of(MASS_1_POWER_TICK, MASS_2_POWER_TICK, MASS_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> LOOTING_POWER_PER_TICK = EnumSet.of(LOOTING_1_POWER_TICK, LOOTING_2_POWER_TICK, LOOTING_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> DECAP_POWER_PER_TICK = EnumSet.of(DECAP_1_POWER_TICK, DECAP_2_POWER_TICK, DECAP_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> XP_POWER_PER_TICK = EnumSet.of(XP_1_POWER_TICK, XP_2_POWER_TICK, XP_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> EFF_POWER_PER_TICK = EnumSet.of(EFF_1_POWER_TICK, EFF_2_POWER_TICK, EFF_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> BM_POWER_PER_TICK = EnumSet.of(BM_1_POWER_TICK, BM_2_POWER_TICK, BM_3_POWER_TICK);

    public static EnumSet<EnumConfigKey> RATE_PARAM = EnumSet.of(RATE_1_PARAM, RATE_2_PARAM, RATE_3_PARAM);
    public static EnumSet<EnumConfigKey> MASS_PARAM = EnumSet.of(MASS_1_PARAM, MASS_2_PARAM, MASS_3_PARAM);
    public static EnumSet<EnumConfigKey> LOOTING_PARAM = EnumSet.of(LOOTING_1_PARAM, LOOTING_2_PARAM, LOOTING_3_PARAM);
    public static EnumSet<EnumConfigKey> DECAP_PARAM = EnumSet.of(DECAP_1_PARAM, DECAP_2_PARAM, DECAP_3_PARAM);
    public static EnumSet<EnumConfigKey> XP_PARAM = EnumSet.of(XP_1_PARAM, XP_2_PARAM, XP_3_PARAM);
    public static EnumSet<EnumConfigKey> EFF_PARAM = EnumSet.of(EFF_1_PARAM, EFF_2_PARAM, EFF_3_PARAM);
    public static EnumSet<EnumConfigKey> BM_PARAM_1 = EnumSet.of(BM_1_PARAM1, BM_2_PARAM1, BM_3_PARAM1);
    public static EnumSet<EnumConfigKey> BM_PARAM_2 = EnumSet.of(BM_1_PARAM2, BM_2_PARAM2, BM_3_PARAM2);
}
