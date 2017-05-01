package ipsis.woot.manager;

// Internal blacklists, cannot override
public class MobBlacklist {

    // Blacklist ALL mobs from the following mods
    private static final String[] mods = {
            "cyberware",
            "botania",
            "withercrumbs",
            "draconicevolution"
    };

    // Blacklist specific mob
    private static final String[] mobs = {
            "Woot:none:arsmagica2.Dryad",
            "Woot:none:abyssalcraft.lesserdreadbeast",
            "Woot:none:abyssalcraft.greaterdreadspawn",
            "Woot:none:abyssalcraft.chagaroth",
            "Woot:none:abyssalcraft.shadowboss",
            "Woot:none:abyssalcraft.Jzahar",
            "Woot:none:roots.spriteGuardian",
            "Woot:none:natura.heatscarspider"
    };

    public static boolean isMobBlacklisted(String wootName) {

        for (String mod : mods) {
            if (wootName.toLowerCase().contains(mod))
                return true;
        }

        for (String mob : mobs) {
            if (wootName.equalsIgnoreCase(mob))
                return true;
        }

        return false;
    }
}