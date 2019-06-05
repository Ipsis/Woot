package ipsis.woot.server.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandLoot {

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("loot")
                .then(DropsCommand.register())
                .then(CustomDropsCommand.register())
                .then(LearnedDropsCommand.register());
    }

    private static class DropsCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("drops")
                    .requires(cs -> cs.hasPermissionLevel(2))
                    .executes(ctx -> sendDrops(ctx.getSource()));
        }
    }

    private static int sendDrops(CommandSource cs) throws CommandSyntaxException {
        cs.sendFeedback(new TextComponentTranslation("commands.woot.loot.drops.summary", 1), true);
        return 1;
    }

    private static class CustomDropsCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("custom")
                    .requires(cs -> cs.hasPermissionLevel(2))
                    .executes(ctx -> sendCustom(ctx.getSource()));
        }
    }

    private static int sendCustom(CommandSource cs) throws CommandSyntaxException {
        cs.sendFeedback(new TextComponentTranslation("commands.woot.loot.custom.summary", 1), true);
        return 1;
    }

    private static class LearnedDropsCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("learned")
                    .requires(cs -> cs.hasPermissionLevel(2))
                    .executes(ctx -> sendLearned(ctx.getSource()));
        }
    }

    private static int sendLearned(CommandSource cs) throws CommandSyntaxException {
        cs.sendFeedback(new TextComponentTranslation("commands.woot.loot.learned.summary", 1), true);
        return 1;
    }
}
