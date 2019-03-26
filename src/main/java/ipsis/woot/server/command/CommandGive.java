package ipsis.woot.server.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ipsis.woot.Woot;
import ipsis.woot.factory.TileEntityController;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.mod.ModItems;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandGive {

    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("give")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("target", EntityArgument.singlePlayer())
                        .then(Commands.argument("entities", EntityArgument.multipleEntities())
                                .executes(ctx -> giveItem(ctx.getSource(), EntityArgument.getOnePlayer(ctx, "target"), EntityArgument.getEntities(ctx, "entities")))));
    }

    private static int giveItem(CommandSource source, EntityPlayerMP playerMP, Collection<? extends Entity> entities) throws CommandSyntaxException {

        List<EntityLiving> mobs = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof EntityLiving)
                    mobs.add((EntityLiving) e);
        }
        ItemStack itemStack = TileEntityController.getItemStack(mobs);

        boolean flag = playerMP.inventory.addItemStackToInventory(itemStack);
        if (flag && itemStack.isEmpty()) {
            itemStack.setCount(1);
            EntityItem entityItem = playerMP.dropItem(itemStack, false);
            if (entityItem != null)
                entityItem.makeFakeItem();

            playerMP.world.playSound((EntityPlayer)null, playerMP.posX, playerMP.posY, playerMP.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerMP.getRNG().nextFloat() - playerMP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            playerMP.inventoryContainer.detectAndSendChanges();
        } else {
            EntityItem entityitem = playerMP.dropItem(itemStack, false);
            if (entityitem != null) {
                entityitem.setNoPickupDelay();
                entityitem.setOwnerId(playerMP.getUniqueID());
            }
        }

//        source.sendFeedback(new TextComponentTranslation("commands.give.success.single", count, p_198497_1_.createStack(count, false).getTextComponent(), targets.iterator().next().getDisplayName()), true);
        return 1;
    }
}
