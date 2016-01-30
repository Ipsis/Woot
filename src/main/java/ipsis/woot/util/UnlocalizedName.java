package ipsis.woot.util;

import ipsis.woot.reference.Reference;

public class UnlocalizedName {

    private static String getUnwrappedUnlocalizedName(String s) {
        return s.substring(s.indexOf(".") + 1);
    }

    private static String getUnlocalizedName(String t, String s) {
        return String.format("%s.%s%s", t, Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(s));
    }

    public static String getUnlocalizedNameItem(String s) {
        return getUnlocalizedName("item", s);
    }

    public static String getUnlocalizedNameBlock(String s) {
        return getUnlocalizedName("tile", s);
    }
}
