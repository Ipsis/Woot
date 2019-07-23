package ipsis.woot.factory;

public enum Tier {

    UNKNOWN,
    TIER_1,
    TIER_2,
    TIER_3,
    TIER_4;

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

}
