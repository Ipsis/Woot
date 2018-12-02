package ipsis.woot.configuration.vanilla;

import ipsis.Woot;
import net.minecraftforge.common.config.Config;

@Config(modid = Woot.MODID, category = "plugin")
public class PluginConfig {

    @Config.RequiresMcRestart
    @Config.Comment("Support BloodMagic Ritual Of The Sanguine Urn")
    public static boolean ALLOW_BM_SANGUINE_URN = false;

    @Config.RequiresMcRestart
    @Config.Comment("Support BloodMagic Ritual Of The Mechanical Altar")
    public static boolean ALLOW_BM_MECHANICAL_ALTAR = false;

    @Config.RequiresMcRestart
    @Config.Comment("Support BloodMagic Ritual Of The Cloned Soul")
    public static boolean ALLOW_BM_CLONED_SOUL = false;

}
