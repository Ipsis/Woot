package ipsis.woot.util.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class PlayerHelper {

    public static void sendActionBarMessage(EntityPlayer entityPlayer, String s) {
        if (entityPlayer != null && s != null)
            entityPlayer.sendStatusMessage(new TextComponentString(s), true);
    }

    public static void sendChatMessage(EntityPlayer entityPlayer, String s) {
        if (entityPlayer != null && s != null)
            entityPlayer.sendStatusMessage(new TextComponentString(s), false);
    }
}
