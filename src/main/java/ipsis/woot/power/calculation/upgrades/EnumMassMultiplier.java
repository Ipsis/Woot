package ipsis.woot.power.calculation.upgrades;

public enum EnumMassMultiplier {
    LINEAR,
    X_BASE_2, // multiply by 2^(mob-1) (eg 1,2,4,8)
    X_BASE_3 // multiply by 3^(mob-1) (eg 1,3,9,27)
    ;
}
