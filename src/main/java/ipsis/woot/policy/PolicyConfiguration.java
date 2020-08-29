package ipsis.woot.policy;

import ipsis.woot.config.ConfigPath;
import ipsis.woot.config.PolicyDefaults;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class PolicyConfiguration {

    public static ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_ENTITY;
    public static ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_ENTITY;
    public static ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_DROPS_ONLY;

    // Override values
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_MASS_COUNT;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_SPAWN_TICKS;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_HEALTH;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_UNITS_PER_HEALTH;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_XP;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_TIER;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_SHARD_KILLS;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_FIXED_COST;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_EFFICIENCY_1;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_EFFICIENCY_2;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_EFFICIENCY_3;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_MASS_1;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_MASS_2;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_MASS_3;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_RATE_1;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_RATE_2;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_RATE_3;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_XP_1;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_XP_2;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_XP_3;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_HEADLESS_1;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_HEADLESS_2;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_HEADLESS_3;
    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_PERK_HEADLESS_SKULLS;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the factory").push(ConfigPath.Policy.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the factory").push(ConfigPath.Policy.CATEGORY);
        {
            COMMON_BUILDER.push(ConfigPath.Policy.CATEGORY_BLACKLIST);
            {
                CAPTURE_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not capture any entity from the following mods")
                        .define(ConfigPath.Policy.CAPTURE_BLACKLIST_FULL_MOD_TAG,
                                PolicyDefaults.DEFAULT_CAPTURE_BLACKLIST_FULL_MOD);
                CAPTURE_BLACKLIST_ENTITY = COMMON_BUILDER
                        .comment("Do not capture the following entities")
                        .define(ConfigPath.Policy.CAPTURE_BLACKLIST_ENTITY_TAG,
                                PolicyDefaults.DEFAULT_CAPTURE_BLACKLIST_ENTITY);
                LEARN_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not learn items from the following mods")
                        .define(ConfigPath.Policy.LEARN_BLACKLIST_FULL_MOD_TAG,
                                PolicyDefaults.DEFAULT_LEARN_BLACKLIST_FULL_MOD);
                LEARN_BLACKLIST_ITEM = COMMON_BUILDER
                        .comment("Do not learn the following items")
                        .define(ConfigPath.Policy.LEARN_BLACKLIST_ITEM_TAG,
                                PolicyDefaults.DEFAULT_LEARN_BLACKLIST_ITEM);
                GENERATE_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not generate items from the following mods")
                        .define(ConfigPath.Policy.GENERATE_BLACKLIST_FULL_MOD_TAG,
                                PolicyDefaults.DEFAULT_GENERATE_BLACKLIST_FULL_MOD);
                GENERATE_BLACKLIST_ITEM = COMMON_BUILDER
                        .comment("Do not generate the following items")
                        .define(ConfigPath.Policy.GENERATE_BLACKLIST_ITEM_TAG,
                                PolicyDefaults.DEFAULT_GENERATE_BLACKLIST_ITEM);
                SHARD_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not allow shard creation with entities from the following mods")
                        .define(ConfigPath.Policy.SHARD_BLACKLIST_FULL_MOD_TAG,
                                PolicyDefaults.DEFAULT_SHARD_BLACKLIST_FULL_MOD);
                SHARD_BLACKLIST_ENTITY = COMMON_BUILDER
                        .comment("Do not allow shard creation with the following entities")
                        .define(ConfigPath.Policy.SHARD_BLACKLIST_ENTITY_TAG,
                                PolicyDefaults.DEFAULT_SHARD_BLACKLIST_ENTITY);
            }
            COMMON_BUILDER.pop();

            COMMON_BUILDER.push(ConfigPath.Policy.CATEGORY_MOB);
            {
                MOB_MASS_COUNT = COMMON_BUILDER
                    .comment("Override default mass count for a mob")
                    .define(ConfigPath.Policy.MOB_MASS_COUNT_TAG,
                            PolicyDefaults.DEFAULT_MOB_MASS_COUNT);
                MOB_SPAWN_TICKS = COMMON_BUILDER
                        .comment("Override default spawn ticks for a mob")
                        .define(ConfigPath.Policy.MOB_SPAWN_TICKS_TAG,
                                PolicyDefaults.DEFAULT_MOB_SPAWN_TICKS);
                MOB_HEALTH = COMMON_BUILDER
                        .comment("Override default health for a mob")
                        .define(ConfigPath.Policy.MOB_HEALTH_TAG,
                                PolicyDefaults.DEFAULT_MOB_HEALTH);
                MOB_UNITS_PER_HEALTH = COMMON_BUILDER
                        .comment("Override default units per health for a mob")
                        .define(ConfigPath.Policy.MOB_UNITS_PER_HEALTH_TAG,
                                PolicyDefaults.DEFAULT_MOB_UNITS_PER_HEALTH);
                MOB_XP = COMMON_BUILDER
                        .comment("Override default xp for a mob")
                        .define(ConfigPath.Policy.MOB_XP_TAG,
                                PolicyDefaults.DEFAULT_MOB_XP);
                MOB_TIER = COMMON_BUILDER
                        .comment("Override default tier for a mob")
                        .define(ConfigPath.Policy.MOB_TIER_TAG,
                                PolicyDefaults.DEFAULT_MOB_TIER);
                MOB_SHARD_KILLS = COMMON_BUILDER
                        .comment("Override default shard kills for a mob")
                        .define(ConfigPath.Policy.MOB_SHARD_KILLS_TAG,
                                PolicyDefaults.DEFAULT_MOB_SHARD_KILLS);
                MOB_FIXED_COST = COMMON_BUILDER
                        .comment("Set a fixed cost for a mob")
                        .define(ConfigPath.Policy.MOB_FIXED_COST_TAG,
                                PolicyDefaults.DEFAULT_MOB_FIXED_COST);

                CUSTOM_DROPS_ONLY = COMMON_BUILDER
                        .comment("A list of mobs that should not be simulated and use custom config only")
                        .define(ConfigPath.Policy.CUSTOM_DROPS_ONLY_TAG,
                                PolicyDefaults.DEFAULT_CUSTOM_DROPS_ONLY);

                COMMON_BUILDER.push(ConfigPath.Policy.CATEGORY_PERK);
                {
                    MOB_PERK_EFFICIENCY_1 = COMMON_BUILDER
                            .comment("Set efficiency perk level 1 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_EFFICIENCY_1_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_EFFICIENCY_1);
                    MOB_PERK_EFFICIENCY_2 = COMMON_BUILDER
                            .comment("Set efficiency perk level 2 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_EFFICIENCY_2_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_EFFICIENCY_2);
                    MOB_PERK_EFFICIENCY_3 = COMMON_BUILDER
                            .comment("Set efficiency perk level 3 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_EFFICIENCY_3_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_EFFICIENCY_3);

                    MOB_PERK_MASS_1 = COMMON_BUILDER
                            .comment("Set mass perk level 1 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_MASS_1_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_MASS_1);
                    MOB_PERK_MASS_2 = COMMON_BUILDER
                            .comment("Set mass perk level 2 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_MASS_2_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_MASS_2);
                    MOB_PERK_MASS_3 = COMMON_BUILDER
                            .comment("Set mass perk level 3 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_MASS_3_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_MASS_3);

                    MOB_PERK_RATE_1 = COMMON_BUILDER
                            .comment("Set rate perk level 1 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_RATE_1_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_RATE_1);
                    MOB_PERK_RATE_2 = COMMON_BUILDER
                            .comment("Set rate perk level 2 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_RATE_2_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_RATE_2);
                    MOB_PERK_RATE_3 = COMMON_BUILDER
                            .comment("Set rate perk level 3 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_RATE_3_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_RATE_3);

                    MOB_PERK_XP_1 = COMMON_BUILDER
                            .comment("Set xp perk level 1 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_XP_1_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_XP_1);
                    MOB_PERK_XP_2 = COMMON_BUILDER
                            .comment("Set xp perk level 2 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_XP_2_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_XP_2);
                    MOB_PERK_XP_3 = COMMON_BUILDER
                            .comment("Set xp perk level 3 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_XP_3_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_XP_3);

                    MOB_PERK_HEADLESS_1 = COMMON_BUILDER
                            .comment("Set headless perk level 1 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_HEADLESS_1_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_HEADLESS_1);
                    MOB_PERK_HEADLESS_2 = COMMON_BUILDER
                            .comment("Set headless perk level 2 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_HEADLESS_2_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_HEADLESS_2);
                    MOB_PERK_HEADLESS_3 = COMMON_BUILDER
                            .comment("Set headless perk level 3 for a mob")
                            .define(ConfigPath.Policy.MOB_PERK_HEADLESS_3_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_HEADLESS_3);

                    MOB_PERK_HEADLESS_SKULLS = COMMON_BUILDER
                            .comment("A list of mobs and their skull drop")
                            .define(ConfigPath.Policy.MOB_PERK_HEADLESS_SKULLS_TAG,
                                    PolicyDefaults.DEFAULT_MOB_PERK_HEADLESS_SKULLS);
                }

            }
            COMMON_BUILDER.pop();

        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
