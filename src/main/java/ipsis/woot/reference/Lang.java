package ipsis.woot.reference;

public class Lang {

    public static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";
    public static final String TAG_TOOLTIP = "tooltip." + Reference.MOD_ID + ":";
    public static final String TAG_WAILA = "waila." + Reference.MOD_ID + ":";
    public static final String TAG_VALIDATE = "validate." + Reference.MOD_ID + ":";
    public static final String TAG_CHAT = "chat." + Reference.MOD_ID + ":";
    public static final String TAG_BOOK = "book." + Reference.MOD_ID + ":";

    public static String getLangConfigValue(String tag) {

        return TAG_CONFIG + tag;
    }

    public static final String WAILA_FACTORY_TIER = TAG_WAILA + "factory.tier";
    public static final String WAILA_FACTORY_MOB = TAG_WAILA + "factory.mob";
    public static final String WAILA_FACTORY_RATE = TAG_WAILA + "factory.rate";
    public static final String WAILA_FACTORY_COST = TAG_WAILA + "factory.cost";
    public static final String WAILA_CONTROLLER_TIER = TAG_WAILA + "controller.tier";
    public static final String WAILA_FACTORY_RUNNING = TAG_WAILA + "factory.running";
    public static final String WAILA_FACTORY_STOPPED = TAG_WAILA + "factory.stopped";
    public static final String WAILA_EXTRA_UPGRADE = TAG_WAILA + "factory.sneak";
    public static final String WAILA_NO_UPGRADES = TAG_WAILA + "factory.nullupgrades";

    public static final String TOOLTIP_FACTORY_COST = TAG_TOOLTIP + "factory_cost";

    public static final String TOOLTIP_UPGRADE_COST = TAG_TOOLTIP + "upgrade_cost";

    public static final String TOOLTIP_LOOTING_I_EFFECT = TAG_TOOLTIP + "upgrade_lootingI_effect";
    public static final String TOOLTIP_LOOTING_II_EFFECT = TAG_TOOLTIP + "upgrade_lootingII_effect";
    public static final String TOOLTIP_LOOTING_III_EFFECT = TAG_TOOLTIP + "upgrade_lootingIII_effect";

    public static final String TOOLTIP_RATE_EFFECT = TAG_TOOLTIP + "upgrade_rate_effect";
    public static final String TOOLTIP_XP_EFFECT = TAG_TOOLTIP + "upgrade_xp_effect";
    public static final String TOOLTIP_XP_BASE_EFFECT = TAG_TOOLTIP + "upgrade_xp_base_effect";
    public static final String TOOLTIP_MASS_EFFECT = TAG_TOOLTIP + "upgrade_mass_effect";
    public static final String TOOLTIP_DECAP_EFFECT = TAG_TOOLTIP + "upgrade_decap_effect";
    public static final String TOOLTIP_EFFICIENCY_EFFECT = TAG_TOOLTIP + "upgrade_efficiency_effect";
    public static final String TOOLTIP_BM_EFFECT = TAG_TOOLTIP + "upgrade_bloodmagic_effect";

    public static final String TOOLTIP_UPGRADE = TAG_TOOLTIP + "upgrade.";

    public static final String VALIDATE_FACTORY_MISSING_CONTROLLER = TAG_VALIDATE + "factory.missing_controller";
    public static final String VALIDATE_FACTORY_MISSING_MOB = TAG_VALIDATE + "factory.missing_mob";
    public static final String VALIDATE_FACTORY_INVALID_BLOCK = TAG_VALIDATE + "factory.invalid_block";
    public static final String VALIDATE_FACTORY_MOB_TIER = TAG_VALIDATE + "factory.mob_tier";
    public static final String VALIDATE_FACTORY_BLOCKS_OK = TAG_VALIDATE + "factory.blocks_ok";

    public static final String CHAT_PRISM_INVALID = TAG_CHAT + "prism.invalid";
    public static final String CHAT_PRISM_PROGRAM = TAG_CHAT + "prism.program";
    public static final String CHAT_MOB_INVALID = TAG_CHAT + "mob.invalid";
}
