package ipsis.woot.modules.generator;

import ipsis.woot.config.ConfigDefaults;
import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class GeneratorConfiguration {

    public static ForgeConfigSpec.IntValue CONATUS_GEN_INPUT_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_OUTPUT_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_ENERGY_PER_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the generators").push(ConfigPath.Generator.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the generators").push(ConfigPath.Generator.CATEGORY);
        {
            COMMON_BUILDER.push("conatus");
            CONATUS_GEN_INPUT_TANK_CAPACITY = COMMON_BUILDER
                    .comment(ConfigPath.Common.INPUT_TANK_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.INPUT_TANK_CAPACITY_TAG,
                            ConfigDefaults.Generator.CONATUS_GEN_INPUT_TANK_CAPACITY_DEF, 1000, Integer.MAX_VALUE);
            CONATUS_GEN_OUTPUT_TANK_CAPACITY = COMMON_BUILDER
                    .comment(ConfigPath.Common.TANK_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.TANK_CAPACITY_TAG,
                            ConfigDefaults.Generator.CONATUS_GEN_OUTPUT_TANK_CAPACITY_DEF, 1000, Integer.MAX_VALUE);
            CONATUS_GEN_MAX_ENERGY = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_CAPACITY_TAG,
                            ConfigDefaults.Generator.CONATUS_GEN_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
            CONATUS_GEN_MAX_ENERGY_RX = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_RX_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_RX_TAG,
                            ConfigDefaults.Generator.CONATUS_GEN_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
            CONATUS_GEN_ENERGY_PER_TICK = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_USE_PER_TICK_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_USE_PER_TICK_TAG,
                            ConfigDefaults.Generator.CONATUS_GEN_RECIPE_ENERGY_DEF, 0, Integer.MAX_VALUE);
            COMMON_BUILDER.pop();
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
