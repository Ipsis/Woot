package ipsis.woot.multiblock;

import ipsis.woot.util.StringHelper;


public enum EnumMobFactoryTier {

    TIER_ONE, TIER_TWO, TIER_THREE, TIER_FOUR;

    public String getTranslated(String format) {

        return String.format(StringHelper.localize(format),
                this == TIER_ONE ? "I" : this == TIER_TWO ? "II" : this == TIER_THREE ? "III" : "IV");
    }

    public int getLevel() {

        // ordinal is 0 based, level is 1 based
        return this.ordinal() + 1;
    }

    public static final EnumMobFactoryTier[] VALID_TIERS = new EnumMobFactoryTier[] { TIER_ONE, TIER_TWO, TIER_THREE, TIER_FOUR };

    public EnumMobFactoryTier getNext() {

        int next = ordinal();
        next++;

        if (next < 0 || next >= VALID_TIERS.length)
            next = 0;

        return VALID_TIERS[next];
    }

    public static EnumMobFactoryTier getTier(int v) {

        if (v < 0 || v > values().length - 1)
            v = 0;

        return values()[v];
    }

    public static boolean isLessThanOrEqual(EnumMobFactoryTier a, EnumMobFactoryTier b) {

        return a.ordinal() <= b.ordinal();

    }
}
