package ipsis.woot.common;

import ipsis.woot.factory.FactoryUpgradeType;
import ipsis.woot.factory.Tier;
import ipsis.woot.simulation.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.*;

public class Config {

    static final Logger LOGGER = LogManager.getLogger();
    static final Marker CONFIG = MarkerManager.getMarker("WOOT_CONFIG");

    static final List<String> DEFAULT_MOB_OVERRIDES = Arrays.asList(
            "minecraft:wither_skeleton,mobHealth,40",
            "minecraft:wither_skeleton,mobTier,3",
            "minecraft:villager,mobTier,3",
            "minecraft:magma_cube,mobTier,2",
            "minecraft:enderman,mobTier,3",
            "minecraft:villager_golem,mobTier,3",
            "minecraft:guardian,mobTier,3",
            "minecraft:blaze,mobTier,2",
            "minecraft:witch,mobTier,2",
            "minecraft:ghast,mobTier,2",
            "minecraft:zombie_pigman,mobTier,2",

            "minecraft:ender_dragon,mobHealth,500",
            "minecraft:ender_dragon,spawnTicks,12000",
            "minecraft:ender_dragon,mobTier,4",
            "minecraft:ender_dragon,mass1MobCount,2",
            "minecraft:ender_dragon,mass2MobCount,3",
            "minecraft:ender_dragon,mass3MobCount,4",

            "minecraft:minecraft_wither,mobHealth,500",
            "minecraft:minecraft_wither,spawnTicks,1200",
            "minecraft:minecraft_wither,mobTier,4",
            "minecraft:minecraft_wither,mass1MobCount,2",
            "minecraft:minecraft_wither,mass2MobCount,3",
            "minecraft:minecraft_wither,mass3MobCount,4"
            );

    public static class Common {

        public final IntValue SIMULATION_TICKS;
        public final IntValue SIMULATION_MOB_COUNT;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOB_OVERRIDES;

        public final IntValue MASS_COUNT;
        public final IntValue SPAWN_TICKS;
        public final IntValue UNITS_PER_HEALTH;
        public final IntValue TIER_1_MAX_UNITS;
        public final IntValue TIER_2_MAX_UNITS;
        public final IntValue TIER_3_MAX_UNITS;
        public final IntValue TIER_4_MAX_UNITS;
        public final IntValue TIER_5_MAX_UNITS;
        public final IntValue TIER_1_UNITS_PER_TICK;
        public final IntValue TIER_2_UNITS_PER_TICK;
        public final IntValue TIER_3_UNITS_PER_TICK;
        public final IntValue TIER_4_UNITS_PER_TICK;
        public final IntValue TIER_5_UNITS_PER_TICK;
        public final String MASS_COUNT_TAG = "massCount";
        public final String SPAWN_TICKS_TAG = "spawnTicks";
        public final String MOB_HEALTH_TAG = "mobHealth";
        public final String UNITS_PER_HEALTH_TAG = "unitsPerHealth";
        public final String MOB_TIER_TAG = "mobTier";

        public final String TIER_1_MAX_UNITS_TAG = "tier1MaxUnits";
        public final String TIER_2_MAX_UNITS_TAG = "tier2MaxUnits";
        public final String TIER_3_MAX_UNITS_TAG = "tier3MaxUnits";
        public final String TIER_4_MAX_UNITS_TAG = "tier4MaxUnits";
        public final String TIER_5_MAX_UNITS_TAG = "tier5MaxUnits";
        public final String TIER_1_UNITS_PER_TICK_TAG = "tier1UnitsPerTick";
        public final String TIER_2_UNITS_PER_TICK_TAG = "tier2UnitsPerTick";
        public final String TIER_3_UNITS_PER_TICK_TAG = "tier3UnitsPerTick";
        public final String TIER_4_UNITS_PER_TICK_TAG = "tier4UnitsPerTick";
        public final String TIER_5_UNITS_PER_TICK_TAG = "tier5UnitsPerTick";


        /**
         * Upgrades
         */
        public final IntValue CAPACITY_1;
        public final IntValue CAPACITY_2;
        public final IntValue CAPACITY_3;
        public final IntValue EFFICIENCY_1;
        public final IntValue EFFICIENCY_2;
        public final IntValue EFFICIENCY_3;
        public final IntValue MASS_COUNT_1;
        public final IntValue MASS_COUNT_2;
        public final IntValue MASS_COUNT_3;
        public final IntValue RATE_1;
        public final IntValue RATE_2;
        public final IntValue RATE_3;
        public final String CAPACITY_1_TAG = "capacity1ControllerCount";
        public final String CAPACITY_2_TAG = "capacity2ControllerCount";
        public final String CAPACITY_3_TAG = "capacity3ControllerCount";
        public final String EFFICIENCY_1_TAG = "efficiency1Reduction";
        public final String EFFICIENCY_2_TAG = "efficiency2Reduction";
        public final String EFFICIENCY_3_TAG = "efficiency3Reduction";
        public final String MASS_COUNT_1_TAG = "mass1MobCount";
        public final String MASS_COUNT_2_TAG = "mass2MobCount";
        public final String MASS_COUNT_3_TAG = "mass3MobCount";
        public final String RATE_1_TAG = "rate1Increase";
        public final String RATE_2_TAG = "rate2Increase";
        public final String RATE_3_TAG = "rate3Increase";

        final List<String> VALID_OVERRIDE_TAGS = new ArrayList<String>(){{
            add(RATE_1_TAG.toLowerCase(Locale.ROOT));
            add(RATE_2_TAG.toLowerCase(Locale.ROOT));
            add(RATE_3_TAG.toLowerCase(Locale.ROOT));
            add(MASS_COUNT_1_TAG.toLowerCase(Locale.ROOT));
            add(MASS_COUNT_2_TAG.toLowerCase(Locale.ROOT));
            add(MASS_COUNT_3_TAG.toLowerCase(Locale.ROOT));
            add(EFFICIENCY_1_TAG.toLowerCase(Locale.ROOT));
            add(EFFICIENCY_2_TAG.toLowerCase(Locale.ROOT));
            add(EFFICIENCY_3_TAG.toLowerCase(Locale.ROOT));
            add(MASS_COUNT_TAG.toLowerCase(Locale.ROOT));
            add(SPAWN_TICKS_TAG.toLowerCase(Locale.ROOT));
            add(MOB_HEALTH_TAG.toLowerCase(Locale.ROOT));
            add(UNITS_PER_HEALTH_TAG.toLowerCase(Locale.ROOT));
            add(MOB_TIER_TAG.toLowerCase(Locale.ROOT));

        }};

        Common(ForgeConfigSpec.Builder builder) {
            String TAG = "woot.configgui.";
            builder.comment("Common configuration settings")
                    .push("common");

            String TAG2 = "simulationTicks";
            SIMULATION_TICKS = builder
                    .comment("Number of ticks between mob simulations")
                    .translation(TAG + TAG2)
                    .defineInRange(TAG2, 40, 20, 20 * 60);

            TAG2 = "simulationMobCount";
            SIMULATION_MOB_COUNT = builder
                    .comment("Number of simulated mobs to learn from")
                    .translation(TAG + TAG2)
                    .defineInRange(TAG2, 500,100, 5000);

            builder.push("mob");
            {
                TAG2 = "mobOverrides";
                MOB_OVERRIDES = builder
                        .comment("A list of mob specific factory configuration values")
                        .translation(TAG + TAG2)
                        .define(TAG2, DEFAULT_MOB_OVERRIDES);
            }
            builder.pop(); // mob

            builder.push("factory");
            {
                MASS_COUNT = builder
                        .comment("Number of mobs to spawn")
                        .translation(TAG + MASS_COUNT_TAG)
                        .defineInRange(MASS_COUNT_TAG, 1, 1, 100);

                SPAWN_TICKS = builder
                        .comment("Number of ticks to spawn a mob")
                        .translation(TAG + SPAWN_TICKS_TAG)
                        .defineInRange(SPAWN_TICKS_TAG, 16 * 20, 1, 65535);
                UNITS_PER_HEALTH = builder
                        .comment("Number of units for each health")
                        .translation(TAG + UNITS_PER_HEALTH_TAG)
                        .defineInRange(UNITS_PER_HEALTH_TAG, 1, 1, 65535);

                TIER_1_MAX_UNITS = builder
                        .comment("Max units for a tier 1 mob")
                        .translation(TAG + TIER_1_MAX_UNITS_TAG)
                        .defineInRange(TIER_1_MAX_UNITS_TAG, 20, 5, 65535);
                TIER_2_MAX_UNITS = builder
                        .comment("Max units for a tier 2 mob")
                        .translation(TAG + TIER_2_MAX_UNITS_TAG)
                        .defineInRange(TIER_2_MAX_UNITS_TAG, 40, 5, 65535);
                TIER_3_MAX_UNITS = builder
                        .comment("Max units for a tier 3 mob")
                        .translation(TAG + TIER_3_MAX_UNITS_TAG)
                        .defineInRange(TIER_3_MAX_UNITS_TAG, 60, 5, 65535);
                TIER_4_MAX_UNITS = builder
                        .comment("Max units for a tier 4 mob")
                        .translation(TAG + TIER_4_MAX_UNITS_TAG)
                        .defineInRange(TIER_4_MAX_UNITS_TAG, Integer.MAX_VALUE, 5, Integer.MAX_VALUE);
                TIER_5_MAX_UNITS = builder
                        .comment("Max units for a tier 5 mob")
                        .translation(TAG + TIER_5_MAX_UNITS_TAG)
                        .defineInRange(TIER_5_MAX_UNITS_TAG, Integer.MAX_VALUE, 5, Integer.MAX_VALUE);

                builder.push("cost");
                {
                    TIER_1_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 1 factory")
                            .translation(TAG + TIER_1_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_1_UNITS_PER_TICK_TAG, 5, 1, Integer.MAX_VALUE);
                    TIER_2_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 2 factory")
                            .translation(TAG + TIER_2_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_2_UNITS_PER_TICK_TAG, 10, 1, Integer.MAX_VALUE);
                    TIER_3_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 3 factory")
                            .translation(TAG + TIER_3_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_3_UNITS_PER_TICK_TAG, 20, 1, Integer.MAX_VALUE);
                    TIER_4_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 4 factory")
                            .translation(TAG + TIER_4_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_4_UNITS_PER_TICK_TAG, 30, 1, Integer.MAX_VALUE);
                    TIER_5_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 5 factory")
                            .translation(TAG + TIER_5_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_5_UNITS_PER_TICK_TAG, 40, 1, Integer.MAX_VALUE);
                }
                builder.pop(); // cost

                builder.push("upgrades");
                {
                    CAPACITY_1 = builder
                            .comment("Number of supported controllers for capacity 1 upgrade")
                            .translation(TAG + CAPACITY_1_TAG)
                            .defineInRange(CAPACITY_1_TAG, 2, 1, 4);
                    CAPACITY_2 = builder
                            .comment("Number of supported controllers for capacity 2 upgrade")
                            .translation(TAG + CAPACITY_2_TAG)
                            .defineInRange(CAPACITY_2_TAG, 3, 1, 4);
                    CAPACITY_3 = builder
                            .comment("Number of supported controllers for capacity 3 upgrade")
                            .translation(TAG + CAPACITY_3_TAG)
                            .defineInRange(CAPACITY_3_TAG, 4, 1, 4);

                    EFFICIENCY_1 = builder
                            .comment("Percentage reduction for efficiency 1 upgrade")
                            .translation(TAG + EFFICIENCY_1_TAG)
                            .defineInRange(EFFICIENCY_1_TAG, 15, 1, 100);
                    EFFICIENCY_2 = builder
                            .comment("Percentage reduction for efficiency 2 upgrade")
                            .translation(TAG + EFFICIENCY_2_TAG)
                            .defineInRange(EFFICIENCY_2_TAG, 25, 1, 100);
                    EFFICIENCY_3 = builder
                            .comment("Percentage reduction for efficiency 3 upgrade")
                            .translation(TAG + EFFICIENCY_3_TAG)
                            .defineInRange(EFFICIENCY_3_TAG, 30, 1, 100);

                    MASS_COUNT_1 = builder
                            .comment("Number of mobs to spawn for mass 1 upgrade")
                            .translation(TAG + MASS_COUNT_1_TAG)
                            .defineInRange(MASS_COUNT_1_TAG, 2, 1, 100);
                    MASS_COUNT_2 = builder
                            .comment("Number of mobs to spawn for mass 2 upgrade")
                            .translation(TAG + MASS_COUNT_2_TAG)
                            .defineInRange(MASS_COUNT_2_TAG, 4, 1, 100);
                    MASS_COUNT_3 = builder
                            .comment("Number of mobs to spawn for mass 3 upgrade")
                            .translation(TAG + MASS_COUNT_3_TAG)
                            .defineInRange(MASS_COUNT_3_TAG, 6, 1, 100);

                    RATE_1 = builder
                            .comment("Percentage reduction in spawn time for rate 1 upgrade")
                            .translation(TAG + RATE_1_TAG)
                            .defineInRange(RATE_1_TAG, 20, 1, 99);
                    RATE_2 = builder
                            .comment("Percentage reduction in spawn time for rate 2 upgrade")
                            .translation(TAG + RATE_2_TAG)
                            .defineInRange(RATE_2_TAG, 50, 1, 99);
                    RATE_3 = builder
                            .comment("Percentage reduction in spawn time for rate 3 upgrade")
                            .translation(TAG + RATE_3_TAG)
                            .defineInRange(RATE_3_TAG, 75, 1, 99);
                }
                builder.pop(); // upgrades
            }
            builder.pop(); // factory
            builder.pop(); // common
        }
    }

    public static class Client {

        public final ForgeConfigSpec.DoubleValue LAYOUT_OPACITY;
        public final ForgeConfigSpec.DoubleValue LAYOUT_SIZE;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            LAYOUT_OPACITY = builder
                    .comment("Opacity of the layout blocks")
                    .translation("woot.configgui.layoutOpacity")
                    .defineInRange("layoutOpacity", 0.95D, 0.10D, 1.00D);

            LAYOUT_SIZE = builder
                    .comment("Size of the layout blocks")
                    .translation("woot.configgui.layoutSize")
                    .defineInRange("layoutSize", 0.35D, 0.10D, 1.0D);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    /**
     * Access config values that might have mob specific versions
     * Access config values for upgrades
     * Access the other values directly
     */
    public static int getIntValueForUpgrade(FactoryUpgradeType upgradeType, int level) {
        level = MathHelper.clamp(level, 0, 3);
        if (upgradeType == FactoryUpgradeType.CAPACITY) {
            if (level == 0) return 1;
            else if (level == 1) return COMMON.CAPACITY_1.get();
            else if (level == 2) return COMMON.CAPACITY_2.get();
            else if (level == 3) return COMMON.CAPACITY_3.get();
        } else if (upgradeType == FactoryUpgradeType.EFFICIENCY) {
            if (level == 0) return 0;
            else if (level == 1) return COMMON.EFFICIENCY_1.get();
            else if (level == 2) return COMMON.EFFICIENCY_2.get();
            else if (level == 3) return COMMON.EFFICIENCY_3.get();
        } else if (upgradeType == FactoryUpgradeType.LOOTING) {
            return level;
        } else if (upgradeType == FactoryUpgradeType.MASS) {
            if (level == 0) return COMMON.MASS_COUNT.get();
            else if (level == 1) return COMMON.MASS_COUNT_1.get();
            else if (level == 2) return COMMON.MASS_COUNT_2.get();
            else if (level == 3) return COMMON.MASS_COUNT_3.get();
        } else if (upgradeType == FactoryUpgradeType.RATE) {
            if (level == 0) return 0;
            else if (level == 1) return COMMON.RATE_1.get();
            else if (level == 2) return COMMON.RATE_2.get();
            else if (level == 3) return COMMON.RATE_3.get();
        } else if (upgradeType == FactoryUpgradeType.XP) {

        }
        return 1;
    }

    public static int getIntValueForUpgrade(FakeMob fakeMob, FactoryUpgradeType upgradeType, int level) {
        level = MathHelper.clamp(level, 0, 3);
        if (!fakeMob.isValid())
            return getIntValueForUpgrade(upgradeType, level);

        if (upgradeType == FactoryUpgradeType.CAPACITY || upgradeType == FactoryUpgradeType.LOOTING) {
            // Cannot override capacity
            return getIntValueForUpgrade(upgradeType, level);
        } else {
            String key = getTag(upgradeType, level);
            if (key != "" && intMappings.containsKey(fakeMob) && intMappings.get(fakeMob).containsKey(key))
                return intMappings.get(fakeMob).get(key);
            else
                return getIntValueForUpgrade(upgradeType, level);
        }
    }

    public static boolean hasIntValueByString(FakeMob fakeMob, String key) {
        return intMappings.containsKey(fakeMob) && intMappings.get(fakeMob).containsKey(key);
    }

    public static int getIntValueByString(FakeMob fakeMob, String key) {
        if (!hasIntValueByString(fakeMob, key))
            return -1;
        return intMappings.get(fakeMob).get(key);
    }

    static String getTag(FactoryUpgradeType type, int level) {
        level = MathHelper.clamp(level, 0, 3);
        if (type == FactoryUpgradeType.CAPACITY) {
            if (level == 0) return "";
            else if (level == 1) return COMMON.CAPACITY_1_TAG;
            else if (level == 2) return COMMON.CAPACITY_2_TAG;
            else if (level == 3) return COMMON.CAPACITY_3_TAG;
        } else if (type == FactoryUpgradeType.EFFICIENCY) {
            if (level == 0) return "";
            else if (level == 1) return COMMON.EFFICIENCY_1_TAG;
            else if (level == 2) return COMMON.EFFICIENCY_2_TAG;
            else if (level == 3) return COMMON.EFFICIENCY_3_TAG;
        } else if (type == FactoryUpgradeType.LOOTING) {
            return "";
        } else if (type == FactoryUpgradeType.MASS) {
            if (level == 0) return "";
            else if (level == 1) return COMMON.MASS_COUNT_1_TAG;
            else if (level == 2) return COMMON.MASS_COUNT_2_TAG;
            else if (level == 3) return COMMON.MASS_COUNT_3_TAG;
        } else if (type == FactoryUpgradeType.RATE) {
            if (level == 0) return "";
            else if (level == 1) return COMMON.RATE_1_TAG;
            else if (level == 2) return COMMON.RATE_2_TAG;
            else if (level == 3) return COMMON.RATE_3_TAG;
        } else if (type == FactoryUpgradeType.XP) {
            return "";
        }
        return "";
    }

    public static Tier getMobTier(FakeMob fakeMob, World world) {

        Tier tier;
        if (intMappings.containsKey(fakeMob) && intMappings.get(fakeMob).containsKey(COMMON.MOB_TIER_TAG)) {
            int v = intMappings.get(fakeMob).get(COMMON.MOB_TIER_TAG);
            v = MathHelper.clamp(v, 1, Tier.getMaxTier());
            tier = Tier.byIndex(v);
        } else {
            int health = SpawnController.get().getMobHealth(fakeMob, world);
            if (health <= Config.COMMON.TIER_1_MAX_UNITS.get()) tier = Tier.TIER_1;
            else if (health <= Config.COMMON.TIER_2_MAX_UNITS.get()) tier = Tier.TIER_2;
            else if (health <= Config.COMMON.TIER_3_MAX_UNITS.get()) tier = Tier.TIER_3;
            else if (health <= Config.COMMON.TIER_4_MAX_UNITS.get()) tier = Tier.TIER_4;
            else tier = Tier.TIER_5;
        }
        return tier;
    }

    /**
     * Mob specific config
     */
    static HashMap<FakeMob, HashMap<String, Integer>> intMappings = new HashMap<>();
    static void addIntMapping(FakeMob fakeMob, String key, int value) {
        if (!intMappings.containsKey(fakeMob))
            intMappings.put(fakeMob, new HashMap<>());
        HashMap<String, Integer> map = intMappings.get(fakeMob);
        map.put(key, value);
        LOGGER.info(CONFIG, "Added mapping {}:{} -> {}", fakeMob, key, value);
    }

    public static void loadFromConfig() {

        for (String s : Config.COMMON.MOB_OVERRIDES.get()) {
            String[] parts = s.split(",");
            if (parts.length != 3) {
                LOGGER.error(s + " == INVALID");
            } else {
                String mob = parts[0];
                String param = parts[1].toLowerCase(Locale.ROOT);
                if (COMMON.VALID_OVERRIDE_TAGS.contains(param)) {
                    try {
                        int v = Integer.valueOf(parts[2]);
                        FakeMob fakeMob = new FakeMob(mob);
                        if (fakeMob.isValid())
                            addIntMapping(fakeMob, param, v);
                        else
                            LOGGER.error(s + " == INVALID (invalid mob)");
                    } catch (NumberFormatException e) {
                        LOGGER.error(s + " == INVALID (invalid value)");
                    }
                } else {
                    LOGGER.error(s + " == INVALID (unknown param)");
                }
            }
        }

    }

}
