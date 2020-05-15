package ipsis.woot.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ipsis.woot.config.Config;
import ipsis.woot.util.FakeMob;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class ConfigCommand {

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("config")
                .requires(cs -> cs.hasPermissionLevel(CommandHelper.CONFIG_COMMAND_LEVEL))
                .then(Commands.argument("entity", StringArgumentType.string())
                        .then(Commands.argument("key", StringArgumentType.string())
                                .executes(ctx -> showConfig(ctx.getSource(), StringArgumentType.getString(ctx, "entity"), StringArgumentType.getString(ctx, "key")))));
    }

    private static int showConfig(CommandSource source, String entityKey, String key) throws CommandSyntaxException {
        FakeMob fakeMob = new FakeMob(entityKey);
        source.sendFeedback(new StringTextComponent(Config.OVERRIDE.getConfigAsString(fakeMob, key)), true);
        return 0;
    }
}
