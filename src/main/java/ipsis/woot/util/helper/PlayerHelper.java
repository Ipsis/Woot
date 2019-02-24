package ipsis.woot.util.helper;

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
}
