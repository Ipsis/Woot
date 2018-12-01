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
}
