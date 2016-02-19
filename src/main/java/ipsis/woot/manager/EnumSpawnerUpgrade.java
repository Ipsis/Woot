package ipsis.woot.manager;

import net.minecraft.util.IStringSerializable;

public enum EnumSpawnerUpgrade implements IStringSerializable {

    RATE_I,
    RATE_II,
    RATE_III,
    LOOTING_I,
    LOOTING_II,
    LOOTING_III,
    XP_I,
    XP_II,
    XP_III;

    public int getMetadata() {

        return this.ordinal();
    }

    public static EnumSpawnerUpgrade getFromMetadata(int meta) {

        if (meta < 0 || meta > values().length)
            return values()[0];

        return values()[meta];
    }

    @Override
    public String getName() {

        return toString();
    }

    @Override
    public String toString() {

        return super.toString().toLowerCase();
    }
}
