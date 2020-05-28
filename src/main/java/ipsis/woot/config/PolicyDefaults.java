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
            "minecraft:wither_skeleton,fixedTier,4",
            "minecraft:villager,fixedTier,4",
            "minecraft:magma_cube,fixedTier,3",
            "minecraft:enderman,fixedTier,4",
            "minecraft:villager_golem,fixedTier,4",
            "minecraft:guardian,fixedTier,4",
            "minecraft:blaze,fixedTier,3",
            "minecraft:witch,fixedTier,3",
            "minecraft:ghast,fixedTier,3",
            "minecraft:zombie_pigman,fixedTier,3",

            "minecraft:iron_golem,xp,20",
            "minecraft:bat,xp,1",
            "minecraft:snow_golem,xp,1",
            "minecraft:villager,xp,2",
            "minecraft:wandering_villager,xp,2",

            "minecraft:ender_dragon,health,500",
            "minecraft:ender_dragon,spawnTicks,12000",
            "minecraft:ender_dragon,fixedTier,5",
            "minecraft:ender_dragon,xp,500",
            "minecraft:ender_dragon,perkMassl1MobCount,2",
            "minecraft:ender_dragon,perkMassl2MobCount,3",
            "minecraft:ender_dragon,perkMassl3MobCount,4",

            "minecraft:wither,spawnTicks,1200",
            "minecraft:wither,fixedTier,5",
            "minecraft:wither,perkMassl1MobCount,2",
            "minecraft:wither,perkMassl2MobCount,3",
            "minecraft:wither,perkMassl3MobCount,4"
    );
}
