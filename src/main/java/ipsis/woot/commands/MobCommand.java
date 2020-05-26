package ipsis.woot.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import ipsis.woot.config.Config;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class MobCommand {

    private static final String TAG = "commands.woot.mob.";

    static final SuggestionProvider<CommandSource> ENTITY_SUGGESTIONS = (ctx, builder) ->
            ISuggestionProvider.func_212476_a(
                    ForgeRegistries.ENTITIES.getKeys().stream(),
                    builder);

    static ArgumentBuilder<CommandSource, ?> register()  {
        return Commands.literal("mob")
                .then(InfoCommand.register());
    }

    private static class InfoCommand {
        static ArgumentBuilder<CommandSource, ?> register() {
            return Commands.literal("info")
                    .requires(cs -> cs.hasPermissionLevel(CommandHelper.MOB_INFO_COMMAND_LEVEL))
                    .then(
                            Commands.argument("entity", ResourceLocationArgument.resourceLocation()).suggests(ENTITY_SUGGESTIONS)
                                    .executes(ctx -> mobInfo(
                                            ctx.getSource(),
                                            ResourceLocationArgument.getResourceLocation(ctx, "entity"), ""))
                                    .then(
                                            Commands.argument("tag", StringArgumentType.string())
                                                    .executes(ctx -> mobInfo(
                                                            ctx.getSource(),
                                                            ResourceLocationArgument.getResourceLocation(ctx, "entity"),
                                                            StringArgumentType.getString(ctx, "tag")))
                                    )
                    );
        }
    }

    private static int mobInfo(CommandSource source, ResourceLocation resourceLocation, String tag) throws CommandSyntaxException {
        FakeMob fakeMob;
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);

        if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getWorld())) {
            int health = SpawnController.get().getMobHealth(fakeMob, source.getWorld());
            int xp = SpawnController.get().getMobExperience(fakeMob, source.getWorld());
            Tier mobTier = Config.OVERRIDE.getMobTier(fakeMob, source.getWorld());

            source.sendFeedback(new TranslationTextComponent(TAG + "info.summary", fakeMob, health, xp, mobTier), true);
        }

        return 0;
    }

}
