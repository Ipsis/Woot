package ipsis.woot.modules.factory;

public enum PerkType {
    EFFICIENCY,
    LOOTING,
    MASS,
    RATE,
    XP,
    TIER_SHARD,
    HEADLESS,
    SLAUGHTER, /* Industrial Foregoing */
    CRUSHER, /* Industrial Foregoing */
    LASER /* Industrial Foregoing */
    ;

    public static PerkType byIndex(int index) {
        if (index < 0 && index >= PerkType.values().length)
            return EFFICIENCY;
        return values()[index];
    }
}
