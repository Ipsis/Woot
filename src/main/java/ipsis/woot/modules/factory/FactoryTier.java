package ipsis.woot.modules.factory;

import net.minecraft.util.math.MathHelper;

public enum FactoryTier {

    UNKNOWN,
    TIER_1,
    TIER_2,
    TIER_3,
    TIER_4,
    TIER_5;

    private static FactoryTier[] VALUES = values();

    public FactoryTier getNextValid() {

        FactoryTier next = VALUES[(this.ordinal() + 1) % VALUES.length];
        if (next == UNKNOWN)
            next = VALUES[(UNKNOWN.ordinal() + 1) % VALUES.length];
        return next;
    }

    public int getId() {
        return this.ordinal();
    }

    public static FactoryTier getById(int id) {
        id = MathHelper.clamp(id, 0, VALUES.length - 1);
        return VALUES[id];
    }
}
