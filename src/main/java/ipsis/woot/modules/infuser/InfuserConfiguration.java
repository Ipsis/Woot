package ipsis.woot.modules.infuser;

import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

import ipsis.woot.config.ConfigDefaults.Infuser;

public class InfuserConfiguration {

    public static ForgeConfigSpec.IntValue INFUSER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue INFUSER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue INFUSER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue INFUSER_ENERGY_PER_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the infuser").push(ConfigPath.Infuser.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the infuser").push(ConfigPath.Infuser.CATEGORY);
        {
            INFUSER_TANK_CAPACITY = COMMON_BUILDER
                    .comment(ConfigPath.Common.TANK_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.TANK_CAPACITY_TAG,
                            Infuser.TANK_CAPACITY_DEF, 0, Integer.MAX_VALUE);
            INFUSER_MAX_ENERGY = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_CAPACITY_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_CAPACITY_TAG,
                            Infuser.MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
            INFUSER_MAX_ENERGY_RX = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_RX_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_RX_TAG,
                            Infuser.MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
            INFUSER_ENERGY_PER_TICK = COMMON_BUILDER
                    .comment(ConfigPath.Common.ENERGY_USE_PER_TICK_COMMENT)
                    .defineInRange(ConfigPath.Common.ENERGY_USE_PER_TICK_TAG,
                            Infuser.ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
