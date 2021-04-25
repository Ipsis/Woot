package ipsis.woot.modules.factory.perks;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

import java.util.*;

public enum Perk implements IStringSerializable {

    EMPTY(null),
    EFFICIENCY_1(Group.EFFICIENCY),
    EFFICIENCY_2(Group.EFFICIENCY),
    EFFICIENCY_3(Group.EFFICIENCY),
    LOOTING_1(Group.LOOTING),
    LOOTING_2(Group.LOOTING),
    LOOTING_3(Group.LOOTING),
    MASS_1(Group.MASS),
    MASS_2(Group.MASS),
    MASS_3(Group.MASS),
    RATE_1(Group.RATE),
    RATE_2(Group.RATE),
    RATE_3(Group.RATE),
    TIER_SHARD_1(Group.TIER_SHARD),
    TIER_SHARD_2(Group.TIER_SHARD),
    TIER_SHARD_3(Group.TIER_SHARD),
    XP_1(Group.XP),
    XP_2(Group.XP),
    XP_3(Group.XP),
    HEADLESS_1(Group.HEADLESS),
    HEADLESS_2(Group.HEADLESS),
    HEADLESS_3(Group.HEADLESS),
    SLAUGHTER_1(Group.SLAUGHTER),
    SLAUGHTER_2(Group.SLAUGHTER),
    SLAUGHTER_3(Group.SLAUGHTER),
    CRUSHER_1(Group.CRUSHER),
    CRUSHER_2(Group.CRUSHER),
    CRUSHER_3(Group.CRUSHER),
    LASER_1(Group.LASER),
    LASER_2(Group.LASER),
    LASER_3(Group.LASER),
    FLAYED_1(Group.FLAYED),
    FLAYED_2(Group.FLAYED),
    FLAYED_3(Group.FLAYED)
    ;

    private Group group;
    Perk(Group group) {
        this.group = group;
    }

    public static Perk[] VALUES = values();
    public String getLowerCaseName() { return name().toLowerCase(Locale.ROOT); }
    public String getString() { return getLowerCaseName(); }

    public static Perk byIndex(int index) {
        index = MathHelper.clamp(index, 0, VALUES.length - 1);
        return VALUES[index];
    }

    public static final EnumSet<Perk> LEVEL_1_PERKS = EnumSet.of(EFFICIENCY_1, LOOTING_1, MASS_1, RATE_1, TIER_SHARD_1, XP_1, HEADLESS_1, SLAUGHTER_1, CRUSHER_1, LASER_1, FLAYED_1);
    public static final EnumSet<Perk> LEVEL_2_PERKS = EnumSet.of(EFFICIENCY_2, LOOTING_2, MASS_2, RATE_2, TIER_SHARD_2, XP_2, HEADLESS_2, SLAUGHTER_2, CRUSHER_2, LASER_2, FLAYED_2);
    public static final EnumSet<Perk> LEVEL_3_PERKS = EnumSet.of(EFFICIENCY_3, LOOTING_3, MASS_3, RATE_3, TIER_SHARD_3, XP_3, HEADLESS_3, SLAUGHTER_3, CRUSHER_3, LASER_3, FLAYED_3);

    public static final EnumSet<Perk> EFFICIENCY_PERKS = EnumSet.of(EFFICIENCY_1, EFFICIENCY_2, EFFICIENCY_3);
    public static final EnumSet<Perk> LOOTING_PERKS = EnumSet.of(LOOTING_1, LOOTING_2, LOOTING_3);
    public static final EnumSet<Perk> MASS_PERKS = EnumSet.of(MASS_1, MASS_2, MASS_3);
    public static final EnumSet<Perk> RATE_PERKS = EnumSet.of(RATE_1, RATE_2, RATE_3);
    public static final EnumSet<Perk> XP_PERKS = EnumSet.of(XP_1, XP_2, XP_3);
    public static final EnumSet<Perk> TIER_SHARD_PERKS = EnumSet.of(TIER_SHARD_1, TIER_SHARD_2, TIER_SHARD_3);
    public static final EnumSet<Perk> HEADLESS_PERKS = EnumSet.of(HEADLESS_1, HEADLESS_2, HEADLESS_3);
    public static final EnumSet<Perk> SLAUGHTER_PERKS = EnumSet.of(SLAUGHTER_1, SLAUGHTER_2, SLAUGHTER_3);
    public static final EnumSet<Perk> CRUSHER_PERKS = EnumSet.of(CRUSHER_1, CRUSHER_2, CRUSHER_3);
    public static final EnumSet<Perk> LASER_PERKS = EnumSet.of(LASER_1, LASER_2, LASER_3);
    public static final EnumSet<Perk> FLAYED_PERKS = EnumSet.of(FLAYED_1, FLAYED_2, FLAYED_3);

    private static final Map<Group, EnumSet<Perk>> perkMap = new HashMap<>();
    static {
        perkMap.put(Group.EFFICIENCY, EFFICIENCY_PERKS);
        perkMap.put(Group.LOOTING, LOOTING_PERKS);
        perkMap.put(Group.MASS, MASS_PERKS);
        perkMap.put(Group.RATE, RATE_PERKS);
        perkMap.put(Group.XP, XP_PERKS);
        perkMap.put(Group.TIER_SHARD, TIER_SHARD_PERKS);
        perkMap.put(Group.HEADLESS, HEADLESS_PERKS);
        perkMap.put(Group.SLAUGHTER, SLAUGHTER_PERKS);
        perkMap.put(Group.CRUSHER, CRUSHER_PERKS);
        perkMap.put(Group.LASER, LASER_PERKS);
        perkMap.put(Group.FLAYED, FLAYED_PERKS);
    }

    public static EnumSet<Perk> getPerksByGroup(Group group) {
        EnumSet<Perk> perks = perkMap.get(group);
        // Have we forgotten to add a new map entry for a new group
        if (perks == null)
            throw new IllegalArgumentException("No map entry for perk group");
        return perkMap.get(group);
    }

    public static Group getGroup(Perk perk) {
        return perk.group;
    }

    public static int getLevel(Perk perk) {
        int level = 3;
        if (LEVEL_1_PERKS.contains(perk))
            level = 1;
        else if (LEVEL_2_PERKS.contains(perk))
            level = 2;
        return level;
    }

    public enum Group {
        EFFICIENCY,
        LOOTING,
        MASS,
        RATE,
        XP,
        TIER_SHARD,
        HEADLESS,

        // Industrial Foregoing
        SLAUGHTER, CRUSHER, LASER,

        // Blood Magic
        FLAYED
        ;

        private static final Group VALUES[] = Group.values();
        public static Group byIndex(int index) {
            index = MathHelper.clamp(index, 0, VALUES.length - 1);
            return VALUES[index];
        }

        public String getLowerCaseName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
