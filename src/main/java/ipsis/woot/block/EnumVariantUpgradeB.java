package ipsis.woot.block;

import net.minecraft.util.IStringSerializable;

public enum EnumVariantUpgradeB implements IStringSerializable {

    EFFICIENCY_I,
    EFFICIENCY_II,
    EFFICIENCY_III;

    public int getMetadata() {

        return this.ordinal();
    }

    public static EnumVariantUpgradeB getFromMetadata(int meta) {

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
