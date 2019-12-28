package ipsis.woot.policy;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class PolicyConfiguration {

    public static final String CATEGORY_POLICY = "policy";

    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_OVERRIDES;
    public static ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_ENTITY;
    public static ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_ENTITY;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the factory").push(CATEGORY_POLICY);
        CLIENT_BUILDER.comment("Settings for the factory").push(CATEGORY_POLICY);
        {
            COMMON_BUILDER.push("blacklist");
            {
                CAPTURE_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not capture any entity from the following mods")
                        .define("captureFullMod", Defaults.DEFAULT_CAPTURE_BLACKLIST_FULL_MOD);
                CAPTURE_BLACKLIST_ENTITY = COMMON_BUILDER
                        .comment("Do not capture the following entities")
                        .define("captureEntity", Defaults.DEFAULT_CAPTURE_BLACKLIST_ENTITY);
                LEARN_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not learn items from the following mods")
                        .define("learnFullMod", Defaults.DEFAULT_LEARN_BLACKLIST_FULL_MOD);
                LEARN_BLACKLIST_ITEM = COMMON_BUILDER
                        .comment("Do not learn the following items")
                        .define("learnItem", Defaults.DEFAULT_LEARN_BLACKLIST_ITEM);
                GENERATE_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not generate items from the following mods")
                        .define("generateFullMod", Defaults.DEFAULT_GENERATE_BLACKLIST_FULL_MOD);
                GENERATE_BLACKLIST_ITEM = COMMON_BUILDER
                        .comment("Do not generate the following items")
                        .define("generateItem", Defaults.DEFAULT_GENERATE_BLACKLIST_ITEM);
                SHARD_BLACKLIST_FULL_MOD = COMMON_BUILDER
                        .comment("Do not allow shard creation with entities from the following mods")
                        .define("shardFullMod", Defaults.DEFAULT_SHARD_BLACKLIST_FULL_MOD);
                SHARD_BLACKLIST_ENTITY = COMMON_BUILDER
                        .comment("Do not allow shard creation with the following entities")
                        .define("shardEntity", Defaults.DEFAULT_SHARD_BLACKLIST_ENTITY);
            }
            COMMON_BUILDER.pop();

            COMMON_BUILDER.push("mob");
            {
                MOB_OVERRIDES = COMMON_BUILDER
                        .comment("A list of mob specific factory configuration values")
                        .define("mobOverrides", Defaults.DEFAULT_MOB_OVERRIDES);
            }
            COMMON_BUILDER.pop();

        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
