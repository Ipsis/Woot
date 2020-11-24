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
            ISuggestionProvider.func_212476_a(
                    ForgeRegistries.ENTITIES.getKeys().stream(),
                    builder);

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("give")
                .requires(cs -> cs.hasPermissionLevel(CommandConfiguration.COMMAND_LEVEL_GIVE.get()))
                .then(
                        Commands.argument("target", EntityArgument.player())
                        .then(
                                Commands.argument("entity", ResourceLocationArgument.resourceLocation()).suggests(ENTITY_SUGGESTIONS)
                                    .executes(ctx -> giveItem(
                                        ctx.getSource(),
                                        EntityArgument.getPlayer(ctx, "target"),
                                        ResourceLocationArgument.getResourceLocation(ctx, "entity"), ""))
                                .then(
                                        Commands.argument("tag", StringArgumentType.string())
                                                .executes(ctx -> giveItem(
                                                        ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "target"),
                                                        ResourceLocationArgument.getResourceLocation(ctx, "entity"),
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
        if (fakeMob.isValid() && SpawnController.get().isLivingEntity(fakeMob, source.getWorld())) {
            ItemStack itemStack = ControllerTileEntity.getItemStack(fakeMob);

            /**
             * Straight from vanilla GiveCommand
             */
            boolean added = target.inventory.addItemStackToInventory(itemStack);
            if (added && itemStack.isEmpty()) {
                itemStack.setCount(1);
                ItemEntity itemEntity = target.dropItem(itemStack, false);
                if (itemEntity != null)
                    itemEntity.makeFakeItem();

                target.world.playSound(null,
                        target.getPosX(), target.getPosY(), target.getPosZ(),
                        SoundEvents.ENTITY_ITEM_PICKUP,
                        SoundCategory.PLAYERS,
                        0.2F,
                        ((target.getRNG().nextFloat() - target.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                target.container.detectAndSendChanges();
            } else {
                ItemEntity itemEntity = target.dropItem(itemStack, false);
                if (itemEntity != null) {
                    itemEntity.setNoPickupDelay();
                    itemEntity.setOwnerId(target.getUniqueID());
                }
            }
            source.sendFeedback(new TranslationTextComponent("commands.woot.give.ok",
                    target.getDisplayName(), resourceLocation.toString()), true);
        } else {
            source.sendFeedback(new TranslationTextComponent("commands.woot.give.fail",
                    resourceLocation.toString()), true);
        }

        return 1;
    }
}
