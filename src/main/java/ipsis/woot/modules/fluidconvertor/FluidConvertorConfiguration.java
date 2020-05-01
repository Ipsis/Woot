package ipsis.woot.modules.fluidconvertor;

import ipsis.woot.config.ConfigDefaults;
import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class FluidConvertorConfiguration {

    public static ForgeConfigSpec.IntValue FLUID_CONV_INPUT_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue FLUID_CONV_OUTPUT_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue FLUID_CONV_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue FLUID_CONV_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue FLUID_CONV_ENERYG_PER_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the convertor").push(ConfigPath.FluidConvertor.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the convertor").push(ConfigPath.FluidConvertor.CATEGORY);
        {
            FLUID_CONV_INPUT_TANK_CAPACITY = COMMON_BUILDER
                    .comment(ConfigPath.Common.INPUT_TANK_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.INPUT_TANK_CAPACITY_TAG,
                            ConfigDefaults.FluidConvertor.INPUT_TANK_CAPACITY_DEF, 1000, Integer.MAX_VALUE);
            FLUID_CONV_OUTPUT_TANK_CAPACITY = COMMON_BUILDER
                    .comment(ConfigPath.Common.TANK_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.TANK_CAPACITY_TAG,
                            ConfigDefaults.FluidConvertor.OUTPUT_TANK_CAPACITY_DEF, 1000, Integer.MAX_VALUE);
            FLUID_CONV_MAX_ENERGY = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_CAPACITY_TAG,
                            ConfigDefaults.FluidConvertor.MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
            FLUID_CONV_MAX_ENERGY_RX = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_RX_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_RX_TAG,
                            ConfigDefaults.FluidConvertor.MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
            FLUID_CONV_ENERYG_PER_TICK = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_USE_PER_TICK_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_USE_PER_TICK_TAG,
                            ConfigDefaults.FluidConvertor.ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
