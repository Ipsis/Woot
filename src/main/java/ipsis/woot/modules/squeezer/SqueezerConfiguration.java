package ipsis.woot.modules.squeezer;

import ipsis.woot.common.configuration.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class SqueezerConfiguration {

    public static final String CATEGORY_BUILDER = "squeezer";

    public static ForgeConfigSpec.IntValue OUTPUT_CAPACITY;
//    public static ForgeConfigSpec.IntValue INTERNAL_CAPACITY;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the squeezer").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the squeezer").push(CATEGORY_BUILDER);

        String TAG2 = "outputMaxCapacity";
        OUTPUT_CAPACITY = COMMON_BUILDER
                .comment("Maximum output tank capacity (in mB)")
                .translation(Config.TAG + TAG2)
                .defineInRange(TAG2, 32000, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
