package ipsis.woot.configuration.vanilla;

import ipsis.Woot;
import net.minecraftforge.common.config.Config;

@Config(modid = Woot.MODID, category = "factory")
public class FactoryConfig {

    @Config.Comment(value = "Number of mobs to spawn with no mass upgrade")
    public static int NUM_MOBS = 1;

    @Config.Comment(value = "Number of ticks to spawn a mob")
    public static int SPAWN_TIME = 300;

    @Config.Comment(value = "Number of WU per health point")
    public static int UNITS_PER_HEALTH = 1;

    @Config.Comment(value = "Maximum mob health that tier 1 can handle")
    public static int TIER_1_MAX_MOB_HEALTH = 20;

    @Config.Comment(value = "Maximum mob health that tier 2 can handle")
    public static int TIER_2_MAX_MOB_HEALTH = 40;

    @Config.Comment(value = "Maximum mob health that tier 3 can handle")
    public static int TIER_3_MAX_MOB_HEALTH = 60;

}
