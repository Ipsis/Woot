package ipsis.woot.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class SimulationCommand {

    private static final String TAG = "commands.woot.simulation.";

    static final SuggestionProvider<CommandSource> ENTITY_SUGGESTIONS = (ctx, builder) ->
            ISuggestionProvider.suggest(
                    ForgeRegistries.ENTITIES.getKeys().stream().map(ResourceLocation::toString).map(StringArgumentType::escapeIfRequired),
                    builder);

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("simulation")
                .then(LearnCommand.register())
                .then(DumpCommand.register())
                .then(FlushCommand.register())
                .then(StatusCommand.register())
                .then(RollDropsCommand.register());
    }

    private static class LearnCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("learn")
                    .requires(cs -> cs.hasPermission(CommandConfiguration.COMMAND_LEVEL_SIM_LEARN.get()))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.id()).suggests(ENTITY_SUGGESTIONS)
                                .executes(ctx -> learnEntity(
                                                ctx.getSource(),
                                                ResourceLocationArgument.getId(ctx, "entity"), ""))
                            .then(
                                    Commands.argument("tag", StringArgumentType.string())
                                        .executes(ctx -> learnEntity(
                                                ctx.getSource(),
                                                ResourceLocationArgument.getId(ctx, "entity"),
                                                StringArgumentType.getString(ctx, "tag")))
                            )

                    );
        }
    }

    private static class RollDropsCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("roll")
                    .requires(cs -> cs.hasPermission(CommandConfiguration.COMMAND_LEVEL_SIM_ROLL_DROPS.get()))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.id()).suggests(ENTITY_SUGGESTIONS)
                                    .then (
                                            Commands.argument("looting", IntegerArgumentType.integer(0, 3))
                                                .executes(ctx -> rollDrops(
                                                        ctx.getSource(),
                                                        ResourceLocationArgument.getId(ctx, "entity"), "",
                                                        IntegerArgumentType.getInteger(ctx, "looting"))))
                                    )
                                    .then(
                                            Commands.argument("tag", StringArgumentType.string())
                                                    .then (
                                                            Commands.argument("looting", IntegerArgumentType.integer(0, 3))
                                                                    .executes(ctx -> rollDrops(
                                                                            ctx.getSource(),
                                                                            ResourceLocationArgument.getId(ctx, "entity"), "",
                                                                            IntegerArgumentType.getInteger(ctx, "looting"))
                                                                    )
                                    )

                    );
        }
    }

    private static class DumpCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("dump")
                    .requires(cs -> cs.hasPermission(CommandConfiguration.COMMAND_LEVEL_SIM_DUMP.get()))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.id()).suggests(ENTITY_SUGGESTIONS)
                                    .executes(ctx -> dumpEntity(
                                            ctx.getSource(),
                                            ResourceLocationArgument.getId(ctx, "entity"), ""))
                            .then(
                                    Commands.argument("tag", StringArgumentType.string())
                                            .executes(ctx -> dumpEntity(
                                                    ctx.getSource(),
                                                    ResourceLocationArgument.getId(ctx, "entity"),
                                                    StringArgumentType.getString(ctx, "tag")))
                            )

                    );
        }
    }

    private static class FlushCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("flush")
                    .requires(cs -> cs.hasPermission(CommandConfiguration.COMMAND_LEVEL_SIM_FLUSH.get()))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.id()).suggests(ENTITY_SUGGESTIONS)
                                    .executes(ctx -> flushEntity(
                                            ctx.getSource(),
                                            ResourceLocationArgument.getId(ctx, "entity"), ""))
                                    .then(
                                            Commands.argument("tag", StringArgumentType.string())
                                                    .executes(ctx -> flushEntity(
                                                            ctx.getSource(),
                                                            ResourceLocationArgument.getId(ctx, "entity"),
                                                            StringArgumentType.getString(ctx, "tag")))
                                    )

                    );
        }
    }

    private static int rollDrops(CommandSource source, ResourceLocation resourceLocation, String tag, int looting) throws CommandSyntaxException {

        FakeMob fakeMob;
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

        if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getLevel())) {
            List<ItemStack> drops = MobSimulator.getInstance().getRolledDrops(new FakeMobKey(fakeMob, looting));
            source.sendSuccess(new TranslationTextComponent(TAG + "roll",
                    fakeMob, looting,
                    drops.stream().map(ItemStack::toString).collect(Collectors.joining(","))), true);
        }

        return 0;
    }

    private static int learnEntity(CommandSource source, ResourceLocation resourceLocation, String tag) throws CommandSyntaxException {

        FakeMob fakeMob;
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

        if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getLevel())) {
            boolean result = ipsis.woot.simulator.MobSimulator.getInstance().learn(fakeMob);
            if (result)
                source.sendSuccess(new TranslationTextComponent(TAG + "learn.ok", resourceLocation.toString()), true);
            else
                source.sendFailure(new TranslationTextComponent(TAG + "learn.fail", resourceLocation.toString()));
        } else {
            source.sendFailure(new TranslationTextComponent(TAG + "learn.fail", resourceLocation.toString()));
        }

        return 0;
    }

    private static int dumpEntity(CommandSource source, ResourceLocation resourceLocation, String tag) throws CommandSyntaxException {

        FakeMob fakeMob;
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

        if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getLevel())) {
            for (SimulatedMobDropSummary summary : ipsis.woot.simulator.MobSimulator.getInstance().getDropSummary(fakeMob))
                source.sendSuccess(new TranslationTextComponent(TAG + "dump.drop", summary), true);
        }

        return 0;
    }

    private static class StatusCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("status")
                    .requires(cs -> cs.hasPermission(CommandConfiguration.COMMAND_LEVEL_SIM_STATUS.get()))
                    .executes(ctx -> status(ctx.getSource()));
        }
    }

     private static int status (CommandSource source) throws CommandSyntaxException {
        source.sendSuccess(new TranslationTextComponent(TAG + "status.simulating",
                ipsis.woot.simulator.MobSimulator.getInstance().getSimulations().stream().map(
                        FakeMobKey::toString).collect(Collectors.joining(","))), true);
         source.sendSuccess(new TranslationTextComponent(TAG + "status.waiting",
                 ipsis.woot.simulator.MobSimulator.getInstance().getWaiting().stream().map(
                         FakeMobKey::toString).collect(Collectors.joining(","))), true);

         return 0;
     }

     private static int flushEntity(CommandSource source, ResourceLocation resourceLocation, String tag) throws CommandSyntaxException {
         FakeMob fakeMob;
         if (tag.equalsIgnoreCase(""))
             fakeMob = new FakeMob(resourceLocation.toString());
         else
             fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

         if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getLevel())) {
             MobSimulator.getInstance().flush(fakeMob);
             MobSimulator.getInstance().learn(fakeMob);
         }
         return 0;
     }
}
