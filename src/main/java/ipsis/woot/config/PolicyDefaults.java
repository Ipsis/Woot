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

    public static final ArrayList<String> DEFAULT_MOB_MASS_COUNT = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_SPAWN_TICKS = Lists.newArrayList(
            "minecraft:wither,1200",
            "minecraft:ender_dragon,12000"
    );
    public static final ArrayList<String> DEFAULT_MOB_HEALTH = Lists.newArrayList(
            "minecraft:ender_dragon,500"
    );
    public static final ArrayList<String> DEFAULT_MOB_UNITS_PER_HEALTH = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_XP = Lists.newArrayList(
            "minecraft:iron_golem,20",
            "minecraft:bat,1",
            "minecraft:snow_golem,1",
            "minecraft:villager,2",
            "minecraft:wandering_villager,2",
            "minecraft:ender_dragon,500"
    );
    public static final ArrayList<String> DEFAULT_MOB_TIER = Lists.newArrayList(
            "minecraft:wither_skeleton,4",
            "minecraft:villager,4",
            "minecraft:magma_cube,3",
            "minecraft:enderman,4",
            "minecraft:villager_golem,4",
            "minecraft:guardian,4",
            "minecraft:blaze,3",
            "minecraft:witch,3",
            "minecraft:ghast,3",
            "minecraft:zombie_pigman,3",
            "minecraft:ender_dragon,5",
            "minecraft:wither,5"
    );
    public static final ArrayList<String> DEFAULT_MOB_SHARD_KILLS = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_FIXED_COST = Lists.newArrayList();

    public static final ArrayList<String> DEFAULT_MOB_PERK_EFFICIENCY_1 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_EFFICIENCY_2 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_EFFICIENCY_3 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_MASS_1 = Lists.newArrayList(
            "minecraft:ender_dragon,2",
            "minecraft:wither,2"
    );
    public static final ArrayList<String> DEFAULT_MOB_PERK_MASS_2 = Lists.newArrayList(
            "minecraft:ender_dragon,3",
            "minecraft:wither,3"
    );
    public static final ArrayList<String> DEFAULT_MOB_PERK_MASS_3 = Lists.newArrayList(
            "minecraft:ender_dragon,4",
            "minecraft:wither,4"
    );
    public static final ArrayList<String> DEFAULT_MOB_PERK_RATE_1 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_RATE_2 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_RATE_3 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_XP_1 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_XP_2 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_XP_3 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_HEADLESS_1 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_HEADLESS_2 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_HEADLESS_3 = Lists.newArrayList();
    public static final ArrayList<String> DEFAULT_MOB_PERK_HEADLESS_SKULLS = Lists.newArrayList(
            "minecraft:skeleton,{\"item\": \"minecraft:skeleton_skull\"}",
            "minecraft:wither_skeleton,{\"item\": \"minecraft:wither_skeleton_skull\"}",
            "minecraft:zombie,{\"item\": \"minecraft:zombie_head\"}",
            "minecraft:creeper,{\"item\": \"minecraft:creeper_head\"}"
    );
}
