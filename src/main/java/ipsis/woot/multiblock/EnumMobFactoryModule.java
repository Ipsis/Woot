package ipsis.woot.multiblock;

import ipsis.woot.util.WootColor;
import net.minecraft.util.IStringSerializable;

public enum EnumMobFactoryModule implements IStringSerializable {

    BLOCK_1("block_1", WootColor.RED, 'g'),
    BLOCK_2("block_2", WootColor.GRAY, 'r'),
    BLOCK_3("block_3", WootColor.ORANGE, 'o'),
    BLOCK_4("block_4", WootColor.GREEN, 'h'),
    BLOCK_5("block_5", WootColor.WHITE, 'w'),
    BLOCK_UPGRADE("block_upgrade", WootColor.PURPLE, 'p'),
    CAP_I("tier_i_cap", WootColor.LIGHTGRAY, '1'),
    CAP_II("tier_ii_cap", WootColor.YELLOW, '2'),
    CAP_III("tier_iii_cap", WootColor.CYAN, '3'),
    CAP_IV("tier_iv_cap", WootColor.LIME, '4');

    public static EnumMobFactoryModule[] VALUES = { BLOCK_1, BLOCK_2, BLOCK_3, BLOCK_4, BLOCK_5, BLOCK_UPGRADE, CAP_I, CAP_II, CAP_III, CAP_IV };


    String name;
    WootColor color;
    char character;
    EnumMobFactoryModule(String name, WootColor color, char character) {
        this.name = name;
        this.color = color;
        this.character = character;
    }

    public WootColor getColor() {

        return this.color;
    }

    public char getPatternChar() {

       return this.character;
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

    public static EnumMobFactoryModule byChar(char character) {

        for (EnumMobFactoryModule m : values()) {
            if (m.character == character)
                return m;
        }

        return EnumMobFactoryModule.BLOCK_1;
    }

    public static boolean isValidChar(char character) {

        for (EnumMobFactoryModule m : values()) {
            if (m.character == character)
                return true;
        }

        return false;
    }
}
