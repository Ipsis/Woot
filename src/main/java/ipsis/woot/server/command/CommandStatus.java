package ipsis.woot.server.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentString;

public class CommandStatus {

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("status")
                .requires(cs -> cs.hasPermissionLevel(0))
                .then(Commands.literal("tartarus")
                        .executes(ctx -> sendTartarus(ctx.getSource())))
                .then(Commands.literal("school")
                        .executes(ctx -> sendSchool(ctx.getSource())))
                .then(Commands.literal("droprepo")
                        .executes(ctx -> sendDrops(ctx.getSource()))
        );
    }

    private static int sendTartarus(CommandSource cs) throws CommandSyntaxException {
        cs.sendFeedback(new TextComponentString("show some tartarus info"), true);
        return 1;
    }

    private static int sendSchool(CommandSource cs) throws CommandSyntaxException {
        cs.sendFeedback(new TextComponentString("show some loot school info"), true);
        return 1;
    }

    private static int sendDrops(CommandSource cs) throws CommandSyntaxException {
        cs.sendFeedback(new TextComponentString("show some drop repo info"), true);
        return 1;
    }
}
