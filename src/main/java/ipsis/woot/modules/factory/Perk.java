package ipsis.woot.modules.factory;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;
import java.util.Locale;

public enum Perk implements IStringSerializable {
    EMPTY,
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
    TIER_SHARD_1,
    TIER_SHARD_2,
    TIER_SHARD_3,
    XP_1,
    XP_2,
    XP_3;

    public static Perk[] VALUES = values();
    public String getName() { return name().toLowerCase(Locale.ROOT); }

    public static Perk getPerks(int index) {
        index = MathHelper.clamp(index, 0, VALUES.length);
        return VALUES[index];
    }


    public static final EnumSet<Perk> LEVEL_1_PERKS = EnumSet.of(EFFICIENCY_1, LOOTING_1, MASS_1, RATE_1, TIER_SHARD_1, XP_1);
    public static final EnumSet<Perk> LEVEL_2_PERKS = EnumSet.of(EFFICIENCY_2, LOOTING_2, MASS_2, RATE_2, TIER_SHARD_2, XP_2);
    public static final EnumSet<Perk> LEVEL_3_PERKS = EnumSet.of(EFFICIENCY_3, LOOTING_3, MASS_3, RATE_3, TIER_SHARD_3, XP_3);

    public static final EnumSet<Perk> EFFICIENCY_PERKS = EnumSet.of(EFFICIENCY_1, EFFICIENCY_2, EFFICIENCY_3);
    public static final EnumSet<Perk> LOOTING_PERKS = EnumSet.of(LOOTING_1, LOOTING_2, LOOTING_3);
    public static final EnumSet<Perk> MASS_PERKS = EnumSet.of(MASS_1, MASS_2, MASS_3);
    public static final EnumSet<Perk> RATE_PERKS = EnumSet.of(RATE_1, RATE_2, RATE_3);
    public static final EnumSet<Perk> XP_PERKS = EnumSet.of(XP_1, XP_2, XP_3);
    public static final EnumSet<Perk> TIER_SHARD_PERKS = EnumSet.of(TIER_SHARD_1, TIER_SHARD_2, TIER_SHARD_3);

    public static Perk getPerks(PerkType type, int level) {
        // Hmmmm, not sure about this
        Perk upgrade = null;
        level = MathHelper.clamp(level, 1, 3) - 1;
        if (type == PerkType.EFFICIENCY) {
            upgrade = EFFICIENCY_PERKS.toArray(new Perk[0])[level];
        } else if (type == PerkType.LOOTING) {
            upgrade = LOOTING_PERKS.toArray(new Perk[0])[level];
        } else if (type == PerkType.MASS) {
            upgrade = MASS_PERKS.toArray(new Perk[0])[level];
        } else if (type == PerkType.RATE) {
            upgrade = RATE_PERKS.toArray(new Perk[0])[level];
        } else if (type == PerkType.XP) {
            upgrade = XP_PERKS.toArray(new Perk[0])[level];
        } else if (type == PerkType.TIER_SHARD) {
            upgrade = TIER_SHARD_PERKS.toArray(new Perk[0])[level];
        }
        return upgrade;
    }

    public static PerkType getType(Perk perk) {
        if (EFFICIENCY_PERKS.contains(perk))
            return PerkType.EFFICIENCY;
        if (LOOTING_PERKS.contains(perk))
            return PerkType.LOOTING;
        if (MASS_PERKS.contains(perk))
            return PerkType.MASS;
        if (RATE_PERKS.contains(perk))
            return PerkType.RATE;
        if (TIER_SHARD_PERKS.contains(perk))
            return PerkType.TIER_SHARD;

        return PerkType.XP;
    }

    public static int getLevel(Perk perk) {
        if (LEVEL_1_PERKS.contains(perk))
            return 1;
        if (LEVEL_2_PERKS.contains(perk))
            return 2;

        return 3;
    }

}
