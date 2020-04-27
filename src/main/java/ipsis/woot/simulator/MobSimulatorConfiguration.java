package ipsis.woot.simulator;

import ipsis.woot.config.ConfigDefaults;
import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class MobSimulatorConfiguration {

    public static ForgeConfigSpec.IntValue SIMULATION_TICKS;
    public static ForgeConfigSpec.IntValue SIMULATION_MOB_COUNT;
    public static ForgeConfigSpec.IntValue SIMULATION_TICKS_PER_SIM_TICK;
    public static ForgeConfigSpec.IntValue SIMULATION_CELLS_PER_SIM_TICK;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the loot simulation").push(ConfigPath.Simulation.CATEGORY);
        CLIENT_BUILDER.comment("Settings for the loot simulation").push(ConfigPath.Simulation.CATEGORY);
        {
            SIMULATION_TICKS = COMMON_BUILDER
                    .comment("Number of ticks between mob simulations")
                    .defineInRange(ConfigPath.Simulation.TICKS_TAG,
                            ConfigDefaults.Simulation.TICKS_DEF,
                            20, 20 * 60);

            SIMULATION_MOB_COUNT = COMMON_BUILDER
                    .comment("Number of simulated mobs to learn from")
                    .defineInRange(ConfigPath.Simulation.MOB_SAMPLE_SIZE_TAG,
                            ConfigDefaults.Simulation.MOB_SAMPLE_SIZE_DEF, 100, 5000);

            SIMULATION_TICKS_PER_SIM_TICK = COMMON_BUILDER
                    .comment("Number of ticks per tick of the simulator")
                    .defineInRange(ConfigPath.Simulation.TICKS_PER_SIM_TICK_TAG,
                            ConfigDefaults.Simulation.TICKS_PER_SIM_TICK_DEF, 1, 20 * 60);

            SIMULATION_CELLS_PER_SIM_TICK = COMMON_BUILDER
                    .comment("Number of mobs to simulate per simulator tick")
                    .defineInRange(ConfigPath.Simulation.CELLS_PER_SIM_TICK_TAG,
                            ConfigDefaults.Simulation.CELLS_PER_SIM_TICK_DEF, 1, 128);
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
