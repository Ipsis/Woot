package ipsis.woot.server.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ipsis.woot.simulation.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.stream.Collectors;

public class SimulationCommand {

    // TODO use entitysummonargument style instead of string
    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("simulation")
                .then(LearnCommand.register())
                .then(StatusCommand.register());
    }

    private static class LearnCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("learn")
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .then(Commands.argument("entity", StringArgumentType.string())
                            .then(Commands.argument("looting", IntegerArgumentType.integer(0, 3))
                                    .executes(ctx -> learnEntity(ctx.getSource(), StringArgumentType.getString(ctx, "entity"), IntegerArgumentType.getInteger(ctx, "looting")))));
        }
    }

    private static int learnEntity(CommandSource source, String entityKey, int looting) throws CommandSyntaxException {
        boolean result = MobSimulator.get().learn(new FakeMobKey(new FakeMob(entityKey), looting));
        if (result)
            source.sendFeedback(new TranslationTextComponent("commands.woot.learn.ok", entityKey, looting), true);
        else
            source.sendFeedback(new TranslationTextComponent("commands.woot.learn.fail", entityKey, looting), true);
        return 0;
    }

    private static class StatusCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("status")
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .executes(ctx -> status(ctx.getSource()));
        }
    }

     private static int status (CommandSource source) throws CommandSyntaxException {
        source.sendFeedback(new TranslationTextComponent("commands.woot.pupils",
                MobSimulator.get().getPupils().stream().map(FakeMobKey::toString).collect(Collectors.joining(","))), true);

         return 0;
     }
}
