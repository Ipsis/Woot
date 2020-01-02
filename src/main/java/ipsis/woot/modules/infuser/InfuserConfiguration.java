package ipsis.woot.modules.infuser;

import net.minecraftforge.common.ForgeConfigSpec;

public class InfuserConfiguration {

    public static final String CATEGORY_BUILDER = "infuser";

    public static ForgeConfigSpec.IntValue INFUSER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue INFUSER_MAX_ENERGY;
    public static ForgeConfigSpec.IntValue INFUSER_MAX_ENERGY_RX;
    public static ForgeConfigSpec.IntValue LVL_1_ENCHANT_COST;
    public static ForgeConfigSpec.IntValue LVL_2_ENCHANT_COST;
    public static ForgeConfigSpec.IntValue LVL_3_ENCHANT_COST;
    public static ForgeConfigSpec.IntValue LVL_4_ENCHANT_COST;
    public static ForgeConfigSpec.IntValue LVL_5_ENCHANT_COST;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the infuser").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the infuser").push(CATEGORY_BUILDER);
        {
            INFUSER_TANK_CAPACITY = COMMON_BUILDER
                    .comment("Maximum tank capacity (in mb)")
                    .defineInRange("tankMaxCapacity", 10000, 0, Integer.MAX_VALUE);
            INFUSER_MAX_ENERGY = COMMON_BUILDER
                    .comment("Maximum energy capacity (in RF)")
                    .defineInRange("energyMaxCapacity", 1000, 0, Integer.MAX_VALUE);
            INFUSER_MAX_ENERGY_RX = COMMON_BUILDER
                    .comment("Maximum energy that can be received (in RF/t)")
                    .defineInRange("energyMaxRx", 100, 0, Integer.MAX_VALUE);
            LVL_1_ENCHANT_COST = COMMON_BUILDER
                    .comment("Cost (in mb) to enchant an item to level 1")
                    .defineInRange("lvl1EnchantCost", 1000, 0, Integer.MAX_VALUE);
            LVL_2_ENCHANT_COST = COMMON_BUILDER
                    .comment("Cost (in mb) to enchant an item to level 2")
                    .defineInRange("lvl2EnchantCost", 2000, 0, Integer.MAX_VALUE);
            LVL_3_ENCHANT_COST = COMMON_BUILDER
                    .comment("Cost (in mb) to enchant an item to level 3")
                    .defineInRange("lvl3EnchantCost", 3000, 0, Integer.MAX_VALUE);
            LVL_4_ENCHANT_COST = COMMON_BUILDER
                    .comment("Cost (in mb) to enchant an item to level 4")
                    .defineInRange("lvl4EnchantCost", 4000, 0, Integer.MAX_VALUE);
            LVL_5_ENCHANT_COST = COMMON_BUILDER
                    .comment("Cost (in mb) to enchant an item to level 5")
                    .defineInRange("lvl5EnchantCost", 5000, 0, Integer.MAX_VALUE);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
