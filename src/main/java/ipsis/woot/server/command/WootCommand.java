package ipsis.woot.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

/**
 * The main command for the mod.
 * All other commands are sub-commands of this.
 */
public class WootCommand {

    public WootCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("woot")
                .then(CommandLoot.register())
                .then(CommandLearn.register())
        );
    }
}
