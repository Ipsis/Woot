package ipsis.woot.tileentity.multiblock;

import ipsis.woot.util.WootColor;
import net.minecraft.util.IStringSerializable;

public enum EnumMobFactoryModule implements IStringSerializable {

    BLOCK_1("block_1", WootColor.GRAY),
    BLOCK_2("block_2", WootColor.RED),
    BLOCK_3("block_3", WootColor.ORANGE),
    BLOCK_4("block_4", WootColor.GREEN),
    BLOCK_5("block_5", WootColor.WHITE),
    CAP_I("tier_i_cap", WootColor.LIGHTGRAY),
    CAP_II("tier_ii_cap", WootColor.YELLOW),
    CAP_III("tier_iii_cap", WootColor.CYAN),
    CAP_IV("tier_iv_cap", WootColor.LIME);

    public static EnumMobFactoryModule[] VALUES = { BLOCK_1, BLOCK_2, BLOCK_3, BLOCK_4, BLOCK_5, CAP_I, CAP_II, CAP_III, CAP_IV };


    String name;
    WootColor color;
    EnumMobFactoryModule(String name, WootColor color) {
        this.name = name;
        this.color = color;
    }

    public WootColor getColor() {

        return this.color;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {

        return this.name;
    }

    public int getMetadata() {
        return this.ordinal();
    }

    public static EnumMobFactoryModule byMetadata(int metadata) {

        if (metadata < 0 || metadata >= VALUES.length)
            return BLOCK_1;

        return VALUES[metadata];
    }
}
