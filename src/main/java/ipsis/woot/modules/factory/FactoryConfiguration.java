package ipsis.woot.modules.factory;

import ipsis.woot.config.ConfigOverride;
import net.minecraftforge.common.ForgeConfigSpec;

import ipsis.woot.setup.ModDefaults.Factory;

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

    public static ForgeConfigSpec.IntValue EFFICIENCY_1;
    public static ForgeConfigSpec.IntValue EFFICIENCY_2;
    public static ForgeConfigSpec.IntValue EFFICIENCY_3;
    public static ForgeConfigSpec.IntValue MASS_COUNT_1;
    public static ForgeConfigSpec.IntValue MASS_COUNT_2;
    public static ForgeConfigSpec.IntValue MASS_COUNT_3;
    public static ForgeConfigSpec.IntValue RATE_1;
    public static ForgeConfigSpec.IntValue RATE_2;
    public static ForgeConfigSpec.IntValue RATE_3;
    public static ForgeConfigSpec.IntValue XP_1;
    public static ForgeConfigSpec.IntValue XP_2;
    public static ForgeConfigSpec.IntValue XP_3;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the factory").push(CATEGORY_FACTORY);
        CLIENT_BUILDER.comment("Settings for the factory").push(CATEGORY_FACTORY);
        {
            TICK_ACCEL = COMMON_BUILDER
                    .comment("Allow tick acceleration to be used on the factory")
                    .define("tickAcceleration", true);

            MASS_COUNT = COMMON_BUILDER
                    .comment("Number of mobs to spawn")
                    .defineInRange(ConfigOverride.OverrideKey.MASS_COUNT.name(), Factory.MASS_COUNT_DEF, 1, 100);

            SPAWN_TICKS = COMMON_BUILDER
                    .comment("Number of ticks to spawn a mob")
                    .defineInRange(ConfigOverride.OverrideKey.SPAWN_TICKS.name(), Factory.SPAWN_TICKS_DEF, 1, 65535);
            UNITS_PER_HEALTH = COMMON_BUILDER
                    .comment("Number of units for each health")
                    .defineInRange(ConfigOverride.OverrideKey.UNITS_PER_HEALTH.name(), Factory.UNITS_PER_HEALTH_DEF, 1, 65535);
            MOB_SHARD_KILLS = COMMON_BUILDER
                    .comment("Number of kills to program the shard")
                    .defineInRange(ConfigOverride.OverrideKey.SHARD_KILLS.name(), Factory.MOB_SHARD_KILLS_DEF, 1, 65535);

            TIER_1_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 1 mob")
                    .defineInRange("tier1MaxUnits", Factory.TIER_1_MAX_UNITS_DEF, 5, 65535);
            TIER_2_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 2 mob")
                    .defineInRange("tier2MaxUnits", Factory.TIER_2_MAX_UNITS_DEF, 5, 65535);
            TIER_3_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 3 mob")
                    .defineInRange("tier3MaxUnits", Factory.TIER_3_MAX_UNITS_DEF, 5, 65535);
            TIER_4_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 4 mob")
                    .defineInRange("tier4MaxUnits", Factory.TIER_4_MAX_UNITS_DEF, 5, Integer.MAX_VALUE);
            TIER_5_MAX_UNITS = COMMON_BUILDER
                    .comment("Max units for a tier 5 mob")
                    .defineInRange("tier5MaxUnits", Factory.TIER_5_MAX_UNITS_DEF, 5, Integer.MAX_VALUE);


            COMMON_BUILDER.push("cost");
            {
                TIER_1_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 1 factory")
                        .defineInRange("tier1UnitsPerTick", Factory.TIER_1_UNITS_PER_TICK_DEF, 1, Integer.MAX_VALUE);
                TIER_2_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 2 factory")
                        .defineInRange("tier2UnitsPerTick", Factory.TIER_2_UNITS_PER_TICK_DEF, 1, Integer.MAX_VALUE);
                TIER_3_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 3 factory")
                        .defineInRange("tier3UnitsPerTick", Factory.TIER_3_UNITS_PER_TICK_DEF, 1, Integer.MAX_VALUE);
                TIER_4_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 4 factory")
                        .defineInRange("tier4UnitsPerTick", Factory.TIER_4_UNITS_PER_TICK_DEF, 1, Integer.MAX_VALUE);
                TIER_5_UNITS_PER_TICK = COMMON_BUILDER
                        .comment("Units per tick cost to run a Tier 5 factory")
                        .defineInRange("tier5UnitsPerTick", Factory.TIER_5_UNITS_PER_TICK_DEF, 1, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();

            COMMON_BUILDER.push("perks");
            {
                EFFICIENCY_1 = COMMON_BUILDER
                        .comment("Percentage reduction for efficiency 1 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_EFFICIENCY_1_REDUCTION.name(), Factory.EFFICIENCY_1_DEF, 1, 100);
                EFFICIENCY_2 = COMMON_BUILDER
                        .comment("Percentage reduction for efficiency 2 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_EFFICIENCY_2_REDUCTION.name(), Factory.EFFICIENCY_2_DEF, 1, 100);
                EFFICIENCY_3 = COMMON_BUILDER
                        .comment("Percentage reduction for efficiency 3 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_EFFICIENCY_3_REDUCTION.name(), Factory.EFFICIENCY_3_DEF, 1, 100);

                MASS_COUNT_1 = COMMON_BUILDER
                        .comment("Number of mobs to spawn for mass 1 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_MASS_1_COUNT.name(), Factory.MASS_COUNT_1_DEF, 1, 100);
                MASS_COUNT_2 = COMMON_BUILDER
                        .comment("Number of mobs to spawn for mass 2 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_MASS_2_COUNT.name(), Factory.MASS_COUNT_2_DEF, 1, 100);
                MASS_COUNT_3 = COMMON_BUILDER
                        .comment("Number of mobs to spawn for mass 3 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_MASS_3_COUNT.name(), Factory.MASS_COUNT_3_DEF, 1, 100);

                RATE_1 = COMMON_BUILDER
                        .comment("Percentage reduction in spawn time for rate 1 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_RATE_1_REDUCTION.name(), Factory.RATE_1_DEF, 1, 99);
                RATE_2 = COMMON_BUILDER
                        .comment("Percentage reduction in spawn time for rate 2 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_RATE_2_REDUCTION.name(), Factory.RATE_2_DEF, 1, 99);
                RATE_3 = COMMON_BUILDER
                        .comment("Percentage reduction in spawn time for rate 3 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_RATE_3_REDUCTION.name(), Factory.RATE_3_DEF, 1, 99);
                XP_1 = COMMON_BUILDER
                        .comment("Percentage generate of XP for xp 1 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_XP_1_PERCENTAGE.name(), Factory.XP_1_DEF, 0, 1000);
                XP_2 = COMMON_BUILDER
                        .comment("Percentage generate of XP for xp 2 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_XP_2_PERCENTAGE.name(), Factory.XP_2_DEF, 0, 1000);
                XP_3 = COMMON_BUILDER
                        .comment("Percentage generate of XP for xp 3 perks")
                        .defineInRange(ConfigOverride.OverrideKey.PERK_XP_3_PERCENTAGE.name(), Factory.XP_3_DEF, 0, 1000);
            }
            COMMON_BUILDER.pop();

            COMMON_BUILDER.push("cell");
            {
                CELL_1_CAPACITY = COMMON_BUILDER
                        .comment("Storage capacity of a basic cell")
                        .worldRestart()
                        .defineInRange("cell1Capacity", Factory.CELL_1_CAPACITY_DEF, 1, Integer.MAX_VALUE);
                CELL_2_CAPACITY = COMMON_BUILDER
                        .comment("Storage capacity of an advanced cell")
                        .worldRestart()
                        .defineInRange("cell2Capacity", Factory.CELL_2_CAPACITY_DEF, 1, Integer.MAX_VALUE);
                CELL_3_CAPACITY = COMMON_BUILDER
                        .comment("Storage capacity of a premium cell")
                        .worldRestart()
                        .defineInRange("cell3Capacity", Factory.CELL_3_CAPACITY_DEF, 1, Integer.MAX_VALUE);
                CELL_1_MAX_TRANSFER = COMMON_BUILDER
                        .comment("Max transfer rate (per-side) of a basic cell")
                        .worldRestart()
                        .defineInRange("cell1MaxTransfer", Factory.CELL_1_MAX_TRANSFER_DEF, 1, Integer.MAX_VALUE);
                CELL_2_MAX_TRANSFER = COMMON_BUILDER
                        .comment("Max transfer rate (per-side) of an advanced cell")
                        .worldRestart()
                        .defineInRange("cell2MaxTransfer", Factory.CELL_2_MAX_TRANSFER_DEF, 1, Integer.MAX_VALUE);
                CELL_3_MAX_TRANSFER = COMMON_BUILDER
                        .comment("Max transfer rate (per-side) of a premium cell")
                        .worldRestart()
                        .defineInRange("cell3MaxTransfer", Factory.CELL_3_MAX_TRANSFER_DEF, 1, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
