package ipsis.woot.util;

import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.util.math.MathHelper;

public enum FactoryTier {

    TIER_1, TIER_2, TIER_3, TIER_4, TIER_5;

    public String getLocalisedName() {
        return StringHelper.localise("misc.woot." + this.toString().toLowerCase());
    }

    private static FactoryTier[] VALUES = values();
    public FactoryTier getNext() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }

    public static FactoryTier byIndex(int index) {
        index = MathHelper.clamp(index, 0, VALUES.length);
        return VALUES[index];
    }
}
