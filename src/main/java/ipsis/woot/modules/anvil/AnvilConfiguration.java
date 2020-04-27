package ipsis.woot.modules.anvil;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnvilConfiguration {

    public static final String CATEGORY_BUILDER = "anvil";

    public static ForgeConfigSpec.BooleanValue ANVIL_PARTICILES;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the anvil").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the anvil").push(CATEGORY_BUILDER);
        {
            ANVIL_PARTICILES = CLIENT_BUILDER
                    .comment("Anvil generates particles when correctly placed")
                    .define("anvilParticles", true);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
