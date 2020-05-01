package ipsis.woot.modules.squeezer;

import ipsis.woot.config.ConfigDefaults.Squeezer;
import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class SqueezerConfiguration {

    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_INTERNAL_FLUID_MAX;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue DYE_SQUEEZER_ENERGY_PER_TICK;

    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_ENERGY_PER_TICK;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_1_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_2_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_3_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_4_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_5_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_EXTRA_ENCHANT_MB;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_1_ENERGY_COST;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_2_ENERGY_COST;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_3_ENERGY_COST;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_4_ENERGY_COST;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_LVL_5_ENERGY_COST;
    public static ForgeConfigSpec.IntValue ENCH_SQUEEZER_EXTRA_ENERGY_COST;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the squeezer").push(ConfigPath.Squeezer.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the squeezer").push(ConfigPath.Squeezer.CATEGORY);
        {
            COMMON_BUILDER.push(ConfigPath.Squeezer.CATEGORY_DYE_SQUEEZER);
            {
                DYE_SQUEEZER_TANK_CAPACITY = COMMON_BUILDER
                        .comment(ConfigPath.Common.TANK_CAPACITY_COMMENT)
                        .defineInRange(ConfigPath.Common.TANK_CAPACITY_TAG,
                                Squeezer.DYE_SQUEEZER_TANK_CAPACITY_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_INTERNAL_FLUID_MAX = COMMON_BUILDER
                        .comment(ConfigPath.Common.INTERNAL_TANK_CAPACITY_COMMENT)
                        .defineInRange(ConfigPath.Common.INTERNAL_TANK_CAPACITY_TAG,
                                Squeezer.DYE_SQUEEZER_INTERNAL_FLUID_MAX_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_MAX_ENERGY = COMMON_BUILDER
                        .comment(ConfigPath.Common.ENERGY_CAPACITY_COMMENT)
                        .defineInRange(ConfigPath.Common.ENERGY_CAPACITY_TAG,
                                Squeezer.DYE_SQUEEZER_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_MAX_ENERGY_RX = COMMON_BUILDER
                        .comment(ConfigPath.Common.ENERGY_RX_COMMENT)
                        .defineInRange(ConfigPath.Common.ENERGY_RX_TAG,
                                Squeezer.DYE_SQUEEZER_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
                DYE_SQUEEZER_ENERGY_PER_TICK = COMMON_BUILDER
                        .comment(ConfigPath.Common.ENERGY_USE_PER_TICK_COMMENT)
                        .defineInRange(ConfigPath.Common.ENERGY_USE_PER_TICK_TAG,
                                Squeezer.DYE_SQUEEZER_ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
            }
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(ConfigPath.Squeezer.CATEGORY_ENCHANT_SQUEEZER);
            {
                ENCH_SQUEEZER_TANK_CAPACITY = COMMON_BUILDER
                        .comment(ConfigPath.Common.TANK_CAPACITY_COMMENT)
                        .defineInRange(ConfigPath.Common.TANK_CAPACITY_TAG,
                                Squeezer.ENCH_SQUEEZER_TANK_CAPACITY_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_MAX_ENERGY = COMMON_BUILDER
                        .comment(ConfigPath.Common.ENERGY_CAPACITY_COMMENT)
                        .defineInRange(ConfigPath.Common.ENERGY_CAPACITY_TAG,
                                Squeezer.ENCH_SQUEEZER_MAX_ENERGY_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_MAX_ENERGY_RX = COMMON_BUILDER
                        .comment(ConfigPath.Common.ENERGY_RX_COMMENT)
                        .defineInRange(ConfigPath.Common.ENERGY_RX_TAG,
                                Squeezer.ENCH_SQUEEZER_MAX_ENERGY_RX_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_ENERGY_PER_TICK = COMMON_BUILDER
                        .comment(ConfigPath.Common.ENERGY_USE_PER_TICK_COMMENT)
                        .defineInRange(ConfigPath.Common.ENERGY_USE_PER_TICK_TAG,
                                Squeezer.ENCH_SQUEEZER_ENERGY_PER_TICK_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_1_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level I enchantment (in mb)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_1_ENCHANT_MB_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_1_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_2_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 2 enchantment (in mb)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_2_ENCHANT_MB_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_2_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_3_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 3 enchantment (in mb)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_3_ENCHANT_MB_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_3_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_4_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 4 enchantment (in mb)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_4_ENCHANT_MB_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_4_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_5_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of fluid for a level 5 enchantment (in mb)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_5_ENCHANT_MB_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_5_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_EXTRA_ENCHANT_MB = COMMON_BUILDER
                        .comment("Amount of extra fluid for each level above 5 (in mb)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_EXTRA_ENCHANT_MB_TAG,
                                Squeezer.ENCH_SQUEEZER_EXTRA_ENCHANT_MB_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_1_ENERGY_COST = COMMON_BUILDER
                        .comment("How much energy to process a level 1 enchanted item (in RF)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_1_ENERGY_COST_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_1_ENERGY_COST_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_2_ENERGY_COST = COMMON_BUILDER
                        .comment("How much energy to process a level 2 enchanted item (in RF)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_2_ENERGY_COST_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_2_ENERGY_COST_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_3_ENERGY_COST = COMMON_BUILDER
                        .comment("How much energy to process a level 3 enchanted item (in RF)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_3_ENERGY_COST_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_3_ENERGY_COST_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_4_ENERGY_COST = COMMON_BUILDER
                        .comment("How much energy to process a level 4 enchanted item (in RF)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_4_ENERGY_COST_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_4_ENERGY_COST_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_LVL_5_ENERGY_COST = COMMON_BUILDER
                        .comment("How much energy to process a level 5 enchanted item (in RF)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_LVL_5_ENERGY_COST_TAG,
                                Squeezer.ENCH_SQUEEZER_LVL_5_ENERGY_COST_DEF, 0, Integer.MAX_VALUE);
                ENCH_SQUEEZER_EXTRA_ENERGY_COST = COMMON_BUILDER
                        .comment("Amount of extra energy for each level above 5 (in RF)")
                        .defineInRange(ConfigPath.Squeezer.ENCH_SQUEEZER_EXTRA_ENERGY_COST_TAG,
                                Squeezer.ENCH_SQUEEZER_EXTRA_ENERGY_COST_DEF, 0, Integer.MAX_VALUE);
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

    public static int getEnchantEnergy(int level) {
        if (level <= 1) return ENCH_SQUEEZER_LVL_1_ENERGY_COST.get();
        if (level == 2) return ENCH_SQUEEZER_LVL_2_ENERGY_COST.get();
        if (level == 3) return ENCH_SQUEEZER_LVL_3_ENERGY_COST.get();
        if (level == 4) return ENCH_SQUEEZER_LVL_4_ENERGY_COST.get();
        if (level == 5) return ENCH_SQUEEZER_LVL_5_ENERGY_COST.get();

        level -= 5;
        return ENCH_SQUEEZER_LVL_5_ENERGY_COST.get() + (level * ENCH_SQUEEZER_EXTRA_ENERGY_COST.get());
    }
}
