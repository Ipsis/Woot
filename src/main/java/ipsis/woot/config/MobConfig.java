package ipsis.woot.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

/**
 * For overriding upgrade parameters etc based on mob.
 * eg. mobname,param,value
 */
public class MobConfig {

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> MOB_OVERRIDE;

    public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {

        serverBuilder.push("mob");

        MOB_OVERRIDE = serverBuilder
                .comment("A list of mob specific factory configuration values")
                .defineList("configOverride", Collections.emptyList(), obj -> obj instanceof String);

         serverBuilder.pop();
    }
}
