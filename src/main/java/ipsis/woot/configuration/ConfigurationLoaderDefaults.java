package ipsis.woot.configuration;

public class ConfigurationLoaderDefaults {

    public static final String[] defMobConfigs = {
            "minecraft:villager_golem,SPAWN_XP,10",
            "minecraft:wither_skeleton,SPAWN_XP,10",
            "minecraft:magma_cube,SPAWN_XP,5",
            "minecraft:enderman,SPAWN_XP,10",
            "minecraft:ender_dragon,SPAWN_XP,750",
            "minecraft:ender_dragon,DEATH_XP,500",

            "minecraft:wither,FACTORY_TIER,4",

            "minecraft:enderman,FACTORY_TIER,3",
            "minecraft:villager_golem,FACTORY_TIER,3",
            "minecraft:guardian,FACTORY_TIER,3",
            "minecraft:wither_skeleton,FACTORY_TIER,3",

            "minecraft:blaze,FACTORY_TIER,2",
            "minecraft:witch,FACTORY_TIER,2",
            "minecraft:ghast,FACTORY_TIER,2",
            "minecraft:zombie_pigman,FACTORY_TIER,2",
            "minecraft:magma_cube,FACTORY_TIER,2"
    };

    public static final String[] defDragonDrops = {
            "NO_ENCHANT,minecraft:dragon_egg,1,20.0",
            "LOOTING_I,minecraft:dragon_egg,1,40.0",
            "LOOTING_II,minecraft:dragon_egg,1,60.0",
            "LOOTING_III,minecraft:dragon_egg,1,80.0",
            "NO_ENCHANT,minecraft:dragon_breath,2,80.0",
            "LOOTING_I,minecraft:dragon_breath,4,80.0",
            "LOOTING_II,minecraft:dragon_breath,6,80.0",
            "LOOTING_III,minecraft:dragon_breath,8,80.0",
            "NO_ENCHANT,draconicevolution:dragon_heart,1,20.0",
            "LOOTING_I,draconicevolution:dragon_heart,1,40.0",
            "LOOTING_II,draconicevolution:dragon_heart,1,60.0",
            "LOOTING_III,draconicevolution:dragon_heart,1,80.0",
            "NO_ENCHANT,draconicevolution:draconium_dust,30,20.0",
            "LOOTING_I,draconicevolution:draconium_dust,40,40.0",
            "LOOTING_II,draconicevolution:draconium_dust,50,60.0",
            "LOOTING_III,draconicevolution:draconium_dust,60,80.0",

    };

    // Blacklist all mobs from these mods
    public static final String[] internalModBlacklist = {
            "cyberware",
            "botania",
            "withercrumbs",
            "draconicevolution"
    };

    // Blacklist all these mobs
    public static final String[] internalMobBlacklist = {
            "arsmagica2.Dryad",
            "abyssalcraft.lesserdreadbeast",
            "abyssalcraft.greaterdreadspawn",
            "abyssalcraft.chagaroth",
            "abyssalcraft.shadowboss",
            "abyssalcraft.Jzahar",
            "roots.spriteGuardian"
    };

    // Blacklist all these items
    public static final String[] internalItemBlacklist = {

    };

    // Blacklist all items from these mods
    public static final String[] internalModItemBlacklist = {
            "eplus",
            "everlastingabilities",
            "cyberware"
    };
}
