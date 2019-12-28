package ipsis.woot.util.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class PlayerHelper {

    public static void sendActionBarMessage(PlayerEntity entityPlayer, String translatedText) {
        if (entityPlayer != null && translatedText != null)
            entityPlayer.sendStatusMessage(new StringTextComponent(translatedText), true);
    }

    public static void sendChatMessage(PlayerEntity entityPlayer, String translatedText) {
        if (entityPlayer != null && translatedText != null)
            entityPlayer.sendStatusMessage(new StringTextComponent(translatedText), false);
    }

   public  static boolean playerHasFactoryComponent(PlayerEntity playerEntity, List<ItemStack> validStacks) {
        if (playerEntity.isCreative())
            return true;

        for (ItemStack itemStack : playerEntity.inventory.mainInventory) {
            if (itemStack.isEmpty())
                continue;

            for (ItemStack c : validStacks)
                if (c.getItem() == itemStack.getItem())
                    return true;
        }

        return false;
    }

    public static ItemStack takeFactoryComponent(PlayerEntity playerEntity, List<ItemStack> validStacks) {
        if (validStacks.isEmpty())
            return ItemStack.EMPTY;

        if ((playerEntity.isCreative()))
            return validStacks.get(0);

        for (ItemStack itemStack : playerEntity.inventory.mainInventory) {
            if (itemStack.isEmpty())
                continue;

            for (ItemStack c : validStacks) {
                if (c.getItem() == itemStack.getItem()) {
                    c.shrink(1);
                    playerEntity.inventory.markDirty();
                    return c;
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
