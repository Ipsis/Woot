package ipsis.woot.block;

import net.minecraft.util.IStringSerializable;

public enum EnumVariantUpgrade implements IStringSerializable {

    RATE_I,
    RATE_II,
    RATE_III,
    LOOTING_I,
    LOOTING_II,
    LOOTING_III,
    XP_I,
    XP_II,
    XP_III,
    MASS_I,
    MASS_II,
    MASS_III,
    DECAPITATE_I,
    DECAPITATE_II,
    DECAPITATE_III;

    public int getMetadata() {

        return this.ordinal();
    }

    public static EnumVariantUpgrade getFromMetadata(int meta) {

        if (meta < 0 || meta >= values().length)
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
