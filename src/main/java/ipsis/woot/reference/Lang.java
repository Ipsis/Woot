package ipsis.woot.reference;

public class Lang {

    public static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";
    public static final String TAG_TOOLTIP = "tooltip." + Reference.MOD_ID + ":";
    public static final String TAG_WAILA = "waila." + Reference.MOD_ID + ":";

    public static String getLangConfigValue(String tag) {

        return TAG_CONFIG + tag;
    }

    public static final String WAILA_FACTORY_TIER = TAG_WAILA + "factory.tier";
    public static final String WAILA_FACTORY_MOB = TAG_WAILA + "factory.mob";
    public static final String WAILA_FACTORY_RATE = TAG_WAILA + "factory.rate";
    public static final String WAILA_FACTORY_COST = TAG_WAILA + "factory.cost";

    public static final String TOOLTIP_FACTORY_COST = TAG_TOOLTIP + "factory_cost";

    public static final String TOOLTIP_UPGRADE_COST = TAG_TOOLTIP + "upgrade_cost";

    public static final String TOOLTIP_LOOTING_I_EFFECT = TAG_TOOLTIP + "upgrade_lootingI_effect";
    public static final String TOOLTIP_LOOTING_II_EFFECT = TAG_TOOLTIP + "upgrade_lootingII_effect";
    public static final String TOOLTIP_LOOTING_III_EFFECT = TAG_TOOLTIP + "upgrade_lootingIII_effect";

    public static final String TOOLTIP_RATE_EFFECT = TAG_TOOLTIP + "upgrade_rate_effect";
    public static final String TOOLTIP_XP_EFFECT = TAG_TOOLTIP + "upgrade_xp_effect";
    public static final String TOOLTIP_MASS_EFFECT = TAG_TOOLTIP + "upgrade_mass_effect";
    public static final String TOOLTIP_DECAP_EFFECT = TAG_TOOLTIP + "upgrade_decap_effect";

    public static final String TOOLTIP_UPGRADE = TAG_TOOLTIP + "upgrade.";
}
