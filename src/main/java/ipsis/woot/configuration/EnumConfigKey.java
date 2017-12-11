package ipsis.woot.configuration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * User configurable settings
 */
public enum EnumConfigKey {

    SIMPLE_LAYOUT(
            "Render the layout as colors",
            "simpleLayout",
            Boolean.class,
            false,
            false),
    SAMPLE_SIZE(
            "Number of mobs to kill to learn from - higher is more accurate",
            "sampleSize", Integer.class, 500, false),
    LEARN_TICKS(
            "Number of ticks between fake kills",
            "learnTicks", Integer.class, 20, false),
    SPAWN_TICKS(
            "Default number of ticks to spawn a mob",
            "spawnTicks", Integer.class, 16 * 20, true),
    HEADHUNTER_1_CHANCE(
            "Percentage chance of headhunter I enchant dropping a skull",
            "headhunter1DropChance", Integer.class, 30, false),
    HEADHUNTER_2_CHANCE(
            "Percentage chance of headhunter II enchant dropping a skull",
            "headhunter2DropChance", Integer.class, 50, false),
    HEADHUNTER_3_CHANCE(
            "Percentage chance of headhunter III enchant dropping a skull",
            "headhunter3DropChance", Integer.class, 80, false),
    NUM_MOBS(
            "Default number of mobs to spawn",
            "numMobs", Integer.class, 1, false),
    KILL_COUNT(
            "Default number of mobs to kill to program the ender shard",
            "killCount", Integer.class, 1, true),
    SPAWN_XP(
            "Default XP cost for a spawned mob",
            "spawnXp", Integer.class, 1, true),
    DEATH_XP(
            "Default XP to generate for a killed mob",
            "deathXp", Integer.class, 1, true),
    MASS_FX(
            "Function to apply to generate the power cost when more than one mob spawned. (0-linear), (1-powers of 2), (2-powers of 3)",
            "massFx", Integer.class, 0, true),
    FACTORY_TIER(
            "Default tier to assign mobs to",
            "factoryTier", Integer.class, -1, true), // calculated but can override
    T1_XP_MAX(
            "Maximum XP level for a Tier I factory",
            "t1XpMax", Integer.class, 5, false, 1),
    T2_XP_MAX(
            "Maximum XP level for a Tier II factory",
            "t2XpMax", Integer.class, 20, false, 2),
    T3_XP_MAX(
            "Maximum XP level for a Tier III factory",
            "t3XpMax", Integer.class, 49, false, 3),
    T4_XP_MAX(
            "Maximum XP level for a Tier IV factory",
            "t4XpMax", Integer.class, 65535, false, 4),
    POWER_PER_XP(
            "Power cost per level of XP",
            "powerPerXp", Integer.class, 1, true),
    ALLOW_XP_GENERATION(
            "Allow the factory to generate XP shards",
            "allowXpGeneration", Boolean.class, true, false),
    T1_POWER_MAX(
            "Maximum power stored in a Tier I cell",
            "t1PowerMax", Integer.class, 100000, false),
    T2_POWER_MAX(
            "Maximum power stored in a Tier II cell",
            "t2PowerMax", Integer.class, 1000000, false),
    T3_POWER_MAX(
            "Maximum power stored in a Tier III cell",
            "t3PowerMax", Integer.class, 10000000, false),
    T1_POWER_RX_TICK(
            "Receive power limit for a Tier I cell",
            "t1PowerRxTick", Integer.class, 1000, false),
    T2_POWER_RX_TICK(
            "Receive power limit for a Tier II cell",
            "t2PowerRxTick", Integer.class, 10000, false),
    T3_POWER_RX_TICK(
            "Receive power limit for a Tier III cell",
            "t3PowerRxTick", Integer.class, Integer.MAX_VALUE, false),
    T1_POWER_TICK(
            "Default power cost per tick of a Tier I factory",
            "t1PowerTick", Integer.class, 80, true, 1),
    T2_POWER_TICK(
            "Default power cost per tick of a Tier II factory",
            "t2PowerTick", Integer.class, 160, true, 2),
    T3_POWER_TICK(
            "Default power cost per tick of a Tier III factory",
            "t3PowerTick", Integer.class, 240, true, 3),
    T4_POWER_TICK(
            "Default power cost per tick of a Tier IV factory",
            "t4PowerTick", Integer.class, 320, true, 4),
    XP_POWER_TICK(
            "Default power cost per tick per XP level",
            "xpPowerTick", Integer.class, 16, true),
    RATE_1_POWER_TICK(
            "Default power cost per tick of a Rate I upgrade",
            "rate1PowerTick", Integer.class, 80, true, 1),
    RATE_2_POWER_TICK(
            "Default power cost per tick of a Rate II upgrade",
            "rate2PowerTick", Integer.class, 160, true, 2),
    RATE_3_POWER_TICK(
            "Default power cost per tick of a Rate III upgrade",
            "rate3PowerTick", Integer.class, 240, true, 3),
    MASS_1_POWER_TICK(
            "Default power cost per tick of a Mass I upgrade",
            "mass1PowerTick", Integer.class, 80, true, 1),
    MASS_2_POWER_TICK(
            "Default power cost per tick of a Mass II upgrade",
            "mass2PowerTick", Integer.class, 160, true, 2),
    MASS_3_POWER_TICK(
            "Default power cost per tick of a Mass III upgrade",
            "mass3PowerTick", Integer.class, 240, true, 3),
    LOOTING_1_POWER_TICK(
            "Default power cost per tick of a Looting I upgrade",
            "looting1PowerTick", Integer.class, 80, true, 1),
    LOOTING_2_POWER_TICK(
            "Default power cost per tick of a Looting II upgrade",
            "looting2PowerTick", Integer.class, 160, true, 2),
    LOOTING_3_POWER_TICK(
            "Default power cost per tick of a Looting III upgrade",
            "looting3PowerTick", Integer.class, 240, true, 3),
    DECAP_1_POWER_TICK(
            "Default power cost per tick of a Decapitate I upgrade",
            "decap1PowerTick", Integer.class, 80, true, 1),
    DECAP_2_POWER_TICK(
            "Default power cost per tick of a Decapitate II upgrade",
            "decap2PowerTick", Integer.class, 160, true, 2),
    DECAP_3_POWER_TICK(
            "Default power cost per tick of a Decapitate III upgrade",
            "decap3PowerTick", Integer.class, 240, true, 3),
    XP_1_POWER_TICK(
            "Default power cost per tick of a XP I upgrade",
            "xp1PowerTick", Integer.class, 80, true, 1),
    XP_2_POWER_TICK(
            "Default power cost per tick of a XP II upgrade",
            "xp2PowerTick", Integer.class, 160, true, 2),
    XP_3_POWER_TICK(
            "Default power cost per tick of a XP III upgrade",
            "xp3PowerTick", Integer.class, 240, true, 3),
    EFF_1_POWER_TICK(
            "Default power cost per tick of a Efficiency I upgrade",
            "eff1PowerTick", Integer.class, 80, true, 1),
    EFF_2_POWER_TICK(
            "Default power cost per tick of a Efficiency II upgrade",
            "eff2PowerTick", Integer.class, 160, true, 2),
    EFF_3_POWER_TICK(
            "Default power cost per tick of a Efficiency III upgrade",
            "eff3PowerTick", Integer.class, 240, true, 3),
    BM_1_POWER_TICK(
            "Default power cost per tick of a Blood Magic Life Essence I upgrade",
            "bm1PowerTick", Integer.class, 80, true, 1),
    BM_2_POWER_TICK(
            "Default power cost per tick of a Blood Magic Life Essence II upgrade",
            "bm2PowerTick", Integer.class, 160, true, 2),
    BM_3_POWER_TICK(
            "Default power cost per tick of a Blood Magic Life Essence III upgrade",
            "bm3PowerTick", Integer.class, 240, true, 3),
    RATE_1_PARAM(
            "Percentage reduction in spawn time for Rate I upgrade",
            "rate1Param", Integer.class, 20, true, 1),
    RATE_2_PARAM(
            "Percentage reduction in spawn time for Rate II upgrade",
            "rate2Param", Integer.class, 35, true, 2),
    RATE_3_PARAM(
            "Percentage reduction in spawn time for Rate III upgrade",
            "rate3Param", Integer.class, 50, true, 3),
    MASS_1_PARAM(
            "Default number of mobs to spawn for Mass I upgrade",
            "mass1Param", Integer.class, 2, true, 1),
    MASS_2_PARAM(
            "Default number of mobs to spawn for Mass II upgrade",
            "mass2Param", Integer.class, 4, true, 2),
    MASS_3_PARAM(
            "Default number of mobs to spawn for Mass III upgrade",
            "mass3Param", Integer.class, 6, true, 3),
    LOOTING_1_PARAM(
            "",
            "looting1Param", Integer.class, 1, false, 1),
    LOOTING_2_PARAM(
            "",
            "looting2Param", Integer.class, 2, false, 2),
    LOOTING_3_PARAM(
            "",
            "looting3Param", Integer.class, 3, false, 3),
    DECAP_1_PARAM(
            "Default chance to generate a skull for Decapitate I upgrade",
            "decap1Param", Integer.class, 20, true, 1),
    DECAP_2_PARAM(
            "Default chance to generate a skull for Decapitate II upgrade",
            "decap2Param", Integer.class, 40, true, 2),
    DECAP_3_PARAM(
            "Default chance to generate a skull for Decapitate III upgrade",
            "decap3Param", Integer.class, 80, true, 3),
    XP_1_PARAM(
            "Percentage of XP generated for XP I upgrade",
            "xp1Param", Integer.class, 100, true, 1),
    XP_2_PARAM(
            "Percentage of XP generated for XP II upgrade",
            "xp2Param", Integer.class, 140, true, 2),
    XP_3_PARAM(
            "Percentage of XP generated for XP III upgrade",
            "xp3Param", Integer.class, 180, true, 3),
    EFF_1_PARAM(
            "Percentage of power saved for a Efficiency I upgrade",
            "eff1Param", Integer.class, 15, true, 1),
    EFF_2_PARAM(
            "Percentage of power saved for a Efficiency II upgrade",
            "eff2Param", Integer.class, 25, true, 2),
    EFF_3_PARAM(
            "Percentage of power saved for a Efficiency III upgrade",
            "eff3Param", Integer.class, 30, true, 3),
    BM_1_PARAM1(
            "",
            "bm1Param1", Integer.class, 10, true, 1),
    BM_2_PARAM1(
            "",
            "bm2Param1", Integer.class, 20, true, 2),
    BM_3_PARAM1(
            "",
            "bm3Param1", Integer.class, 30, true, 3),
    BM_1_PARAM2(
            "",
            "bm1Param2", Integer.class, 20, true, 1),
    BM_2_PARAM2(
            "",
            "bm2Param2", Integer.class, 30, true, 2),
    BM_3_PARAM2(
            "",
            "bm3Param2", Integer.class, 40, true, 3);

    private String text;
    private Class clazz;
    private int defaultInteger;
    private boolean defaultBoolean;
    private boolean mobOverride;
    private int level;
    private String comment;
    EnumConfigKey(String comment, String text, Class clazz, int defaultValue, boolean mobOverride, int level) {

        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
        this.level = level;
    }

    EnumConfigKey(String comment, String text, Class clazz, int defaultValue, boolean mobOverride) {

        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
        this.level = -1;
    }

    EnumConfigKey(String comment, String text, Class clazz, boolean defaultValue, boolean mobOverride) {

        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
        this.level = -1;
    }

    EnumConfigKey(String comment, String text, Class clazz, boolean defaultValue, boolean mobOverride, int level) {

        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
        this.level = level;
    }

    public String getText() { return text; }
    public String getComment() { return comment; }
    public Class getClazz() { return clazz; }
    public int getDefaultInteger() { return defaultInteger; }
    public boolean getDefaultBoolean() { return defaultBoolean; }
    public boolean canMobOverride() { return mobOverride; }
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
