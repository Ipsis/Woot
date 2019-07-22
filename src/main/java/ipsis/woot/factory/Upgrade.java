package ipsis.woot.factory;

import java.util.EnumSet;

public enum Upgrade {

    MASS_1,
    MASS_2,
    MASS_3,
    RATE_1,
    RATE_2,
    RATE_3,
    LOOTING_1,
    LOOTING_2,
    LOOTING_3,
    DECAP_1,
    DECAP_2,
    DECAP_3,
    XP_1,
    XP_2,
    XP_3;

    public static EnumSet<Upgrade> LEVEL_1_UPGRADES = EnumSet.of(MASS_1, RATE_1, LOOTING_1, DECAP_1, XP_1);
    public static EnumSet<Upgrade> LEVEL_2_UPGRADES = EnumSet.of(MASS_2, RATE_2, LOOTING_2, DECAP_2, XP_2);
    public static EnumSet<Upgrade> LEVEL_3_UPGRADES = EnumSet.of(MASS_3, RATE_3, LOOTING_3, DECAP_3, XP_3);

    public static EnumSet<Upgrade> MASS_UPGRADES = EnumSet.of(MASS_1, MASS_2, MASS_3);
    public static EnumSet<Upgrade> RATE_UPGRADES = EnumSet.of(RATE_1, RATE_2, RATE_3);
    public static EnumSet<Upgrade> LOOTING_UPGRADES = EnumSet.of(LOOTING_1, LOOTING_2, LOOTING_3);
    public static EnumSet<Upgrade> DECAP_UPGRADES = EnumSet.of(DECAP_1, DECAP_2, DECAP_3);
    public static EnumSet<Upgrade> XP_UPGRADES = EnumSet.of(XP_1, XP_2, XP_3);

}
