package ipsis.woot.setup;

import ipsis.woot.modules.squeezer.DyeMakeup;

public final class ModDefaults {

    public static final class Squeezer {
        public static final int DYE_SQUEEZER_TANK_CAPACITY_DEF = 4000;
        public static final int DYE_SQUEEZER_INTERNAL_FLUID_MAX_DEF = DyeMakeup.LCM * 100;
        public static final int DYE_SQUEEZER_MAX_ENERGY_DEF = 10000;
        public static final int DYE_SQUEEZER_MAX_ENERGY_RX_DEF = 100;
        public static final int DYE_SQUEEZER_ENERGY_PER_TICK_DEF = 20;

        public static final int ENCH_SQUEEZER_TANK_CAPACITY_DEF = 5000;
        public static final int ENCH_SQUEEZER_MAX_ENERGY_DEF = 10000;
        public static final int ENCH_SQUEEZER_MAX_ENERGY_RX_DEF = 100;
        public static final int ENCH_SQUEEZER_ENERGY_PER_TICK_DEF = 20;
        public static final int ENCH_SQUEEZER_RECIPE_ENERGY_DEF = 1000;
        public static final int ENCH_SQUEEZER_LVL_1_ENCHANT_MB_DEF = 500;
        public static final int ENCH_SQUEEZER_LVL_2_ENCHANT_MB_DEF = 1000;
        public static final int ENCH_SQUEEZER_LVL_3_ENCHANT_MB_DEF = 1500;
        public static final int ENCH_SQUEEZER_LVL_4_ENCHANT_MB_DEF = 2000;
        public static final int ENCH_SQUEEZER_LVL_5_ENCHANT_MB_DEF = 2500;
        public static final int ENCH_SQUEEZER_EXTRA_ENCHANT_MB_DEF = 500;
    }

    public static final class Infuser {
        public static final int INFUSER_TANK_CAPACITY_DEF = 5000;
        public static final int INFUSER_MAX_ENERGY_DEF = 10000;
        public static final int INFUSER_MAX_ENERGY_RX_DEF = 100;
        public static final int INFUSER_ENERGY_PER_TICK_DEF = 20;
    }

    public static final class Generator {
        public static final int CONATUS_GEN_INPUT_TANK_CAPACITY_DEF = 10000;
        public static final int CONATUS_GEN_OUTPUT_TANK_CAPACITY_DEF = 10000;
        public static final int CONATUS_GEN_MAX_ENERGY_DEF = 10000;
        public static final int CONATUS_GEN_MAX_ENERGY_RX_DEF = 100;
        public static final int CONATUS_GEN_MAX_ENERGY_PER_TICK_DEF = 20;
        public static final int CONATUS_GEN_RECIPE_ENERGY_DEF = 1000;

    }

    public static final class Factory {
        public static final int MASS_COUNT_DEF = 1;
        public static final int SPAWN_TICKS_DEF = 16 * 20;
        public static final int UNITS_PER_HEALTH_DEF = 1;
        public static final int MOB_SHARD_KILLS_DEF = 5;
        public static final int TIER_1_MAX_UNITS_DEF = 20;
        public static final int TIER_2_MAX_UNITS_DEF = 40;
        public static final int TIER_3_MAX_UNITS_DEF = 60;
        public static final int TIER_4_MAX_UNITS_DEF = Integer.MAX_VALUE;
        public static final int TIER_5_MAX_UNITS_DEF = Integer.MAX_VALUE;
        public static final int TIER_1_UNITS_PER_TICK_DEF = 5;
        public static final int TIER_2_UNITS_PER_TICK_DEF = 10;
        public static final int TIER_3_UNITS_PER_TICK_DEF = 20;
        public static final int TIER_4_UNITS_PER_TICK_DEF = 30;
        public static final int TIER_5_UNITS_PER_TICK_DEF = 40;

        public static final int CELL_1_CAPACITY_DEF = 10000;
        public static final int CELL_2_CAPACITY_DEF = 100000;
        public static final int CELL_3_CAPACITY_DEF = Integer.MAX_VALUE;
        public static final int CELL_1_MAX_TRANSFER_DEF = 1000;
        public static final int CELL_2_MAX_TRANSFER_DEF = 10000;
        public static final int CELL_3_MAX_TRANSFER_DEF = 100000;

        public static final int EFFICIENCY_1_DEF = 15;
        public static final int EFFICIENCY_2_DEF = 25;
        public static final int EFFICIENCY_3_DEF = 30;
        public static final int MASS_COUNT_1_DEF = 2;
        public static final int MASS_COUNT_2_DEF = 4;
        public static final int MASS_COUNT_3_DEF = 6;
        public static final int RATE_1_DEF = 20;
        public static final int RATE_2_DEF = 50;
        public static final int RATE_3_DEF = 75;
    }
}
