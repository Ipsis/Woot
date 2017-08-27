package ipsis.woot.util;


import net.minecraft.util.text.translation.I18n;

public class StringHelper {

    public static String localize(String s) {

        return I18n.translateToLocal(s);
    }

    public static String localizeFormat(String s, Object... format) {

        return I18n.translateToLocalFormatted(s, format);
    }

    public static String getInfoText(String key) {

        // TODO apply consistent coloring
        return localize(key);

    }
}
