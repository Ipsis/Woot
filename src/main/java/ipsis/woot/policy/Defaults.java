package ipsis.woot.policy;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class Defaults {

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


    public static final ArrayList<String> DEFAULT_MOB_OVERRIDES = Lists.newArrayList(
            "minecraft:wither_skeleton,HEALTH,40",
            "minecraft:wither_skeleton,TIER,3",
            "minecraft:villager,TIER,3",
            "minecraft:magma_cube,TIER,2",
            "minecraft:enderman,TIER,3",
            "minecraft:villager_golem,TIER,3",
            "minecraft:guardian,TIER,3",
            "minecraft:blaze,TIER,2",
            "minecraft:witch,TIER,2",
            "minecraft:ghast,TIER,2",
            "minecraft:zombie_pigman,TIER,2",

            "minecraft:ender_dragon,HEALTH,500",
            "minecraft:ender_dragon,SPAWN_TICKS,12000",
            "minecraft:ender_dragon,TIER,4",
            "minecraft:ender_dragon,PERK_MASS_1_COUNT,2",
            "minecraft:ender_dragon,PERK_MASS_2_COUNT,3",
            "minecraft:ender_dragon,PERK_MASS_3_COUNT,4",

            "minecraft:minecraft_wither,HEALTH,500",
            "minecraft:minecraft_wither,SPAWN_TICKS,1200",
            "minecraft:minecraft_wither,TIER,4",
            "minecraft:minecraft_wither,PERK_MASS_1_COUNT,2",
            "minecraft:minecraft_wither,PERK_MASS_2_COUNT,3",
            "minecraft:minecraft_wither,PERK_MASS_3_COUNT,4"
    );
}
