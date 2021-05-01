package ipsis.woot.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class GiveCommand {

    static final SuggestionProvider<CommandSource> ENTITY_SUGGESTIONS = (ctx, builder) ->
            ISuggestionProvider.suggest(
                    ForgeRegistries.ENTITIES.getKeys().stream().map(ResourceLocation::toString).map(StringArgumentType::escapeIfRequired),
                    builder);

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("give")
                .requires(cs -> cs.hasPermission(CommandConfiguration.COMMAND_LEVEL_GIVE.get()))
                .then(
                        Commands.argument("target", EntityArgument.player())
                        .then(
                                Commands.argument("entity", ResourceLocationArgument.id()).suggests(ENTITY_SUGGESTIONS)
                                    .executes(ctx -> giveItem(
                                        ctx.getSource(),
                                        EntityArgument.getPlayer(ctx, "target"),
                                        ResourceLocationArgument.getId(ctx, "entity"), ""))
                                .then(
                                        Commands.argument("tag", StringArgumentType.string())
                                                .executes(ctx -> giveItem(
                                                        ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "target"),
                                                        ResourceLocationArgument.getId(ctx, "entity"),
                                                        StringArgumentType.getString(ctx, "tag")))
                                )
                        )
                );
    }

    private static int giveItem(CommandSource source, ServerPlayerEntity target, ResourceLocation resourceLocation, String tag) {

        FakeMob fakeMob = new FakeMob();
        if (tag.equalsIgnoreCase(""))
            fakeMob = new FakeMob(resourceLocation.toString());
        else
            fakeMob = new FakeMob(resourceLocation.toString() + "," + tag);
        if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getLevel())) {
            ItemStack itemStack = ControllerTileEntity.getItemStack(fakeMob);

            /**
             * Straight from vanilla GiveCommand
             */
            boolean added = target.inventory.add(itemStack);
            if (added && itemStack.isEmpty()) {
                itemStack.setCount(1);
                ItemEntity itemEntity = target.drop(itemStack, false);
                if (itemEntity != null)
                    itemEntity.makeFakeItem();

                target.level.playSound(null,
                        target.getX(), target.getY(), target.getZ(),
                        SoundEvents.ITEM_PICKUP,
                        SoundCategory.PLAYERS,
                        0.2F,
                        ((target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                target.inventoryMenu.broadcastChanges();
            } else {
                ItemEntity itemEntity = target.drop(itemStack, false);
                if (itemEntity != null) {
                    itemEntity.setNoPickUpDelay();
                    itemEntity.setOwner(target.getUUID());
                }
            }
            source.sendSuccess(new TranslationTextComponent("commands.woot.give.ok",
                    target.getDisplayName(), resourceLocation.toString()), true);
        } else {
            source.sendFailure(new TranslationTextComponent("commands.woot.give.fail",
                    resourceLocation.toString()));
        }

        return 1;
    }
}
