package ipsis.woot.util;

import net.minecraft.util.StatCollector;

public class StringHelper {

    public static String localize(String s) {

        return StatCollector.translateToLocal(s);
    }
}
