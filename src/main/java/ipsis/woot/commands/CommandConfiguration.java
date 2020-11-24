package ipsis.woot.commands;

import ipsis.woot.config.ConfigPath;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommandConfiguration {

    public static ForgeConfigSpec.IntValue COMMAND_LEVEL_SIM_LEARN;
    public static ForgeConfigSpec.IntValue COMMAND_LEVEL_SIM_DUMP;
    public static ForgeConfigSpec.IntValue COMMAND_LEVEL_SIM_FLUSH;
    public static ForgeConfigSpec.IntValue COMMAND_LEVEL_SIM_STATUS;
    public static ForgeConfigSpec.IntValue COMMAND_LEVEL_SIM_ROLL_DROPS;
    public static ForgeConfigSpec.IntValue COMMAND_LEVEL_GIVE;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for commands").push(ConfigPath.Commands.CATEGORY);
        CLIENT_BUILDER.comment("Settings for commands").push(ConfigPath.Commands.CATEGORY);
        {
            COMMAND_LEVEL_SIM_LEARN = COMMON_BUILDER
                    .comment("Manual learning of a mob command level")
                    .defineInRange(ConfigPath.Commands.COMMAND_LEVEL_SIM_LEARN,
                            CommandHelper.SIMULATION_LEARN_COMMAND_LEVEL, 0, 10);
            COMMAND_LEVEL_SIM_DUMP = COMMON_BUILDER
                    .comment("Display loot table of a mob command level")
                    .defineInRange(ConfigPath.Commands.COMMAND_LEVEL_SIM_DUMP,
                            CommandHelper.SIMULATION_DUMP_COMMAND_LEVEL, 0, 10);
            COMMAND_LEVEL_SIM_FLUSH = COMMON_BUILDER
                    .comment("Flush loot table of a mob command level")
                    .defineInRange(ConfigPath.Commands.COMMAND_LEVEL_SIM_FLUSH,
                            CommandHelper.SIMULATION_FLUSH_COMMAND_LEVEL, 0, 10);
            COMMAND_LEVEL_SIM_STATUS = COMMON_BUILDER
                    .comment("Show status of mob simulation command level")
                    .defineInRange(ConfigPath.Commands.COMMAND_LEVEL_SIM_STATUS,
                            CommandHelper.SIMULATION_STATUS_COMMAND_LEVEL, 0, 10);
            COMMAND_LEVEL_SIM_ROLL_DROPS = COMMON_BUILDER
                    .comment("Test drop of a mob command level")
                    .defineInRange(ConfigPath.Commands.COMMAND_LEVEL_SIM_ROLL_DROPS,
                            CommandHelper.SIMULATION_ROLL_DROPS_COMMAND_LEVEL, 0, 10);
            COMMAND_LEVEL_GIVE = COMMON_BUILDER
                    .comment("Give mob controller command level")
                    .defineInRange(ConfigPath.Commands.COMMAND_LEVEL_GIVE,
                            CommandHelper.GIVE_COMMAND_LEVEL, 0, 10);
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }
}
