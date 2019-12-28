package ipsis.woot.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        new WootCommand(dispatcher);
    }
}
