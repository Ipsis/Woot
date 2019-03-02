package ipsis.woot.util.helper;

import ipsis.woot.factory.layout.FactoryBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class PlayerHelper {

    public static void sendActionBarMessage(EntityPlayer entityPlayer, String translatedText) {
        if (entityPlayer != null && translatedText != null)
            entityPlayer.sendStatusMessage(new TextComponentString(translatedText), true);
    }

    public static void sendChatMessage(EntityPlayer entityPlayer, String translatedText) {
        if (entityPlayer != null && translatedText != null)
            entityPlayer.sendStatusMessage(new TextComponentString(translatedText), false);
    }

    /**
     * @param fuzzy - when true allow any cell rather than specific one
     */
    public static boolean hasFactoryBlock(EntityPlayer entityPlayer, FactoryBlock factoryBlock, boolean fuzzy) {

        // Creative players have all the things
        if (entityPlayer.isCreative())
            return true;

        return false;
    }

    /**
     * @param fuzzy - when true allow any cell rather than specific one
     */
    public static void takeFactoryBlock(EntityPlayer entityPlayer, FactoryBlock factoryBlock, boolean fuzzy) {

        if (entityPlayer.isCreative())
            return;
    }
}
