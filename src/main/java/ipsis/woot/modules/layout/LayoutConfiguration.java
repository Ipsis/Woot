package ipsis.woot.modules.layout;

import ipsis.woot.config.ConfigDefaults;
import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class LayoutConfiguration {

    public static ForgeConfigSpec.BooleanValue SIMPLE_LAYOUT;
    public static ForgeConfigSpec.DoubleValue RENDER_OPACITY;
    public static ForgeConfigSpec.DoubleValue RENDER_SIZE;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the layout guide").push(ConfigPath.Layout.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the layout guide").push(ConfigPath.Layout.CATEGORY);
        {
            SIMPLE_LAYOUT = CLIENT_BUILDER
                    .comment("Use untextured blocks to render the factory")
                    .define(ConfigPath.Layout.SIMPLE_TAG,
                            ConfigDefaults.Layout.SIMPLE_DEF);

            RENDER_OPACITY = CLIENT_BUILDER
                    .comment("Opacity of the layout blocks")
                    .defineInRange(ConfigPath.Layout.RENDER_OPACITY_TAG,
                            ConfigDefaults.Layout.OPACITY_DEF, 0.10D, 1.00D);

            RENDER_SIZE = CLIENT_BUILDER
                    .comment("Size of the layout blocks")
                    .defineInRange(ConfigPath.Layout.RENDER_SIZE_TAG,
                            ConfigDefaults.Layout.SIZE_DEF, 0.1D, 1.0D);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
