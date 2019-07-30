package ipsis.woot.factory;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;
import java.util.Locale;

public enum FactoryUpgrade implements IStringSerializable {
    EMPTY,
    CAPACITY_1,
    CAPACITY_2,
    CAPACITY_3,
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
    public String getName() { return name().toLowerCase(Locale.ROOT); }
    public String getTranslationKey() { return "item.woot." + getName(); }


    public static final EnumSet<FactoryUpgrade> LEVEL_1_UPGRADES = EnumSet.of(CAPACITY_1, EFFICIENCY_1, LOOTING_1, MASS_1, RATE_1, XP_1);
    public static final EnumSet<FactoryUpgrade> LEVEL_2_UPGRADES = EnumSet.of(CAPACITY_2, EFFICIENCY_2, LOOTING_2, MASS_2, RATE_2, XP_2);
    public static final EnumSet<FactoryUpgrade> LEVEL_3_UPGRADES = EnumSet.of(CAPACITY_3, EFFICIENCY_3, LOOTING_3, MASS_3, RATE_3, XP_3);

    public static final EnumSet<FactoryUpgrade> CAPACITY_UPGRADES = EnumSet.of(CAPACITY_1, CAPACITY_2, CAPACITY_3);
    public static final EnumSet<FactoryUpgrade> EFFICIENCY_UPGRADES = EnumSet.of(EFFICIENCY_1, EFFICIENCY_2, EFFICIENCY_3);
    public static final EnumSet<FactoryUpgrade> LOOTING_UPGRADES = EnumSet.of(LOOTING_1, LOOTING_2, LOOTING_3);
    public static final EnumSet<FactoryUpgrade> MASS_UPGRADES = EnumSet.of(MASS_1, MASS_2, MASS_3);
    public static final EnumSet<FactoryUpgrade> RATE_UPGRADES = EnumSet.of(RATE_1, RATE_2, RATE_3);
    public static final EnumSet<FactoryUpgrade> XP_UPGRADES = EnumSet.of(XP_1, XP_2, XP_3);

    public static FactoryUpgrade getUpgrade(FactoryUpgradeType type, int level) {
        // Hmmmm, not sure about this
        FactoryUpgrade upgrade = null;
        level = MathHelper.clamp(level, 1, 3) - 1;
        if (type == FactoryUpgradeType.CAPACITY) {
            upgrade = CAPACITY_UPGRADES.toArray(new FactoryUpgrade[0])[level];
        } else if (type == FactoryUpgradeType.EFFICIENCY) {
            upgrade = EFFICIENCY_UPGRADES.toArray(new FactoryUpgrade[0])[level];
        } else if (type == FactoryUpgradeType.LOOTING) {
            upgrade = LOOTING_UPGRADES.toArray(new FactoryUpgrade[0])[level];
        } else if (type == FactoryUpgradeType.MASS) {
            upgrade = MASS_UPGRADES.toArray(new FactoryUpgrade[0])[level];
        } else if (type == FactoryUpgradeType.RATE) {
            upgrade = RATE_UPGRADES.toArray(new FactoryUpgrade[0])[level];
        } else if (type == FactoryUpgradeType.XP) {
            upgrade = XP_UPGRADES.toArray(new FactoryUpgrade[0])[level];
        }
        return upgrade;
    }

    public static FactoryUpgradeType getType(FactoryUpgrade upgrade) {
        if (CAPACITY_UPGRADES.contains(upgrade))
            return FactoryUpgradeType.CAPACITY;
        if (EFFICIENCY_UPGRADES.contains(upgrade))
            return FactoryUpgradeType.EFFICIENCY;
        if (LOOTING_UPGRADES.contains(upgrade))
            return FactoryUpgradeType.LOOTING;
        if (MASS_UPGRADES.contains(upgrade))
            return FactoryUpgradeType.MASS;
        if (RATE_UPGRADES.contains(upgrade))
            return FactoryUpgradeType.RATE;

        return FactoryUpgradeType.XP;
    }

    public static int getLevel(FactoryUpgrade upgrade) {
        if (LEVEL_1_UPGRADES.contains(upgrade))
            return 1;
        if (LEVEL_2_UPGRADES.contains(upgrade))
            return 2;

        return 3;
    }

}
