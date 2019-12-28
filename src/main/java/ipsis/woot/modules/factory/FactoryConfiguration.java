package ipsis.woot.modules.factory;

import ipsis.woot.common.configuration.Config;
import ipsis.woot.config.MobOverride;
import ipsis.woot.config.WootConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class FactoryConfiguration {

    public static final String CATEGORY_FACTORY = "factory";

    public static ForgeConfigSpec.BooleanValue TICK_ACCEL;

    public static ForgeConfigSpec.IntValue CELL_1_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_1_MAX_TRANSFER;
    public static ForgeConfigSpec.IntValue CELL_2_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_2_MAX_TRANSFER;
    public static ForgeConfigSpec.IntValue CELL_3_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_3_MAX_TRANSFER;

    public static ForgeConfigSpec.IntValue MASS_COUNT;
    public static ForgeConfigSpec.IntValue SPAWN_TICKS;
    public static ForgeConfigSpec.IntValue UNITS_PER_HEALTH;
    public static ForgeConfigSpec.IntValue MOB_SHARD_KILLS;
    public static ForgeConfigSpec.IntValue TIER_1_MAX_UNITS;
    public static ForgeConfigSpec.IntValue TIER_2_MAX_UNITS;
    public static ForgeConfigSpec.IntValue TIER_3_MAX_UNITS;
    public static ForgeConfigSpec.IntValue TIER_4_MAX_UNITS;
    public static ForgeConfigSpec.IntValue TIER_5_MAX_UNITS;
    public static ForgeConfigSpec.IntValue TIER_1_UNITS_PER_TICK;
    public static ForgeConfigSpec.IntValue TIER_2_UNITS_PER_TICK;
    public static ForgeConfigSpec.IntValue TIER_3_UNITS_PER_TICK;
    public static ForgeConfigSpec.IntValue TIER_4_UNITS_PER_TICK;
    public static ForgeConfigSpec.IntValue TIER_5_UNITS_PER_TICK;

    //public static ForgeConfigSpec.IntValue CAPACITY_1;
    //public static ForgeConfigSpec.IntValue CAPACITY_2;
    //public static ForgeConfigSpec.IntValue CAPACITY_3;
    public static ForgeConfigSpec.IntValue EFFICIENCY_1;
    public static ForgeConfigSpec.IntValue EFFICIENCY_2;
    public static ForgeConfigSpec.IntValue EFFICIENCY_3;
    public static ForgeConfigSpec.IntValue MASS_COUNT_1;
    public static ForgeConfigSpec.IntValue MASS_COUNT_2;
    public static ForgeConfigSpec.IntValue MASS_COUNT_3;
    public static ForgeConfigSpec.IntValue RATE_1;
    public static ForgeConfigSpec.IntValue RATE_2;
    public static ForgeConfigSpec.IntValue RATE_3;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the factory").push(CATEGORY_FACTORY);
        CLIENT_BUILDER.comment("Settings for the factory").push(CATEGORY_FACTORY);
        {
            TICK_ACCEL = COMMON_BUILDER
                    .comment("Allow tick acceleration to be used on the factory")
                    .define("tickAcceleration", true);

            MASS_COUNT = COMMON_BUILDER
                    .comment("Number of mobs to spawn")
                    .defineInRange(MobOverride.MASS_COUNT_TAG, 1, 1, 100);

            SPAWN_TICKS = COMMON_BUILDER
                    .comment("Number of ticks to spawn a mob")
                    .defineInRange(MobOverride.SPAWN_TICKS_TAG, 16 * 20, 1, 65535);
            UNITS_PER_HEALTH = COMMON_BUILDER
                    .comment("Number of units for each health")
                    .defineInRange(MobOverride.UNITS_PER_HEALTH_TAG, 1, 1, 65535);
            MOB_SHARD_KILLS = COMMON_BUILDER
                    .comment("Number of kills to program the shard")
                    .defineInRange(MobOverride.MOB_SHARD_KILLS_TAG, 5, 1, 65535);

            TIER_1_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 1 mob")
                    .defineInRange("tier1MaxUnits", 20, 5, 65535);
            TIER_2_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 2 mob")
                    .defineInRange("tier2MaxUnits", 40, 5, 65535);
            TIER_3_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 3 mob")
                    .defineInRange("tier3MaxUnits", 60, 5, 65535);
            TIER_4_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 4 mob")
                    .defineInRange("tier4MaxUnits", Integer.MAX_VALUE, 5, Integer.MAX_VALUE);
            TIER_5_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 5 mob")
                    .defineInRange("tier5MaxUnits", Integer.MAX_VALUE, 5, Integer.MAX_VALUE);


            COMMON_BUILDER.push("cost");
            {
                TIER_1_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 1 factory")
                        .defineInRange("tier1UnitsPerTick", 5, 1, Integer.MAX_VALUE);
                TIER_2_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 2 factory")
                        .defineInRange("tier2UnitsPerTick", 10, 1, Integer.MAX_VALUE);
                TIER_3_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 3 factory")
                        .defineInRange("tier3UnitsPerTick", 20, 1, Integer.MAX_VALUE);
                TIER_4_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 4 factory")
                        .defineInRange("tier4UnitsPerTick", 30, 1, Integer.MAX_VALUE);
                TIER_5_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 5 factory")
                        .defineInRange("tier5UnitsPerTick", 40, 1, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();

            COMMON_BUILDER.push("upgrades");
            {
                /*
                CAPACITY_1 = COMMON_BUILDER
                        .comment("Number of supported controllers for capacity 1 upgrade")
                        .defineInRange(MobOverride.CAPACITY_1_TAG, 2, 1, 4);
                CAPACITY_2 = COMMON_BUILDER
                        .comment("Number of supported controllers for capacity 2 upgrade")
                        .defineInRange(MobOverride.CAPACITY_2_TAG, 3, 1, 4);
                CAPACITY_3 = COMMON_BUILDER
                        .comment("Number of supported controllers for capacity 3 upgrade")
                        .defineInRange(MobOverride.CAPACITY_3_TAG, 4, 1, 4); */

                EFFICIENCY_1 = COMMON_BUILDER
                        .comment("Percentage reduction for efficiency 1 upgrade")
                        .defineInRange(MobOverride.EFFICIENCY_1_TAG, 15, 1, 100);
                EFFICIENCY_2 = COMMON_BUILDER
                        .comment("Percentage reduction for efficiency 2 upgrade")
                        .defineInRange(MobOverride.EFFICIENCY_2_TAG, 25, 1, 100);
                EFFICIENCY_3 = COMMON_BUILDER
                        .comment("Percentage reduction for efficiency 3 upgrade")
                        .defineInRange(MobOverride.EFFICIENCY_3_TAG, 30, 1, 100);

                MASS_COUNT_1 = COMMON_BUILDER
                        .comment("Number of mobs to spawn for mass 1 upgrade")
                        .defineInRange(MobOverride.MASS_COUNT_1_TAG, 2, 1, 100);
                MASS_COUNT_2 = COMMON_BUILDER
                        .comment("Number of mobs to spawn for mass 2 upgrade")
                        .defineInRange(MobOverride.MASS_COUNT_2_TAG, 4, 1, 100);
                MASS_COUNT_3 = COMMON_BUILDER
                        .comment("Number of mobs to spawn for mass 3 upgrade")
                        .defineInRange(MobOverride.MASS_COUNT_3_TAG, 6, 1, 100);

                RATE_1 = COMMON_BUILDER
                        .comment("Percentage reduction in spawn time for rate 1 upgrade")
                        .defineInRange(MobOverride.RATE_1_TAG, 20, 1, 99);
                RATE_2 = COMMON_BUILDER
                        .comment("Percentage reduction in spawn time for rate 2 upgrade")
                        .defineInRange(MobOverride.RATE_2_TAG, 50, 1, 99);
                RATE_3 = COMMON_BUILDER
                        .comment("Percentage reduction in spawn time for rate 3 upgrade")
                        .defineInRange(MobOverride.RATE_3_TAG, 75, 1, 99);
            }
            COMMON_BUILDER.pop();

            COMMON_BUILDER.push("cell");
            {
                CELL_1_CAPACITY = COMMON_BUILDER
                        .comment("Storage capacity of a basic cell")
                        .worldRestart()
                        .defineInRange("cell1Capacity", 10000, 1, Integer.MAX_VALUE);
                CELL_2_CAPACITY = COMMON_BUILDER
                        .comment("Storage capacity of an advanced cell")
                        .worldRestart()
                        .defineInRange("cell2Capacity", 100000, 1, Integer.MAX_VALUE);
                CELL_3_CAPACITY = COMMON_BUILDER
                        .comment("Storage capacity of a premium cell")
                        .worldRestart()
                        .defineInRange("cell3Capacity", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
                CELL_1_MAX_TRANSFER = COMMON_BUILDER
                        .comment("Max transfer rate (per-side) of a basic cell")
                        .worldRestart()
                        .defineInRange("cell1MaxTransfer", 1000, 1, Integer.MAX_VALUE);
                CELL_2_MAX_TRANSFER = COMMON_BUILDER
                        .comment("Max transfer rate (per-side) of an advanced cell")
                        .worldRestart()
                        .defineInRange("cell2MaxTransfer", 1000, 1, Integer.MAX_VALUE);
                CELL_3_MAX_TRANSFER = COMMON_BUILDER
                        .comment("Max transfer rate (per-side) of a premium cell")
                        .worldRestart()
                        .defineInRange("cell3MaxTransfer", Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }

    public static void pushToWootConfig() {

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_COUNT, MASS_COUNT.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.SPAWN_TICKS, SPAWN_TICKS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MOB_SHARD_KILLS, MOB_SHARD_KILLS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.UNITS_PER_HEALTH, UNITS_PER_HEALTH.get());
        // MOB_TIER is an override or calculated, never a default value

        //WootConfig.get().putIntConfig(WootConfig.ConfigKey.CAPACITY_1, Config.COMMON.CAPACITY_1.get());
        //WootConfig.get().putIntConfig(WootConfig.ConfigKey.CAPACITY_2, Config.COMMON.CAPACITY_2.get());
        //WootConfig.get().putIntConfig(WootConfig.ConfigKey.CAPACITY_3, Config.COMMON.CAPACITY_3.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.EFFICIENCY_1, EFFICIENCY_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.EFFICIENCY_2, EFFICIENCY_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.EFFICIENCY_3, EFFICIENCY_3.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_1, MASS_COUNT_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_2, MASS_COUNT_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_3, MASS_COUNT_3.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.RATE_1, RATE_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.RATE_2, RATE_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.RATE_3, RATE_3.get());
    }
}
