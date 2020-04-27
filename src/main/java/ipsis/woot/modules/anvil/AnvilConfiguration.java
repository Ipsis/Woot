package ipsis.woot.modules.anvil;

import ipsis.woot.config.ConfigDefaults;
import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class AnvilConfiguration {

    public static ForgeConfigSpec.BooleanValue ANVIL_PARTICILES;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the anvil").push(ConfigPath.Anvil.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the anvil").push(ConfigPath.Anvil.CATEGORY);
        {
            ANVIL_PARTICILES = CLIENT_BUILDER
                    .comment("Anvil generates particles when correctly placed")
                    .define(ConfigPath.Anvil.PARTICLES_TAG, ConfigDefaults.Anvil.PARTICLES_DEF);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
