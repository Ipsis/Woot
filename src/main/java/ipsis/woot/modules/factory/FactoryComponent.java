package ipsis.woot.modules.factory;

import net.minecraft.block.BlockState;

import java.util.Locale;

public enum FactoryComponent {

    FACTORY_A,
    FACTORY_B,
    FACTORY_C,
    FACTORY_D,
    FACTORY_E,
    FACTORY_CONNECT,
    FACTORY_CTR_BASE_PRI,
    FACTORY_CTR_BASE_SEC,
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

    public BlockState getDefaultBlockState() {
        if (this == FACTORY_A)
            return FactorySetup.FACTORY_A_BLOCK.get().getDefaultState();
        else if (this == FACTORY_B)
            return FactorySetup.FACTORY_B_BLOCK.get().getDefaultState();
        else if (this == FACTORY_C)
            return FactorySetup.FACTORY_C_BLOCK.get().getDefaultState();
        else if (this == FACTORY_D)
            return FactorySetup.FACTORY_D_BLOCK.get().getDefaultState();
        else if (this == FACTORY_E)
            return FactorySetup.FACTORY_E_BLOCK.get().getDefaultState();
        else if (this == FACTORY_CONNECT)
            return FactorySetup.FACTORY_CONNECT_BLOCK.get().getDefaultState();
        else if (this == FACTORY_CTR_BASE_PRI)
            return FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get().getDefaultState();
        else if (this == FACTORY_CTR_BASE_SEC)
            return FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get().getDefaultState();
        else if (this == FACTORY_UPGRADE)
            return FactorySetup.FACTORY_UPGRADE_BLOCK.get().getDefaultState();
        else if (this == CAP_A)
            return FactorySetup.CAP_A_BLOCK.get().getDefaultState();
        else if (this == CAP_B)
            return FactorySetup.CAP_B_BLOCK.get().getDefaultState();
        else if (this == CAP_C)
            return FactorySetup.CAP_C_BLOCK.get().getDefaultState();
        else if (this == CAP_D)
            return FactorySetup.CAP_D_BLOCK.get().getDefaultState();
        else if (this == IMPORT)
            return FactorySetup.IMPORT_BLOCK.get().getDefaultState();
        else if (this == EXPORT)
            return FactorySetup.EXPORT_BLOCK.get().getDefaultState();
        else if (this == CONTROLLER)
            return FactorySetup.CONTROLLER_BLOCK.get().getDefaultState();
        else if (this == CELL)
            return FactorySetup.CELL_1_BLOCK.get().getDefaultState();

        return FactorySetup.HEART_BLOCK.get().getDefaultState();
    }
}
