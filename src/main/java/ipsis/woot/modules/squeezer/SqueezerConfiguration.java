package ipsis.woot.modules.squeezer;

import net.minecraftforge.common.ForgeConfigSpec;

public class SqueezerConfiguration {

    public static final String CATEGORY_BUILDER = "squeezer";

    public static ForgeConfigSpec.IntValue TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue MAX_ENERGY;
    public static ForgeConfigSpec.IntValue MAX_ENERGY_RX;


    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the squeezer").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the squeezer").push(CATEGORY_BUILDER);
        {
            TANK_CAPACITY = COMMON_BUILDER
                    .comment("Maximum tank capacity (in mB)")
                    .defineInRange("tankMaxCapacity", DyeMakeup.LCM * 100, 0, Integer.MAX_VALUE);
            MAX_ENERGY = COMMON_BUILDER
                    .comment("Maximum energy capacity (in RF)")
                    .defineInRange("energyMaxCapacity", 1000, 0, Integer.MAX_VALUE);
            MAX_ENERGY_RX = COMMON_BUILDER
                    .comment("Maximum energy that can be received (in RF/t)")
                    .defineInRange("energyMaxRx", 100, 0, Integer.MAX_VALUE);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
