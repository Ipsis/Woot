package ipsis.woot.configuration.vanilla;

import ipsis.Woot;
import net.minecraftforge.common.config.Config;

@Config(modid = Woot.MODID, category = "client")
public class ClientConfig {

    @Config.Comment(value = "Layout uses colored block to render factory")
    public static boolean SIMPLE_LAYOUT = false;
}
