package ipsis.woot.util.helper;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.translation.LanguageMap;

public class StringHelper {

    public static String translate(String key) {
        return LanguageMap.getInstance().translateKey(key);
    }

    public static String translateFormat(String key, Object... format) {
        return LanguageMap.getInstance().translateKey(key);
    }

    public static String getInfoText(String key) {
        return ChatFormatting.GREEN + translate(key);
    }
}
