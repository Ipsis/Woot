package ipsis.woot.server.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandLearn {

    // TODO use entitysummonargument style instead of string
    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("learn")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("entity", StringArgumentType.string())
                .executes(ctx -> learnEntity(ctx.getSource(), StringArgumentType.getString(ctx, "entity"))));
    }

    private static int learnEntity(CommandSource source, String entityName) throws CommandSyntaxException {

        source.sendFeedback(new TranslationTextComponent(entityName), false);
        return 1;
    }
}
