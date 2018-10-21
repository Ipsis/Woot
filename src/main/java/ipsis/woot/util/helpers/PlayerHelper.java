package ipsis.woot.util.helpers;

import ipsis.woot.util.FactoryBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class PlayerHelper {

    public static void sendActionBarMessage(EntityPlayer entityPlayer, String s) {
        if (entityPlayer != null && s != null)
            entityPlayer.sendStatusMessage(new TextComponentString(s), true);
    }

    public static void sendChatMessage(EntityPlayer entityPlayer, String s) {
        if (entityPlayer != null && s != null)
            entityPlayer.sendStatusMessage(new TextComponentString(s), false);
    }

    public static void sendChatMessage(TextFormatting color, EntityPlayer entityPlayer, String s) {
        if (entityPlayer != null && s != null)
            entityPlayer.sendStatusMessage(new TextComponentString(color + s), false);
    }

    public static boolean hasFactoryBlock(EntityPlayer entityPlayer, FactoryBlock factoryBlock) {

        if (entityPlayer.capabilities.isCreativeMode)
            return true;

        for (ItemStack playerItemStack : entityPlayer.inventory.mainInventory) {

            if (playerItemStack.isEmpty())
                continue;

            if (FactoryBlock.isSameFactoryBlock(factoryBlock, playerItemStack))
                return true;
        }

        return false;
    }

    public static void takeFactoryBlock(EntityPlayer entityPlayer, FactoryBlock factoryBlock) {

        if (entityPlayer.capabilities.isCreativeMode)
            return;

        for (ItemStack playerItemStack : entityPlayer.inventory.mainInventory) {

            if (playerItemStack.isEmpty())
                continue;

            if (FactoryBlock.isSameFactoryBlock(factoryBlock, playerItemStack)) {
                playerItemStack.shrink(1);
                return;
            }
        }
    }
}
