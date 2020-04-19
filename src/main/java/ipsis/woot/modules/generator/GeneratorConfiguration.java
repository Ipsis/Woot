package ipsis.woot.modules.generator;

import ipsis.woot.setup.ModDefaults;
import net.minecraftforge.common.ForgeConfigSpec;

public class GeneratorConfiguration {

    public static final String CATEGORY_BUILDER = "generator";

    public static ForgeConfigSpec.IntValue CONATUS_GEN_INPUT_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_OUTPUT_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue CONATUS_GEN_ENERGY_PER_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the generators").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the generators").push(CATEGORY_BUILDER);
        {
            COMMON_BUILDER.push("conatus");
            CONATUS_GEN_INPUT_TANK_CAPACITY = COMMON_BUILDER
                    .comment("Maximum input tank capacity (in mb)")
                    .defineInRange("inTankMaxCapacity",
                            ModDefaults.Generator.CONATUS_GEN_INPUT_TANK_CAPACITY_DEF, 1000, Integer.MAX_VALUE);
            CONATUS_GEN_OUTPUT_TANK_CAPACITY = COMMON_BUILDER
                    .comment("Maximum output tank capacity (in mb)")
                    .defineInRange("outTankMaxCapacity",
                            ModDefaults.Generator.CONATUS_GEN_OUTPUT_TANK_CAPACITY_DEF, 1000, Integer.MAX_VALUE);
            CONATUS_GEN_MAX_ENERGY = COMMON_BUILDER
                    .comment("Maximum energy capacity (in RF)")
                    .defineInRange("energyMaxCapacity", ModDefaults.Generator.CONATUS_GEN_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
            CONATUS_GEN_MAX_ENERGY_RX = COMMON_BUILDER
                    .comment("Maximum energy that can be received (in RF/t)")
                    .defineInRange("energyMaxRx", ModDefaults.Generator.CONATUS_GEN_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
            CONATUS_GEN_ENERGY_PER_TICK = COMMON_BUILDER
                    .comment("How much energy per tick to use (in RF/t)")
                    .defineInRange("energyPerTick", ModDefaults.Generator.CONATUS_GEN_RECIPE_ENERGY_DEF, 0, Integer.MAX_VALUE);
            COMMON_BUILDER.pop();
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
