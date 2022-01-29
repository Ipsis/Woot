package ipsis.woot.modules.squeezer;

import net.minecraftforge.common.ForgeConfigSpec;

public class SqueezerConfig {

    public static final String SUB_CAT_SQUEEZER = "squeezer";

    public static void setup(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        CLIENT_BUILDER.comment("").push(SUB_CAT_SQUEEZER);
        CLIENT_BUILDER.pop();

        SERVER_BUILDER.comment("Squeezer settings").push(SUB_CAT_SQUEEZER);
        SERVER_BUILDER.pop();
    }
}
