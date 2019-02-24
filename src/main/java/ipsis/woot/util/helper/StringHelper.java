package ipsis.woot.util.helper;

import net.minecraft.util.text.translation.LanguageMap;

public class StringHelper {

    public static String translate(String key) {
        return LanguageMap.getInstance().translateKey(key);
    }

    public static String translateFormat(String key, Object... format) {
        return LanguageMap.getInstance().translateKey(key);
    }
}
