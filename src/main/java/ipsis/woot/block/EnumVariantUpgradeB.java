package ipsis.woot.block;

import net.minecraft.util.IStringSerializable;

public enum EnumVariantUpgradeB implements IStringSerializable {

    EFFICIENCY_I,
    EFFICIENCY_II,
    EFFICIENCY_III,
    BM_LE_TANK_I,
    BM_LE_TANK_II,
    BM_LE_TANK_III,
    BM_LE_ALTAR_I,
    BM_LE_ALTAR_II,
    BM_LE_ALTAR_III,
    EC_BLOOD_I,
    EC_BLOOD_II,
    EC_BLOOD_III,
    BM_CRYSTAL_I,
    BM_CRYSTAL_II,
    BM_CRYSTAL_III;

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
