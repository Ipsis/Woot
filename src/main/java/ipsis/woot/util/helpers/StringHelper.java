package ipsis.woot.util.helpers;

import net.minecraft.util.text.translation.I18n;

public class StringHelper {

    public static String localise(String s) {
        return I18n.translateToLocal(s);
    }

    public static String localise(String s, Object ...format) {
        return I18n.translateToLocalFormatted(s, format);
    }


}
