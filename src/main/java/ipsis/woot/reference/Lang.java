package ipsis.woot.reference;

public class Lang {

    public static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";

    public static String getLangConfigValue(String tag) {

        return TAG_CONFIG + tag;
    }
}
