package ipsis.woot.server.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ipsis.woot.Woot;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.loot.MobDrop;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

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
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .then(Commands.argument("entity", StringArgumentType.string())
                            .then(Commands.argument("looting", IntegerArgumentType.integer(0, 3))
                                .executes(ctx -> sendDrops(ctx.getSource(), StringArgumentType.getString(ctx, "entity"), IntegerArgumentType.getInteger(ctx, "looting")))));
        }
    }

    private static int sendDrops(CommandSource cs, String entityKey, int looting) throws CommandSyntaxException {
        FakeMobKey fakeMobKey = new FakeMobKey(new FakeMob(entityKey), looting);
        List<MobDrop> drops = DropRegistry.get().getMobDrops(fakeMobKey);
        cs.sendFeedback(new TranslationTextComponent("commands.woot.loot.drops.list", entityKey, looting), true);
        if (drops.isEmpty()) {
            cs.sendFeedback(new TranslationTextComponent("commands.woot.loot.drops.none"), true);
        } else {
            for (MobDrop drop : drops) {
                Woot.LOGGER.info("sendDrops: {}", drop);
                cs.sendFeedback(new TranslationTextComponent("commands.woot.loot.drops.info", drop.getDroppedItem().getTranslationKey(), drop.getDropChance()), true);
            }
        }

        return 0;
    }

    private static class CustomDropsCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("custom")
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .then(Commands.argument("entity", StringArgumentType.string())
                        .executes(ctx -> sendCustom(ctx.getSource(), StringArgumentType.getString(ctx, "entity"))));
        }
    }

    private static int sendCustom(CommandSource cs, String entityKey) throws CommandSyntaxException {
        cs.sendFeedback(new TranslationTextComponent("commands.woot.loot.custom.summary", 1), true);
        return 1;
    }

    private static class LearnedDropsCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("learned")
                    .requires(cs -> cs.hasPermissionLevel(0))
                    .then(Commands.argument("entity", StringArgumentType.string())
                            .executes(ctx -> sendLearned(ctx.getSource(), StringArgumentType.getString(ctx, "entity"))));
        }
    }

    private static int sendLearned(CommandSource cs, String entityKey) throws CommandSyntaxException {
        FakeMob fakeMob = new FakeMob(entityKey);
        List<Integer> status = DropRegistry.get().getLearningStatus(fakeMob);
        cs.sendFeedback(new TranslationTextComponent("commands.woot.loot.learned.summary",
                1000, status.get(0), status.get(1), status.get(2), status.get(3)), true);
        return 0;
    }
}
