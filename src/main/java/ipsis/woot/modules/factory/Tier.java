package ipsis.woot.modules.factory;

import ipsis.woot.Woot;

import java.util.EnumSet;
import java.util.Locale;

public enum Tier {

    UNKNOWN,
    TIER_1,
    TIER_2,
    TIER_3,
    TIER_4,
    TIER_5;

    static final EnumSet<Tier> VALID_FOR_TIER_1 = EnumSet.of(TIER_1);
    static final EnumSet<Tier> VALID_FOR_TIER_2 = EnumSet.range(TIER_1, TIER_2);
    static final EnumSet<Tier> VALID_FOR_TIER_3 = EnumSet.range(TIER_1, TIER_3);
    static final EnumSet<Tier> VALID_FOR_TIER_4 = EnumSet.range(TIER_1, TIER_4);
    static final EnumSet<Tier> VALID_FOR_TIER_5 = EnumSet.range(TIER_1, TIER_5);
    public static final EnumSet<Tier> VALID_TIERS = EnumSet.range(TIER_1, TIER_5);

    public static Tier[] VALUES = values();

    public static Tier byIndex(int index) {
        if (index < 0 && index >= Tier.values().length)
            return UNKNOWN;
        return values()[index];
    }

    public Tier getNextValid() {
        Tier next = VALUES[(this.ordinal() + 1) % VALUES.length];
        if (next == UNKNOWN)
            next = VALUES[(UNKNOWN.ordinal() + 1) % VALUES.length];
        return next;
    }

    public static int getMaxTier() {
        return VALUES.length - 1;
    }

    /**
     * check must be <= the current tier
     */
    public boolean isValidForTier(Tier check) {
        if (this == TIER_1) return VALID_FOR_TIER_1.contains(check);
        if (this == TIER_2) return VALID_FOR_TIER_2.contains(check);
        if (this == TIER_3) return VALID_FOR_TIER_3.contains(check);
        if (this == TIER_4) return VALID_FOR_TIER_4.contains(check);
        if (this == TIER_5) return VALID_FOR_TIER_5.contains(check);
        return false;
    }

    public String getTranslationKey() {
        return "misc.woot." + this.name().toLowerCase(Locale.ROOT);
    }
}
