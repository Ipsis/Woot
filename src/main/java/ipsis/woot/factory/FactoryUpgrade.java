package ipsis.woot.factory;

public enum FactoryUpgrade {
    EFFICIENCY_1,
    EFFICIENCY_2,
    EFFICIENCY_3,
    LOOTING_1,
    LOOTING_2,
    LOOTING_3,
    MASS_1,
    MASS_2,
    MASS_3,
    RATE_1,
    RATE_2,
    RATE_3,
    XP_1,
    XP_2,
    XP_3;

    public static FactoryUpgrade[] VALUES = values();
    public String getName() { return name().toLowerCase(); }
    public String getTranslationKey() { return "item.woot." + getName(); }

}
