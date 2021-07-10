package ipsis.woot.config;

import com.google.common.collect.Lists;
import ipsis.woot.modules.squeezer.DyeMakeup;

import java.util.ArrayList;

public final class ConfigDefaults {

    public static final class Anvil {
        public static final boolean PARTICLES_DEF = true;
    }

    public static final class Squeezer {
        public static final int DYE_SQUEEZER_TANK_CAPACITY_DEF = 4000;
        public static final int DYE_SQUEEZER_INTERNAL_FLUID_MAX_DEF = DyeMakeup.LCM * 100;
        public static final int DYE_SQUEEZER_MAX_ENERGY_DEF = 10000;
        public static final int DYE_SQUEEZER_MAX_ENERGY_RX_DEF = 100;
        public static final int DYE_SQUEEZER_ENERGY_PER_TICK_DEF = 40;

        public static final int ENCH_SQUEEZER_TANK_CAPACITY_DEF = 10000;
        public static final int ENCH_SQUEEZER_MAX_ENERGY_DEF = 10000;
        public static final int ENCH_SQUEEZER_MAX_ENERGY_RX_DEF = 100;
        public static final int ENCH_SQUEEZER_ENERGY_PER_TICK_DEF = 40;
        public static final int ENCH_SQUEEZER_LVL_1_ENCHANT_MB_DEF = 500;
        public static final int ENCH_SQUEEZER_LVL_2_ENCHANT_MB_DEF = 1000;
        public static final int ENCH_SQUEEZER_LVL_3_ENCHANT_MB_DEF = 1500;
        public static final int ENCH_SQUEEZER_LVL_4_ENCHANT_MB_DEF = 2000;
        public static final int ENCH_SQUEEZER_LVL_5_ENCHANT_MB_DEF = 2500;
        public static final int ENCH_SQUEEZER_EXTRA_ENCHANT_MB_DEF = 500;

        public static final int ENCH_SQUEEZER_LVL_1_ENERGY_COST_DEF = 1000;
        public static final int ENCH_SQUEEZER_LVL_2_ENERGY_COST_DEF = 2000;
        public static final int ENCH_SQUEEZER_LVL_3_ENERGY_COST_DEF = 3000;
        public static final int ENCH_SQUEEZER_LVL_4_ENERGY_COST_DEF = 4000;
        public static final int ENCH_SQUEEZER_LVL_5_ENERGY_COST_DEF = 5000;
        public static final int ENCH_SQUEEZER_EXTRA_ENERGY_COST_DEF = 1000;
    }

    public static final class Infuser {
        public static final int TANK_CAPACITY_DEF = 5000;
        public static final int MAX_ENERGY_DEF = 10000;
        public static final int MAX_ENERGY_RX_DEF = 100;
        public static final int ENERGY_PER_TICK_DEF = 40;
    }

    public static final class Layout {
        public static final boolean SIMPLE_DEF = false;
        public static final double OPACITY_DEF = 0.95D;
        public static final double SIZE_DEF = 0.35D;
    }

    public static final class FluidConvertor {
        public static final int INPUT_TANK_CAPACITY_DEF = 10000;
        public static final int OUTPUT_TANK_CAPACITY_DEF = 10000;
        public static final int MAX_ENERGY_DEF = 10000;
        public static final int MAX_ENERGY_RX_DEF = 100;
        public static final int ENERGY_PER_TICK_DEF = 40;
    }

    public static final class Simulation {
        public static final int TICKS_DEF = 40;
        public static final int MOB_SAMPLE_SIZE_DEF = 1000;
        public static final int TICKS_PER_SIM_TICK_DEF = 10;
        public static final int CELLS_PER_SIM_TICK_DEF = 32;
    }

    public static final class Factory {
        public static final boolean TICK_ACCELERATION_DEF = true;
        public static final int MASS_COUNT_DEF = 1;
        public static final int SPAWN_TICKS_DEF = 16 * 20;
        public static final int UNITS_PER_HEALTH_DEF = 10;
        public static final int MOB_SHARD_KILLS_DEF = 5;
        public static final int TIER_1_MAX_UNITS_DEF = 20;
        public static final int TIER_2_MAX_UNITS_DEF = 40;
        public static final int TIER_3_MAX_UNITS_DEF = 60;
        public static final int TIER_4_MAX_UNITS_DEF = Integer.MAX_VALUE;
        public static final int TIER_5_MAX_UNITS_DEF = Integer.MAX_VALUE;
        public static final double EXOTIC_DROP_DEF = 15.0F;

        private static final int BUCKET_CAPACITY = 1000;
        public static final int CELL_1_CAPACITY_DEF = BUCKET_CAPACITY * 10;
        public static final int CELL_2_CAPACITY_DEF = BUCKET_CAPACITY * 50;
        public static final int CELL_3_CAPACITY_DEF = BUCKET_CAPACITY * 100;
        public static final int CELL_4_CAPACITY_DEF = BUCKET_CAPACITY * 1000;
        public static final int CELL_1_MAX_TRANSFER_DEF = 1000;
        public static final int CELL_2_MAX_TRANSFER_DEF = 5000;
        public static final int CELL_3_MAX_TRANSFER_DEF = 10000;
        public static final int CELL_4_MAX_TRANSFER_DEF = 25000;

        public static final int EFFICIENCY_1_DEF = 15;
        public static final int EFFICIENCY_2_DEF = 25;
        public static final int EFFICIENCY_3_DEF = 30;
        public static final int MASS_COUNT_1_DEF = 2;
        public static final int MASS_COUNT_2_DEF = 4;
        public static final int MASS_COUNT_3_DEF = 6;
        public static final int RATE_1_DEF = 20;
        public static final int RATE_2_DEF = 50;
        public static final int RATE_3_DEF = 75;
        public static final int XP_1_DEF = 100;
        public static final int XP_2_DEF = 125;
        public static final int XP_3_DEF = 150;
        public static final int TIER_SHARD_1_DEF = 1;
        public static final int TIER_SHARD_2_DEF = 2;
        public static final int TIER_SHARD_3_DEF = 3;
        public static final int HEADLESS_1_DEF = 25;
        public static final int HEADLESS_2_DEF = 50;
        public static final int HEADLESS_3_DEF = 80;
        public static final int SLAUGHTER_1_DEF = 100;
        public static final int SLAUGHTER_2_DEF = 120;
        public static final int SLAUGHTER_3_DEF = 140;
        public static final int CRUSHER_1_DEF = 100;
        public static final int CRUSHER_2_DEF = 120;
        public static final int CRUSHER_3_DEF = 140;
        public static final int LASER_1_DEF = 100;
        public static final int LASER_2_DEF = 120;
        public static final int LASER_3_DEF = 140;
        public static final int FLAYED_1_DEF = 90;
        public static final int FLAYED_2_DEF = 100;
        public static final int FLAYED_3_DEF = 110;

        public static final float EXOTIC_A_DEF = 85.0F;
        public static final float EXOTIC_B_DEF = 85.0F;
        public static final float EXOTIC_C_DEF = 85.0F;
        public static final int EXOTIC_D_DEF = 20;
        public static final int EXOTIC_E_DEF = 10;

        public static final double T1_SHARD_DROP_CHANCE_DEF = 5.0F;
        public static final double T2_SHARD_DROP_CHANCE_DEF = 10.0F;
        public static final double T3_SHARD_DROP_CHANCE_DEF = 20.0F;
        public static final double T4_SHARD_DROP_CHANCE_DEF = 30.0F;
        public static final double T5_SHARD_DROP_CHANCE_DEF = 40.0F;
        public static final ArrayList<Integer> T1_SHARD_DROP_WEIGHTS_DEF = Lists.newArrayList( 80, 20, 1);
        public static final ArrayList<Integer> T2_SHARD_DROP_WEIGHTS_DEF = Lists.newArrayList( 70, 25, 4);
        public static final ArrayList<Integer> T3_SHARD_DROP_WEIGHTS_DEF = Lists.newArrayList( 60, 30, 8);
        public static final ArrayList<Integer> T4_SHARD_DROP_WEIGHTS_DEF = Lists.newArrayList( 50, 40, 12);
        public static final ArrayList<Integer> T5_SHARD_DROP_WEIGHTS_DEF = Lists.newArrayList( 40, 50, 16);
    }
}
