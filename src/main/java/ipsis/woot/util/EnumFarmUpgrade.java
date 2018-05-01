package ipsis.woot.util;

public enum EnumFarmUpgrade {

    DECAPITATE,
    EFFICIENCY,
    LOOTING,
    MASS,
    RATE,
    XP,
    BM_LE_TANK,
    BM_LE_ALTAR;

    public static EnumFarmUpgrade getFromEnumSpawnerUpgrade(EnumSpawnerUpgrade u) {

        EnumFarmUpgrade farmUpgrade;
        switch (u) {
            case DECAPITATE_I:
            case DECAPITATE_II:
            case DECAPITATE_III:
                farmUpgrade = DECAPITATE;
                break;
            case EFFICIENCY_I:
            case EFFICIENCY_II:
            case EFFICIENCY_III:
                farmUpgrade = EFFICIENCY;
                break;
            case LOOTING_I:
            case LOOTING_II:
            case LOOTING_III:
                farmUpgrade = LOOTING;
                break;
            case MASS_I:
            case MASS_II:
            case MASS_III:
                farmUpgrade = MASS;
                break;
            case RATE_I:
            case RATE_II:
            case RATE_III:
                farmUpgrade = RATE;
                break;
            case XP_I:
            case XP_II:
            case XP_III:
                farmUpgrade = XP;
                break;
            case BM_LE_TANK_I:
            case BM_LE_TANK_II:
            case BM_LE_TANK_III:
                farmUpgrade = BM_LE_TANK;
                break;
            case BM_LE_ALTAR_I:
            case BM_LE_ALTAR_II:
            case BM_LE_ALTAR_III:
                farmUpgrade = BM_LE_ALTAR;
                break;
            default:
                farmUpgrade = XP; // Should never happen
                break;
        }

        return farmUpgrade;
    }
}
