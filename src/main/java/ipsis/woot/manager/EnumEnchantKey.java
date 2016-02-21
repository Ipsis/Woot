package ipsis.woot.manager;

public enum EnumEnchantKey {
    NO_ENCHANT,
    LOOTING_I,
    LOOTING_II,
    LOOTING_III;

    public static EnumEnchantKey getEnchantKey(int v) {

        if (v < 0 || v > values().length)
            v = 0;

        return values()[v];
    }
}
