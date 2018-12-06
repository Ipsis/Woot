package ipsis.woot.configuration.vanilla;

import ipsis.Woot;
import net.minecraftforge.common.config.Config;

@Config(modid = Woot.MODID)
public class GeneralConfig {

    @Config.RequiresMcRestart
    @Config.Comment(value = "Dimension id of Tartarus")
    public static int TARTARUS_ID = 418;

    @Config.Comment(value = "Number of mobs to kill to learn from - higher is more accurate")
    public static int LEARN_MOB_COUNT = 500;

    @Config.Comment(value = "Number of ticks between fake kills")
    public static int LEARN_TICKS = 20;

    @Config.Comment(value = "Allow the factory generate XP shard")
    public static boolean ALLOW_XP_GEN = true;

    // Headhunter is the enchant
    @Config.Comment("Percentage chance of Headhunter 1 dropping a skull")
    public static int HEADHUNTER_1_DROP = 30;
    @Config.Comment("Percentage chance of Headhunter 2 dropping a skull")
    public static int HEADHUNTER_2_DROP = 50;
    @Config.Comment("Percentage chance of Headhunter 3 dropping a skull")
    public static int HEADHUNTER_3_DROP = 80;
}
