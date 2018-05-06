package ipsis.woot.configuration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static ipsis.woot.reference.Config.*;
import static net.minecraftforge.common.config.Configuration.CATEGORY_CLIENT;
import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

/**
 * User configurable settings
 */
public enum EnumConfigKey {

    SIMPLE_LAYOUT(
            CATEGORY_CLIENT,
            "Render the layout as colors",
            "simpleLayout",
            Boolean.class,
            false,
            false),
    TARTARUS_ID(
            CATEGORY_GENERAL,
            "Dimension id of Tartarus",
            "tartarusId",
            Integer.class,
            418,
            false),
    ALLOW_ANVIL_VANILLA_CRAFTING(
            CATEGORY_GENERAL,
            "Add vanilla recipes to duplicate most anvil ones",
            "allowAnvilVanillaCrafting",
            Boolean.class,
            false,
            false
    ),
    SAMPLE_SIZE(
            CATEGORY_GENERAL,
            "Number of mobs to kill to learn from - higher is more accurate",
            "sampleSize", Integer.class, 500, false),
    LEARN_TICKS(
            CATEGORY_GENERAL,
            "Number of ticks between fake kills",
            "learnTicks", Integer.class, 20, false),
    SPAWN_TICKS(
            CATEGORY_FACTORY_GENERAL,
            "Default number of ticks to spawn a mob",
            "spawnTicks", Integer.class, 16 * 20, true),
    STRICT_POWER(
            CATEGORY_FACTORY_POWER,
            "Factory needs at least the power per tick or power is consumed and thrown away",
            "strictPower", Boolean.class, false, false),
    HEADHUNTER_1_CHANCE(
            CATEGORY_GENERAL,
            "Percentage chance of headhunter I enchant dropping a skull",
            "headhunter1DropChance", Integer.class, 30, false),
    HEADHUNTER_2_CHANCE(
            CATEGORY_GENERAL,
            "Percentage chance of headhunter II enchant dropping a skull",
            "headhunter2DropChance", Integer.class, 50, false),
    HEADHUNTER_3_CHANCE(
            CATEGORY_GENERAL,
            "Percentage chance of headhunter III enchant dropping a skull",
            "headhunter3DropChance", Integer.class, 80, false),
    NUM_MOBS(
            CATEGORY_FACTORY_GENERAL,
            "Default number of mobs to spawn",
            "numMobs", Integer.class, 1, false),
    KILL_COUNT(
            CATEGORY_FACTORY_GENERAL,
            "Default number of mobs to kill to program the ender shard",
            "killCount", Integer.class, 1, true),
    SPAWN_UNITS(
            CATEGORY_FACTORY_GENERAL,
            "Default units for spawning a mob",
            "spawnUnits", Integer.class, 1, true),
    DEATH_XP(
            CATEGORY_FACTORY_GENERAL,
            "Default XP to generate for a killed mob",
            "deathXp", Integer.class, 1, true),
    MASS_FX(
            CATEGORY_FACTORY_GENERAL,
            "Function to apply to generate the power cost when more than one mob spawned. (0-linear), (1-powers of 2), (2-powers of 3)",
            "massFx", Integer.class, 0, true),
    FACTORY_TIER(
            CATEGORY_FACTORY_GENERAL,
            "Default tier to assign mobs to",
            "factoryTier", Integer.class, -1, true), // calculated but can override
    T1_UNITS_MAX(
            CATEGORY_FACTORY_GENERAL,
            "Maximum units for a Tier I factory",
            "t1UnitsMax", Integer.class, 20, false, 1),
    T2_UNITS_MAX(
            CATEGORY_FACTORY_GENERAL,
            "Maximum units for a Tier II factory",
            "t2UnitsMax", Integer.class, 40, false, 2),
    T3_UNITS_MAX(
            CATEGORY_FACTORY_GENERAL,
            "Maximum units for a Tier III factory",
            "t3UnitsMax", Integer.class, 60, false, 3),
    T4_UNITS_MAX(
            CATEGORY_FACTORY_GENERAL,
            "Maximum units for a Tier IV factory",
            "t4CostMax", Integer.class, 65535, false, 4),
    POWER_PER_UNIT(
            CATEGORY_FACTORY_POWER,
            "Power cost per spawn unit",
            "powerPerSpawnUnit", Integer.class, 1, true),
    ALLOW_XP_GENERATION(
            CATEGORY_FACTORY_GENERAL,
            "Allow the factory to generate XP shards",
            "allowXpGeneration", Boolean.class, true, false),
    T1_POWER_MAX(
            CATEGORY_FACTORY_POWER,
            "Maximum power stored in a Tier I cell",
            "t1PowerMax", Integer.class, 100000, false),
    T2_POWER_MAX(
            CATEGORY_FACTORY_POWER,
            "Maximum power stored in a Tier II cell",
            "t2PowerMax", Integer.class, 1000000, false),
    T3_POWER_MAX(
            CATEGORY_FACTORY_POWER,
            "Maximum power stored in a Tier III cell",
            "t3PowerMax", Integer.class, 10000000, false),
    T1_POWER_RX_TICK(
            CATEGORY_FACTORY_POWER,
            "Receive power limit for a Tier I cell",
            "t1PowerRxTick", Integer.class, 1000, false),
    T2_POWER_RX_TICK(
            CATEGORY_FACTORY_POWER,
            "Receive power limit for a Tier II cell",
            "t2PowerRxTick", Integer.class, 10000, false),
    T3_POWER_RX_TICK(
            CATEGORY_FACTORY_POWER,
            "Receive power limit for a Tier III cell",
            "t3PowerRxTick", Integer.class, Integer.MAX_VALUE, false),
    T1_POWER_TICK(
            CATEGORY_FACTORY_POWER,
            "Default power cost per tick of a Tier I factory",
            "t1PowerTick", Integer.class, 80, true, 1),
    T2_POWER_TICK(
            CATEGORY_FACTORY_POWER,
            "Default power cost per tick of a Tier II factory",
            "t2PowerTick", Integer.class, 160, true, 2),
    T3_POWER_TICK(
            CATEGORY_FACTORY_POWER,
            "Default power cost per tick of a Tier III factory",
            "t3PowerTick", Integer.class, 240, true, 3),
    T4_POWER_TICK(
            CATEGORY_FACTORY_POWER,
            "Default power cost per tick of a Tier IV factory",
            "t4PowerTick", Integer.class, 320, true, 4),
    RATE_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Rate I upgrade",
            "rate1PowerTick", Integer.class, 80, true, 1),
    RATE_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Rate II upgrade",
            "rate2PowerTick", Integer.class, 160, true, 2),
    RATE_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Rate III upgrade",
            "rate3PowerTick", Integer.class, 240, true, 3),
    MASS_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Mass I upgrade",
            "mass1PowerTick", Integer.class, 80, true, 1),
    MASS_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Mass II upgrade",
            "mass2PowerTick", Integer.class, 160, true, 2),
    MASS_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Mass III upgrade",
            "mass3PowerTick", Integer.class, 240, true, 3),
    LOOTING_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Looting I upgrade",
            "looting1PowerTick", Integer.class, 80, true, 1),
    LOOTING_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Looting II upgrade",
            "looting2PowerTick", Integer.class, 160, true, 2),
    LOOTING_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Looting III upgrade",
            "looting3PowerTick", Integer.class, 240, true, 3),
    DECAP_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Decapitate I upgrade",
            "decap1PowerTick", Integer.class, 80, true, 1),
    DECAP_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Decapitate II upgrade",
            "decap2PowerTick", Integer.class, 160, true, 2),
    DECAP_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Decapitate III upgrade",
            "decap3PowerTick", Integer.class, 240, true, 3),
    XP_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a XP I upgrade",
            "xp1PowerTick", Integer.class, 80, true, 1),
    XP_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a XP II upgrade",
            "xp2PowerTick", Integer.class, 160, true, 2),
    XP_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a XP III upgrade",
            "xp3PowerTick", Integer.class, 240, true, 3),
    EFF_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Efficiency I upgrade",
            "eff1PowerTick", Integer.class, 1, true, 1),
    EFF_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Efficiency II upgrade",
            "eff2PowerTick", Integer.class, 2, true, 2),
    EFF_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a Efficiency III upgrade",
            "eff3PowerTick", Integer.class, 4, true, 3),
    BM_LE_TANK_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic LifeEssence Tank I upgrade",
            "bmLeTank1PowerTick", Integer.class, 80, true, 1),
    BM_LE_TANK_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic LifeEssence Tank II upgrade",
            "bmLeTank1PowerTick", Integer.class, 160, true, 2),
    BM_LE_TANK_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic LifeEssence Tank III upgrade",
            "bmLeTank1PowerTick", Integer.class, 240, true, 3),
    BM_LE_ALTAR_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic LifeEssence Altar I upgrade",
            "bmLeAltar1PowerTick", Integer.class, 80, true, 1),
    BM_LE_ALTAR_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic LifeEssence Altar II upgrade",
            "bmLeAltar2PowerTick", Integer.class, 160, true, 2),
    BM_LE_ALTAR_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic LifeEssence Altar III upgrade",
            "bmLeAltar3PowerTick", Integer.class, 240, true, 3),
    BM_CRYSTAL_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic Crystal I upgrade",
            "bmCrystal1PowerTick", Integer.class, 80, true, 1),
    BM_CRYSTAL_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic Crystal II upgrade",
            "bmCrystal2PowerTick", Integer.class, 160, true, 2),
    BM_CRYSTAL_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a BloodMagic Crystal III upgrade",
            "bmCrystal3PowerTick", Integer.class, 240, true, 3),
    EC_BLOOD_1_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a EvilCraft Blood Tank I upgrade",
            "ecBlood1PowerTick", Integer.class, 80, true, 1),
    EC_BLOOD_2_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a EvilCraft Blood Tank II upgrade",
            "ecBlood2PowerTick", Integer.class, 160, true, 2),
    EC_BLOOD_3_POWER_TICK(
            CATEGORY_UPGRADES,
            "Default power cost per tick of a EvilCraft Blood Tank III upgrade",
            "ecBlood3PowerTick", Integer.class, 240, true, 3),
    RATE_1_PARAM(
            CATEGORY_UPGRADES,
            "Percentage reduction in spawn time for Rate I upgrade",
            "rate1Param", Integer.class, 20, true, 1),
    RATE_2_PARAM(
            CATEGORY_UPGRADES,
            "Percentage reduction in spawn time for Rate II upgrade",
            "rate2Param", Integer.class, 50, true, 2),
    RATE_3_PARAM(
            CATEGORY_UPGRADES,
            "Percentage reduction in spawn time for Rate III upgrade",
            "rate3Param", Integer.class, 75, true, 3),
    MASS_1_PARAM(
            CATEGORY_UPGRADES,
            "Default number of mobs to spawn for Mass I upgrade",
            "mass1Param", Integer.class, 2, true, 1),
    MASS_2_PARAM(
            CATEGORY_UPGRADES,
            "Default number of mobs to spawn for Mass II upgrade",
            "mass2Param", Integer.class, 4, true, 2),
    MASS_3_PARAM(
            CATEGORY_UPGRADES,
            "Default number of mobs to spawn for Mass III upgrade",
            "mass3Param", Integer.class, 6, true, 3),
    LOOTING_1_PARAM(
            CATEGORY_UPGRADES,
            "",
            "looting1Param", Integer.class, 1, false, 1),
    LOOTING_2_PARAM(
            CATEGORY_UPGRADES,
            "",
            "looting2Param", Integer.class, 2, false, 2),
    LOOTING_3_PARAM(
            CATEGORY_UPGRADES,
            "",
            "looting3Param", Integer.class, 3, false, 3),
    DECAP_1_PARAM(
            CATEGORY_UPGRADES,
            "Default chance to generate a skull for Decapitate I upgrade",
            "decap1Param", Integer.class, 20, true, 1),
    DECAP_2_PARAM(
            CATEGORY_UPGRADES,
            "Default chance to generate a skull for Decapitate II upgrade",
            "decap2Param", Integer.class, 40, true, 2),
    DECAP_3_PARAM(
            CATEGORY_UPGRADES,
            "Default chance to generate a skull for Decapitate III upgrade",
            "decap3Param", Integer.class, 80, true, 3),
    XP_1_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of XP generated for XP I upgrade",
            "xp1Param", Integer.class, 100, true, 1),
    XP_2_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of XP generated for XP II upgrade",
            "xp2Param", Integer.class, 140, true, 2),
    XP_3_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of XP generated for XP III upgrade",
            "xp3Param", Integer.class, 180, true, 3),
    EFF_1_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of power saved for a Efficiency I upgrade",
            "eff1Param", Integer.class, 15, true, 1),
    EFF_2_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of power saved for a Efficiency II upgrade",
            "eff2Param", Integer.class, 25, true, 2),
    EFF_3_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of power saved for a Efficiency III upgrade",
            "eff3Param", Integer.class, 30, true, 3),
    BM_LE_TANK_1_PARAM(
            CATEGORY_UPGRADES,
            "Number of sacrifice runes for a BloodMagic LifeEssence Tank I upgrade",
            "bmLeTank1Param", Integer.class, 4, true, 1),
    BM_LE_TANK_2_PARAM(
            CATEGORY_UPGRADES,
            "Number of sacrifice runes for a BloodMagic LifeEssence Tank II upgrade",
            "bmLeTank2Param", Integer.class, 16, true, 2),
    BM_LE_TANK_3_PARAM(
            CATEGORY_UPGRADES,
            "Number of sacrifice runes for a BloodMagic LifeEssence Tank III upgrade",
            "bmLeTank13Param", Integer.class, 24, true, 3),
    BM_LE_ALTAR_1_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of sacrifice amount for a BloodMagic LifeEssence Altar I upgrade",
                    "bmLeAltar1Param", Integer.class, 50, true, 1),
    BM_LE_ALTAR_2_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of sacrifice amount for a BloodMagic LifeEssence Altar II upgrade",
                    "bmLeAltar2Param", Integer.class, 75, true, 2),
    BM_LE_ALTAR_3_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of sacrifice amount for a BloodMagic LifeEssence Altar III upgrade",
                    "bmLeAltar3Param", Integer.class, 100, true, 3),
    EC_BLOOD_1_PARAM(
            CATEGORY_UPGRADES,
            "mb per HP for the EvilCraft Blood Tank I upgrade",
                "ecBlood1Param", Integer.class, 20, true, 1),
    EC_BLOOD_2_PARAM(
            CATEGORY_UPGRADES,
            "mb per HP for the EvilCraft Blood Tank II upgrade",
                "ecBlood2Param", Integer.class, 40, true, 2),
    EC_BLOOD_3_PARAM(
            CATEGORY_UPGRADES,
            "mb per HP for the EvilCraft Blood Tank III upgrade",
            "ecBlood3Param", Integer.class, 60, true, 3),
    BM_CRYSTAL_1_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of mob health given to the BloodMagic Crystal I upgrade",
            "bmCrystal1Param", Integer.class, 25, true, 1),
    BM_CRYSTAL_2_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of mob health given to the BloodMagic Crystal II upgrade",
            "bmCrystal2Param", Integer.class, 60, true, 2),
    BM_CRYSTAL_3_PARAM(
            CATEGORY_UPGRADES,
            "Percentage of mob health given to the BloodMagic Crystal III upgrade",
            "bmCrystal3Param", Integer.class, 100, true, 3),
    ALLOW_BM_LE_TANK(
            CATEGORY_FACTORY_MOD,
            "Allow the factory to support BloodMagic Ritual Of The Sanguine Urn",
             "allowBmLeTank", Boolean.class, true, false),
    ALLOW_BM_LE_ALTAR(
            CATEGORY_FACTORY_MOD,
            "Allow the factory to support BloodMagic Ritual Of The Mechanical Altar",
            "allowBmLeAltar", Boolean.class, true, false),
    ALLOW_EC_BLOOD(
            CATEGORY_FACTORY_MOD,
            "Allow the factory to support EvilCraft Blood Generation",
                    "allowEcBlood", Boolean.class, true, false),
    ALLOW_BM_CRYSTAL(
            CATEGORY_FACTORY_MOD,
            "Allow the factory to support BloodMagic Ritual Of The Cloned Soul",
            "allowBmCrystal", Boolean.class, true, false);

    private String text;
    private Class clazz;
    private int defaultInteger;
    private boolean defaultBoolean;
    private boolean mobOverride;
    private int level;
    private String comment;
    private String category;
    EnumConfigKey(String category, String comment, String text, Class clazz, int defaultValue, boolean mobOverride, int level) {

        this.category = category;
        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
        this.level = level;
    }

    EnumConfigKey(String category, String comment, String text, Class clazz, int defaultValue, boolean mobOverride) {

        this.category = category;
        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = defaultValue;
        this.defaultBoolean = false;
        this.mobOverride = mobOverride;
        this.level = -1;
    }

    EnumConfigKey(String category, String comment, String text, Class clazz, boolean defaultValue, boolean mobOverride) {

        this.category = category;
        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
        this.level = -1;
    }

    EnumConfigKey(String category, String comment, String text, Class clazz, boolean defaultValue, boolean mobOverride, int level) {

        this.category = category;
        this.comment = comment;
        this.text = text;
        this.clazz = clazz;
        this.defaultInteger = 1;
        this.defaultBoolean = defaultValue;
        this.mobOverride = mobOverride;
        this.level = level;
    }

    public String getCategory() { return category; }
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
    public static EnumSet<EnumConfigKey> BM_LE_TANK_POWER_PER_TICK = EnumSet.of(BM_LE_TANK_1_POWER_TICK, BM_LE_TANK_2_POWER_TICK, BM_LE_TANK_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> BM_LE_ALTAR_POWER_PER_TICK = EnumSet.of(BM_LE_ALTAR_1_POWER_TICK, BM_LE_ALTAR_2_POWER_TICK, BM_LE_ALTAR_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> BM_CRYSTAL_POWER_PER_TICK = EnumSet.of(BM_CRYSTAL_1_POWER_TICK, BM_CRYSTAL_2_POWER_TICK, BM_CRYSTAL_3_POWER_TICK);
    public static EnumSet<EnumConfigKey> EC_BLOOD_POWER_PER_TICK = EnumSet.of(EC_BLOOD_1_POWER_TICK, EC_BLOOD_2_POWER_TICK, EC_BLOOD_3_POWER_TICK);

    public static EnumSet<EnumConfigKey> RATE_PARAM = EnumSet.of(RATE_1_PARAM, RATE_2_PARAM, RATE_3_PARAM);
    public static EnumSet<EnumConfigKey> MASS_PARAM = EnumSet.of(MASS_1_PARAM, MASS_2_PARAM, MASS_3_PARAM);
    public static EnumSet<EnumConfigKey> LOOTING_PARAM = EnumSet.of(LOOTING_1_PARAM, LOOTING_2_PARAM, LOOTING_3_PARAM);
    public static EnumSet<EnumConfigKey> DECAP_PARAM = EnumSet.of(DECAP_1_PARAM, DECAP_2_PARAM, DECAP_3_PARAM);
    public static EnumSet<EnumConfigKey> XP_PARAM = EnumSet.of(XP_1_PARAM, XP_2_PARAM, XP_3_PARAM);
    public static EnumSet<EnumConfigKey> EFF_PARAM = EnumSet.of(EFF_1_PARAM, EFF_2_PARAM, EFF_3_PARAM);
    public static EnumSet<EnumConfigKey> BM_LE_TANK_PARAM = EnumSet.of(BM_LE_TANK_1_PARAM, BM_LE_TANK_2_PARAM, BM_LE_TANK_3_PARAM);
    public static EnumSet<EnumConfigKey> BM_LE_ALTAR_PARAM = EnumSet.of(BM_LE_ALTAR_1_PARAM, BM_LE_ALTAR_2_PARAM, BM_LE_ALTAR_3_PARAM);
    public static EnumSet<EnumConfigKey> EC_BLOOD_PARAM = EnumSet.of(EC_BLOOD_1_PARAM, EC_BLOOD_2_PARAM, EC_BLOOD_3_PARAM);
    public static EnumSet<EnumConfigKey> BM_CRYSTAL_PARAM = EnumSet.of(BM_CRYSTAL_1_PARAM, BM_CRYSTAL_2_PARAM, BM_CRYSTAL_3_PARAM);
}
