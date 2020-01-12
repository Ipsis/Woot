package ipsis.woot.modules.squeezer;

import net.minecraftforge.common.ForgeConfigSpec;

public class SqueezerConfiguration {

    public static final String CATEGORY_BUILDER = "squeezer";

    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_INTERNAL_FLUID_MAX;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_MAX_ENERGY_RX;

    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_1_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_2_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_3_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_4_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_5_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_EXTRA_ENCHANT_MB;


    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the squeezer").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the squeezer").push(CATEGORY_BUILDER);
        {
            COMMON_BUILDER.push("dye");
            {
                DYE_SQUEEZER_TANK_CAPACITY = COMMON_BUILDER
                        .comment("Maximum tank capacity (in mb)")
                        .defineInRange("tankMaxCapacity", DyeMakeup.LCM * 100, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_INTERNAL_FLUID_MAX = COMMON_BUILDER
                        .comment("Maximum internal fluid capacity(in mb)")
                        .defineInRange("internalFluidMaxCapacity", DyeMakeup.LCM * 10, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_MAX_ENERGY = COMMON_BUILDER
                        .comment("Maximum energy capacity (in RF)")
                        .defineInRange("energyMaxCapacity", 1000, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_MAX_ENERGY_RX = COMMON_BUILDER
                        .comment("Maximum energy that can be received (in RF/t)")
                        .defineInRange("energyMaxRx", 100, 0, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("enchantment");
            {
                ENCH_SQUEEZER_TANK_CAPACITY = COMMON_BUILDER
                        .comment("Maximum tank capacity (in mb)")
                        .defineInRange("tankMaxCapacity", 5 * 1000, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_MAX_ENERGY = COMMON_BUILDER
                        .comment("Maximum energy capacity (in RF)")
                        .defineInRange("energyMaxCapacity", 1000, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_MAX_ENERGY_RX = COMMON_BUILDER
                        .comment("Maximum energy that can be received (in RF/t)")
                        .defineInRange("energyMaxRx", 100, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_1_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level I enchantment (in mb")
                        .defineInRange("level1Fluid", 500, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_2_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 2 enchantment (in mb")
                        .defineInRange("level2Fluid", 1000, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_3_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 3 enchantment (in mb")
                        .defineInRange("level3Fluid", 1500, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_4_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 4 enchantment (in mb")
                        .defineInRange("level4Fluid", 2000, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_5_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 5 enchantment (in mb")
                        .defineInRange("level5Fluid", 2500, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_EXTRA_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of extra fluid for each level above 5 (in mb")
                        .defineInRange("plus5Fluid", 500, 0, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }

    public static int getEnchantFluidAmount(int level) {
        if (level <= 1) return ENCH_SQUEEZER_LVL_1_ENCHANT_MB.get();
        if (level == 2) return ENCH_SQUEEZER_LVL_2_ENCHANT_MB.get();
        if (level == 3) return ENCH_SQUEEZER_LVL_3_ENCHANT_MB.get();
        if (level == 4) return ENCH_SQUEEZER_LVL_4_ENCHANT_MB.get();
        if (level == 5) return ENCH_SQUEEZER_LVL_5_ENCHANT_MB.get();

        level -= 5;
        return ENCH_SQUEEZER_LVL_5_ENCHANT_MB.get() + (level * ENCH_SQUEEZER_EXTRA_ENCHANT_MB.get());

    }
}
