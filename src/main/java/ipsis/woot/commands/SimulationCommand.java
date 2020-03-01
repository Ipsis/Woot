package ipsis.woot.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import ipsis.woot.modules.simulation.MobSimulator;
import ipsis.woot.modules.simulation.library.DropSummary;
import ipsis.woot.modules.simulation.library.DropLibrary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class SimulationCommand {

    static final SuggestionProvider<CommandSource> ENTITY_SUGGESTIONS = (ctx, builder) ->
            ISuggestionProvider.func_212476_a(
                    ForgeRegistries.ENTITIES.getKeys().stream(),
                    builder);

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("simulation")
                .then(LearnCommand.register())
                .then(DumpCommand.register())
                .then(StatusCommand.register());
    }

    private static class LearnCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("learn")
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.resourceLocation()).suggests(ENTITY_SUGGESTIONS)
                            .then(
                                    Commands.argument("looting", IntegerArgumentType.integer(0, 3))
                                        .executes(ctx -> learnEntity(
                                                ctx.getSource(),
                                                ResourceLocationArgument.getResourceLocation(ctx, "entity"),
                                                IntegerArgumentType.getInteger(ctx, "looting"), ""))
                                        .then(
                                            Commands.argument("tag", StringArgumentType.string())
                                                .executes(ctx -> learnEntity(
                                                    ctx.getSource(),
                                                    ResourceLocationArgument.getResourceLocation(ctx, "entity"),
                                                    IntegerArgumentType.getInteger(ctx, "looting"),
                                                    StringArgumentType.getString(ctx, "tag")))
                                        )
                            )

                    );
        }
    }

    private static class DumpCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("dump")
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.resourceLocation()).suggests(ENTITY_SUGGESTIONS)
                                    .then(
                                            Commands.argument("looting", IntegerArgumentType.integer(0, 3))
                                                    .executes(ctx -> dumpEntity(
                                                            ctx.getSource(),
                                                            ResourceLocationArgument.getResourceLocation(ctx, "entity"),
                                                            IntegerArgumentType.getInteger(ctx, "looting"), ""))
                                                    .then(
                                                            Commands.argument("tag", StringArgumentType.string())
                                                                    .executes(ctx -> dumpEntity(
                                                                            ctx.getSource(),
                                                                            ResourceLocationArgument.getResourceLocation(ctx, "entity"),
                                                                            IntegerArgumentType.getInteger(ctx, "looting"),
                                                                            StringArgumentType.getString(ctx, "tag")))
                                                    )
                                    )

                    );
        }
    }

    private static int learnEntity(CommandSource source, ResourceLocation resourceLocation, int looting, String tag) throws CommandSyntaxException {

        FakeMob fakeMob;
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

        if (fakeMob.isValid()) {
            boolean result = MobSimulator.get().learn(new FakeMobKey(fakeMob, looting));
            if (result)
                source.sendFeedback(new TranslationTextComponent("commands.woot.learn.ok", resourceLocation.toString(), looting), true);
            else
                source.sendFeedback(new TranslationTextComponent("commands.woot.learn.fail", resourceLocation.toString(), looting), true);
        }

        return 0;
    }

    private static int dumpEntity(CommandSource source, ResourceLocation resourceLocation, int looting, String tag) throws CommandSyntaxException {

        FakeMob fakeMob;
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

        if (fakeMob.isValid()) {
            for (DropSummary dropSummary : DropLibrary.getInstance().getDropSummary(new FakeMobKey(fakeMob, looting)))
                source.sendFeedback(new TranslationTextComponent("commands.woot.simulation.dump.drop", dropSummary), true);
        }

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
