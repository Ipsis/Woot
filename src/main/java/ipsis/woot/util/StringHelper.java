package ipsis.woot.util;


import net.minecraft.util.text.translation.I18n;

public class StringHelper {

    public static String localize(String s) {

        return I18n.translateToLocal(s);
    }
}
