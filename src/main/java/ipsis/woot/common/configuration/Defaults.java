package ipsis.woot.common.configuration;

import java.util.Arrays;
import java.util.List;

public class Defaults {

    public static final List<String> DEFAULT_CAPTURE_BLACKLIST_FULL_MOD = Arrays.asList(
            "botania"
    );

    public static final List<String> DEFAULT_CAPTURE_BLACKLIST_ENTITY = Arrays.asList(
    );

    public static final List<String> DEFAULT_LEARN_BLACKLIST_FULL_MOD = Arrays.asList(
    );
    public static final List<String> DEFAULT_LEARN_BLACKLIST_ITEM = Arrays.asList(
    );

    /**
     * These are for mod pack makers that want to stop the factory from generating
     * specific items.
     */
    public static final List<String> DEFAULT_GENERATE_BLACKLIST_FULL_MOD = Arrays.asList(
    );
    public static final List<String> DEFAULT_GENERATE_BLACKLIST_ITEM = Arrays.asList(
    );

    /**
     * These are for mod pack makers who want to provide shards/controllers via other methods.
     * See #415
     */

    public static final List<String> DEFAULT_SHARD_BLACKLIST_FULL_MOD = Arrays.asList();
    public static final List<String> DEFAULT_SHARD_BLACKLIST_ENTITY = Arrays.asList();

    public static final List<String> DEFAULT_MOB_OVERRIDES = Arrays.asList(
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
}
