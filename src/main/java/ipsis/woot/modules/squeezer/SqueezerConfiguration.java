package ipsis.woot.modules.squeezer;

import ipsis.woot.config.ConfigDefaults.Squeezer;
import net.minecraftforge.common.ForgeConfigSpec;

public class SqueezerConfiguration {

    public static final String CATEGORY_BUILDER = "squeezer";

    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_INTERNAL_FLUID_MAX;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_ENERGY_PER_TICK;

    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_RECIPE_ENERGY;
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
                        .defineInRange("tankMaxCapacity", Squeezer.DYE_SQUEEZER_TANK_CAPACITY_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_INTERNAL_FLUID_MAX = COMMON_BUILDER
                        .comment("Maximum internal fluid capacity(in mb)")
                        .defineInRange("internalFluidMaxCapacity", Squeezer.DYE_SQUEEZER_INTERNAL_FLUID_MAX_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_MAX_ENERGY = COMMON_BUILDER
                        .comment("Maximum energy capacity (in RF)")
                        .defineInRange("energyMaxCapacity", Squeezer.DYE_SQUEEZER_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_MAX_ENERGY_RX = COMMON_BUILDER
                        .comment("Maximum energy that can be received (in RF/t)")
                        .defineInRange("energyMaxRx", Squeezer.DYE_SQUEEZER_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_ENERGY_PER_TICK = COMMON_BUILDER
                        .comment("How much energy per tick to use (in RF/t)")
                        .defineInRange("energyPerTick", Squeezer.DYE_SQUEEZER_ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push("enchantment");
            {
                ENCH_SQUEEZER_TANK_CAPACITY = COMMON_BUILDER
                        .comment("Maximum tank capacity (in mb)")
                        .defineInRange("tankMaxCapacity", Squeezer.ENCH_SQUEEZER_TANK_CAPACITY_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_MAX_ENERGY = COMMON_BUILDER
                        .comment("Maximum energy capacity (in RF)")
                        .defineInRange("energyMaxCapacity", Squeezer.ENCH_SQUEEZER_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_MAX_ENERGY_RX = COMMON_BUILDER
                        .comment("Maximum energy that can be received (in RF/t)")
                        .defineInRange("energyMaxRx", Squeezer.ENCH_SQUEEZER_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_ENERGY_PER_TICK = COMMON_BUILDER
                        .comment("How much energy per tick to use (in RF/t)")
                        .defineInRange("energyPerTick", Squeezer.ENCH_SQUEEZER_ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_RECIPE_ENERGY = COMMON_BUILDER
                        .comment("How much energy to process one item (in RF/t)")
                        .defineInRange("energyPerRecipe", Squeezer.ENCH_SQUEEZER_RECIPE_ENERGY_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_1_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level I enchantment (in mb")
                        .defineInRange("level1Fluid", Squeezer.ENCH_SQUEEZER_LVL_1_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_2_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 2 enchantment (in mb")
                        .defineInRange("level2Fluid", Squeezer.ENCH_SQUEEZER_LVL_2_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_3_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 3 enchantment (in mb")
                        .defineInRange("level3Fluid", Squeezer.ENCH_SQUEEZER_LVL_3_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_4_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 4 enchantment (in mb")
                        .defineInRange("level4Fluid", Squeezer.ENCH_SQUEEZER_LVL_4_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_5_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 5 enchantment (in mb")
                        .defineInRange("level5Fluid", Squeezer.ENCH_SQUEEZER_LVL_5_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_EXTRA_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of extra fluid for each level above 5 (in mb")
                        .defineInRange("plus5Fluid", Squeezer.ENCH_SQUEEZER_EXTRA_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
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
