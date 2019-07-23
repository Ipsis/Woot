package ipsis.woot.util.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class PlayerHelper {

    public static void sendActionBarMessage(PlayerEntity entityPlayer, String translatedText) {
        if (entityPlayer != null && translatedText != null)
            entityPlayer.sendStatusMessage(new StringTextComponent(translatedText), true);
    }

    public static void sendChatMessage(PlayerEntity entityPlayer, String translatedText) {
        if (entityPlayer != null && translatedText != null)
            entityPlayer.sendStatusMessage(new StringTextComponent(translatedText), false);
    }
}
