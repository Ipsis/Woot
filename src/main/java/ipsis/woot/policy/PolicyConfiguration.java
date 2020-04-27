package ipsis.woot.policy;

import ipsis.woot.config.ConfigPath;
import ipsis.woot.config.PolicyDefaults;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class PolicyConfiguration {

    public static ForgeConfigSpec.ConfigValue<List<String>> MOB_OVERRIDES;
    public static ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_ENTITY;
    public static ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_FULL_MOD;
    public static ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_ENTITY;
    public static ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_DROPS_ONLY;

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
                MOB_OVERRIDES = COMMON_BUILDER
                        .comment("A list of mob specific factory configuration values")
                        .define(ConfigPath.Policy.MOB_OVERRIDES_TAG,
                                PolicyDefaults.DEFAULT_MOB_OVERRIDES);
                CUSTOM_DROPS_ONLY = COMMON_BUILDER
                        .comment("A list of mobs that should not be simulated and use custom config only")
                        .define(ConfigPath.Policy.CUSTOM_DROPS_ONLY_TAG,
                                PolicyDefaults.DEFAULT_CUSTOM_DROPS_ONLY);
            }
            COMMON_BUILDER.pop();

        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
