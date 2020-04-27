package ipsis.woot.modules.infuser;

import net.minecraftforge.common.ForgeConfigSpec;

import ipsis.woot.config.ConfigDefaults.Infuser;

public class InfuserConfiguration {

    public static final String CATEGORY_BUILDER = "infuser";

    public static ForgeConfigSpec.IntValue INFUSER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue INFUSER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue INFUSER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue INFUSER_ENERGY_PER_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the infuser").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the infuser").push(CATEGORY_BUILDER);
        {
            INFUSER_TANK_CAPACITY = COMMON_BUILDER
                    .comment("Maximum tank capacity (in mb)")
                    .defineInRange("tankMaxCapacity", Infuser.INFUSER_TANK_CAPACITY_DEF, 0, Integer.MAX_VALUE);
            INFUSER_MAX_ENERGY = COMMON_BUILDER
                    .comment("Maximum energy capacity (in RF)")
                    .defineInRange("energyMaxCapacity", Infuser.INFUSER_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
            INFUSER_MAX_ENERGY_RX = COMMON_BUILDER
                    .comment("Maximum energy that can be received (in RF/t)")
                    .defineInRange("energyMaxRx", Infuser.INFUSER_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
            INFUSER_ENERGY_PER_TICK = COMMON_BUILDER
                    .comment("How much energy per tick to use (in RF/t)")
                    .defineInRange("energyPerTick", Infuser.INFUSER_ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
