package ipsis.woot.util;

import ipsis.woot.block.EnumVariantUpgrade;
import ipsis.woot.block.EnumVariantUpgradeB;
import ipsis.woot.configuration.EnumConfigKey;
import net.minecraft.util.IStringSerializable;

public enum EnumSpawnerUpgrade implements IStringSerializable {

    RATE_I(EnumConfigKey.RATE_1_POWER_TICK, EnumConfigKey.RATE_1_PARAM, 1),
    RATE_II(EnumConfigKey.RATE_2_POWER_TICK, EnumConfigKey.RATE_2_PARAM, 2),
    RATE_III(EnumConfigKey.RATE_3_POWER_TICK, EnumConfigKey.RATE_3_PARAM, 3),
    LOOTING_I(EnumConfigKey.LOOTING_1_POWER_TICK, EnumConfigKey.LOOTING_1_PARAM, 1),
    LOOTING_II(EnumConfigKey.LOOTING_2_POWER_TICK, EnumConfigKey.LOOTING_2_PARAM, 2),
    LOOTING_III(EnumConfigKey.LOOTING_3_POWER_TICK, EnumConfigKey.LOOTING_3_PARAM, 3),
    XP_I(EnumConfigKey.XP_1_POWER_TICK, EnumConfigKey.XP_1_PARAM, 1),
    XP_II(EnumConfigKey.XP_2_POWER_TICK, EnumConfigKey.XP_2_PARAM, 2),
    XP_III(EnumConfigKey.XP_3_POWER_TICK, EnumConfigKey.XP_3_PARAM, 3),
    MASS_I(EnumConfigKey.MASS_1_POWER_TICK, EnumConfigKey.MASS_1_PARAM, 1),
    MASS_II(EnumConfigKey.MASS_2_POWER_TICK, EnumConfigKey.MASS_2_PARAM, 2),
    MASS_III(EnumConfigKey.MASS_3_POWER_TICK, EnumConfigKey.MASS_3_PARAM, 3),
    DECAPITATE_I(EnumConfigKey.DECAP_1_POWER_TICK, EnumConfigKey.DECAP_1_PARAM, 1),
    DECAPITATE_II(EnumConfigKey.DECAP_2_POWER_TICK, EnumConfigKey.DECAP_2_PARAM, 2),
    DECAPITATE_III(EnumConfigKey.DECAP_3_POWER_TICK, EnumConfigKey.DECAP_3_PARAM, 3),
    EFFICIENCY_I(EnumConfigKey.EFF_1_POWER_TICK, EnumConfigKey.EFF_1_PARAM, 1),
    EFFICIENCY_II(EnumConfigKey.EFF_2_POWER_TICK, EnumConfigKey.EFF_2_PARAM, 2),
    EFFICIENCY_III(EnumConfigKey.EFF_3_POWER_TICK, EnumConfigKey.EFF_3_PARAM, 3),
    BM_LE_TANK_I(EnumConfigKey.BM_LE_TANK_1_POWER_TICK, EnumConfigKey.BM_LE_TANK_1_PARAM, 1),
    BM_LE_TANK_II(EnumConfigKey.BM_LE_TANK_2_POWER_TICK, EnumConfigKey.BM_LE_TANK_2_PARAM, 2),
    BM_LE_TANK_III(EnumConfigKey.BM_LE_TANK_3_POWER_TICK, EnumConfigKey.BM_LE_TANK_3_PARAM, 3),
    BM_LE_ALTAR_I(EnumConfigKey.BM_LE_ALTAR_1_POWER_TICK, EnumConfigKey.BM_LE_ALTAR_1_PARAM, 1),
    BM_LE_ALTAR_II(EnumConfigKey.BM_LE_ALTAR_2_POWER_TICK, EnumConfigKey.BM_LE_ALTAR_2_PARAM, 2),
    BM_LE_ALTAR_III(EnumConfigKey.BM_LE_ALTAR_3_POWER_TICK, EnumConfigKey.BM_LE_ALTAR_3_PARAM, 3),
    EC_BLOOD_I(EnumConfigKey.EC_BLOOD_1_POWER_TICK, EnumConfigKey.EC_BLOOD_1_PARAM, 1),
    EC_BLOOD_II(EnumConfigKey.EC_BLOOD_2_POWER_TICK, EnumConfigKey.EC_BLOOD_2_PARAM, 2),
    EC_BLOOD_III(EnumConfigKey.EC_BLOOD_3_POWER_TICK, EnumConfigKey.EC_BLOOD_3_PARAM, 3),
    BM_CRYSTAL_I(EnumConfigKey.BM_CRYSTAL_1_POWER_TICK, EnumConfigKey.BM_CRYSTAL_1_PARAM, 1),
    BM_CRYSTAL_II(EnumConfigKey.BM_CRYSTAL_2_POWER_TICK, EnumConfigKey.BM_CRYSTAL_2_PARAM, 2),
    BM_CRYSTAL_III(EnumConfigKey.BM_CRYSTAL_3_POWER_TICK, EnumConfigKey.BM_CRYSTAL_3_PARAM, 3);

    EnumSpawnerUpgrade() { }

    private EnumConfigKey power;
    private EnumConfigKey param1;
    private EnumConfigKey param2;
    private int tier;
    EnumSpawnerUpgrade(EnumConfigKey power, EnumConfigKey param, int tier) {

        this.power = power;
        this.param1 = param;
        this.param2 = param;
        this.tier = tier;
    }

    EnumSpawnerUpgrade(EnumConfigKey power, EnumConfigKey param, EnumConfigKey param2, int tier) {

        this.power = power;
        this.param1 = param;
        this.param2 = param2;
        this.tier = tier;
    }

    public int getMetadata() {

        return this.ordinal();
    }

    public static EnumSpawnerUpgrade getFromMetadata(int meta) {

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

    public int getTier() {

        return this.tier;
    }

    public static EnumSpawnerUpgrade getFromVariant(EnumVariantUpgrade e) {

        return values()[e.ordinal()];
    }

    public static EnumSpawnerUpgrade getFromVariant(EnumVariantUpgradeB e) {

        return values()[e.ordinal() + EFFICIENCY_I.ordinal()];
    }
}
