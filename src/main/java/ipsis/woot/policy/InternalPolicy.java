package ipsis.woot.policy;

public class InternalPolicy {

    /**
     * Mods whose entities don't work will with Woot
     */
    public static final String[] NO_CAPTURE_FROM_MOD = {
            "cyberware",
            "withercrumbs"
    };

    /**
     * Mods whose drops don't work well with Woot
     */
    public static final String[] NO_LEARN_FROM_MOD = {
            "ebwizardry",
            "eplus",
            "everlastingabilities",
            "cyberware",
    };

    /**
     * Specific entities that don't work will with Woot
     */
    public static final String[] NO_CAPTURE_ENTITY = {
            "twighlightforest:lich",
            "twighlightforest:knight_phantom",
            "twighlightforest:quest_ram",
    };

    /**
     * Specific items that don't work well with Woot
     */
    public static final String[] NO_LEARN = {
    };
}
