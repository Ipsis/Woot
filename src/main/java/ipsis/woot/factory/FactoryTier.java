package ipsis.woot.factory;

import net.minecraft.util.math.MathHelper;

public enum FactoryTier {

    TIER_1, TIER_2, TIER_3, TIER_4, TIER_5;

    public String getLocalisedNam() { return "bob"; }

    private static FactoryTier[] VALUES = values();
    public FactoryTier getNext() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }

    public static FactoryTier byIndex(int index) {
        index = MathHelper.clamp(index, 0, VALUES.length - 1);
        return VALUES[index];
    }
}
