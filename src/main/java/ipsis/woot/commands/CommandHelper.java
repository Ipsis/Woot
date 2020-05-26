package ipsis.woot.commands;

public class CommandHelper {

    public static final int CONFIG_COMMAND_LEVEL = 0; // Read only info

    public static final int SIMULATION_LEARN_COMMAND_LEVEL = 2; // Starts learning of a mob
    public static final int SIMULATION_DUMP_COMMAND_LEVEL = 0; // Read only info
    public static final int SIMULATION_FLUSH_COMMAND_LEVEL = 2; // Restarts learning of a mob
    public static final int SIMULATION_STATUS_COMMAND_LEVEL = 0; // Read only info
    public static final int SIMULATION_ROLL_DROPS_COMMAND_LEVEL = 0; // Read only info

    public static final int MOB_INFO_COMMAND_LEVEL = 0; // Read only info

    public static final int GIVE_COMMAND_LEVEL = 2; // Same as vanilla give
}
