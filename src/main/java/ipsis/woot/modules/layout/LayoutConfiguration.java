package ipsis.woot.modules.layout;

import net.minecraftforge.common.ForgeConfigSpec;

public class LayoutConfiguration {

    public static final String CATEGORY_LAYOUT = "layout";

    public static ForgeConfigSpec.BooleanValue SIMPLE_LAYOUT;
    public static ForgeConfigSpec.DoubleValue RENDER_OPACITY;
    public static ForgeConfigSpec.DoubleValue RENDER_SIZE;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the layout guide").push(CATEGORY_LAYOUT);
        CLIENT_BUILDER.comment("Settings for the layout guide").push(CATEGORY_LAYOUT);
        {
            SIMPLE_LAYOUT = CLIENT_BUILDER
                    .comment("Use untextured blocks to render the factory")
                    .define("simpleLayout", false);

            RENDER_OPACITY = CLIENT_BUILDER
                    .comment("Opacity of the layout blocks")
                    .defineInRange("layoutOpacity", 0.95D, 0.10D, 1.00D);

            RENDER_SIZE = CLIENT_BUILDER
                    .comment("Size of the layout blocks")
                    .defineInRange("layoutSize", 0.35D, 0.10D, 1.0D);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
