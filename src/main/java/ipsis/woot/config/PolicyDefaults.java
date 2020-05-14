package ipsis.woot.config;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class PolicyDefaults {

    public static final ArrayList<String> DEFAULT_CAPTURE_BLACKLIST_FULL_MOD = Lists.newArrayList(
            "botania"
    );

    public static final ArrayList<String> DEFAULT_CAPTURE_BLACKLIST_ENTITY = Lists.newArrayList(
    );

    public static final ArrayList<String> DEFAULT_LEARN_BLACKLIST_FULL_MOD = Lists.newArrayList(
    );
    public static final ArrayList<String> DEFAULT_LEARN_BLACKLIST_ITEM = Lists.newArrayList(
    );

    /**
     * These are for mod pack makers that want to stop the factory from generating
     * specific items.
     */
    public static final ArrayList<String> DEFAULT_GENERATE_BLACKLIST_FULL_MOD = Lists.newArrayList(
    );
    public static final ArrayList<String> DEFAULT_GENERATE_BLACKLIST_ITEM = Lists.newArrayList(
    );

    /**
     * These are for mod pack makers who want to provide shards/controllers via other methods.
     * See #415
     */

    public static final ArrayList<String> DEFAULT_SHARD_BLACKLIST_FULL_MOD = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_SHARD_BLACKLIST_ENTITY = Lists.newArrayList();

    public static final ArrayList<String> DEFAULT_CUSTOM_DROPS_ONLY = Lists.newArrayList(
            "minecraft:ender_dragon"
    );

    public static final ArrayList<String> DEFAULT_MOB_OVERRIDES = Lists.newArrayList(
            "minecraft:wither_skeleton,health,40",
            "minecraft:wither_skeleton,fixedTier,3",
            "minecraft:villager,fixedTier,3",
            "minecraft:magma_cube,fixedTier,2",
            "minecraft:enderman,fixedTier,3",
            "minecraft:villager_golem,fixedTier,3",
            "minecraft:guardian,fixedTier,3",
            "minecraft:blaze,fixedTier,2",
            "minecraft:witch,fixedTier,2",
            "minecraft:ghast,fixedTier,2",
            "minecraft:zombie_pigman,fixedTier,2",

            "minecraft:ender_dragon,health,500",
            "minecraft:ender_dragon,spawnTicks,12000",
            "minecraft:ender_dragon,fixedTier,4",
            "minecraft:ender_dragon,perkMassl1MobCount,2",
            "minecraft:ender_dragon,perkMassl2MobCount,3",
            "minecraft:ender_dragon,perkMassl3MobCount,4",

            "minecraft:wither,health,500",
            "minecraft:wither,spawnTicks,1200",
            "minecraft:wither,fixedTier,4",
            "minecraft:wither,perkMassl1MobCount,2",
            "minecraft:wither,perkMassl2MobCount,3",
            "minecraft:wither,perkMassl3MobCount,4"
    );
}
