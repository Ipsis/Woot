package ipsis.woot.modules.factory;

import java.util.Locale;

public enum FactoryComponent {

    FACTORY_A,
    FACTORY_B,
    FACTORY_C,
    FACTORY_D,
    FACTORY_E,
    FACTORY_CONNECT,
    FACTORY_CTR_BASE,
    FACTORY_UPGRADE,
    HEART,
    CAP_A,
    CAP_B,
    CAP_C,
    CAP_D,
    IMPORT,
    EXPORT,
    CONTROLLER,
    CELL;

    public static FactoryComponent[] VALUES = values();
    public String getName() { return name().toLowerCase(Locale.ROOT); }
    public String getTranslationKey() { return "block.woot." + getName(); }

    public static boolean isSameComponentFuzzy(FactoryComponent componentA, FactoryComponent componentB) {
        return componentA == componentB;
    }
}
