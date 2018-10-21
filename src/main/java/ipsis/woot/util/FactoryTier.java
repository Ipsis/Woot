package ipsis.woot.util;

import ipsis.woot.util.helpers.StringHelper;

public enum FactoryTier {

    TIER_1, TIER_2, TIER_3, TIER_4;

    public String getLocalisedName() {
        return StringHelper.localise("misc.woot." + this.toString().toLowerCase());
    }
}
