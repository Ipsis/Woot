package ipsis.woot.modules.factory;

import net.minecraftforge.common.ForgeConfigSpec;

public class FactoryConfig {

    public static final String SUB_CAT_FACTORY = "factory";

    public static ForgeConfigSpec.DoubleValue RENDER_OPACITY;
    public static ForgeConfigSpec.DoubleValue RENDER_SIZE;

    public static void setup(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        CLIENT_BUILDER.comment("Factory display settings").push(SUB_CAT_FACTORY);
        {
            RENDER_OPACITY = CLIENT_BUILDER
                    .comment("Opacity of the rendered factory blocks")
                    .translation("woot.configgui.renderOpacity")
                    .defineInRange("renderOpacity", 0.95D, 0.10D, 1.10D);

            RENDER_SIZE = CLIENT_BUILDER
                    .comment("Size of the rendered factory blocks")
                    .translation("woot.configgui.renderSize")
                    .defineInRange("renderSize", 0.35D, .01D, 1.0D);

        }
        CLIENT_BUILDER.pop();

        SERVER_BUILDER.comment("Factory settings").push(SUB_CAT_FACTORY);
        {

        }
        SERVER_BUILDER.pop();

    }
}
