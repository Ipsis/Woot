package ipsis.woot.factory;

import net.minecraft.util.math.MathHelper;

public enum FactoryTier {

    TIER_1(1), TIER_2(2), TIER_3(3), TIER_4(4), TIER_5(5);

    private int num;
    public int getNum() { return this.num; }

    private static FactoryTier[] VALUES = values();
    public FactoryTier getNext() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }

    FactoryTier(int num) {
        this.num = num;
    }

    public static FactoryTier byIndex(int index) {
        index = MathHelper.clamp(index, 0, VALUES.length - 1);
        return VALUES[index];
    }
}
