package ipsis.woot.simulator;

import net.minecraftforge.common.ForgeConfigSpec;

public class MobSimulatorConfiguration {

    public static final String CATEGORY_SIMULATION = "simulation";

    public static ForgeConfigSpec.IntValue SIMULATION_TICKS;
    public static ForgeConfigSpec.IntValue SIMULATION_MOB_COUNT;
    public static ForgeConfigSpec.IntValue SIMULATION_TICKS_PER_SIM_TICK;
    public static ForgeConfigSpec.IntValue SIMULATION_CELLS_PER_SIM_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the loot simulation").push(CATEGORY_SIMULATION);
        CLIENT_BUILDER.comment("Settings for the loot simulation").push(CATEGORY_SIMULATION);
        {
            SIMULATION_TICKS = COMMON_BUILDER
                    .comment("Number of ticks between mob simulations")
                    .defineInRange("simulationTicks", 40, 20, 20 * 60);

            SIMULATION_MOB_COUNT = COMMON_BUILDER
                    .comment("Number of simulated mobs to learn from")
                    .defineInRange("simulationMobCount", 500, 100, 5000);

            SIMULATION_TICKS_PER_SIM_TICK = COMMON_BUILDER
                    .comment("Number of ticks per tick of the simulator")
                    .defineInRange("simulationTicksPerSimTick", 10, 1, 20 * 60);

            SIMULATION_CELLS_PER_SIM_TICK = COMMON_BUILDER
                    .comment("Number of mobs to simulate per simulator tick")
                    .defineInRange("simulationCellsPerSimTick", 8, 1, 128);

        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
