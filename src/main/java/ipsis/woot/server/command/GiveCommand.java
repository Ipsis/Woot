package ipsis.woot.server.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import ipsis.woot.factory.blocks.ControllerTileEntity;
import ipsis.woot.util.FakeMob;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;

public class GiveCommand {

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("give")
                .requires(cs -> cs.hasPermissionLevel(0))
                .then(Commands.argument("target", EntityArgument.player())
                    .then(Commands.argument("entity", StringArgumentType.string())
                            .executes(ctx -> giveItem(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), StringArgumentType.getString(ctx, "entity")))));
    }

    private static int giveItem(CommandSource source, ServerPlayerEntity target, String entityKey) {

        FakeMob fakeMob = new FakeMob(entityKey);
        if (fakeMob.isValid()) {
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
                        target.posX, target.posY, target.posZ,
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
            source.sendFeedback(new TranslationTextComponent("commands.woot.give.ok", target.getDisplayName(), entityKey), true);
        } else {
            source.sendFeedback(new TranslationTextComponent("commands.woot.give.fail", entityKey), true);
        }

        return 1;
    }
}
