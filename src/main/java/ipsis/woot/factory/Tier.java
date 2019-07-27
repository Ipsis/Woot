package ipsis.woot.factory;

import java.util.EnumSet;

public enum Tier {

    UNKNOWN,
    TIER_1,
    TIER_2,
    TIER_3,
    TIER_4;

    static final EnumSet<Tier> VALID_FOR_TIER_1 = EnumSet.of(TIER_1);
    static final EnumSet<Tier> VALID_FOR_TIER_2 = EnumSet.range(TIER_1, TIER_2);
    static final EnumSet<Tier> VALID_FOR_TIER_3 = EnumSet.range(TIER_1, TIER_3);
    static final EnumSet<Tier> VALID_FOR_TIER_4 = EnumSet.range(TIER_1, TIER_4);

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
        return false;

    }
}
