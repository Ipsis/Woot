package ipsis.woot.tileentity.multiblock;

import net.minecraft.util.IStringSerializable;

public enum EnumMobFactoryModule implements IStringSerializable {

    BLOCK_1("block_1"),
    BLOCK_2("block_2"),
    BLOCK_3("block_3"),
    BLOCK_4("block_4"),
    BLOCK_5("block_5");

    public static EnumMobFactoryModule[] VALUES = { BLOCK_1, BLOCK_2, BLOCK_3, BLOCK_4, BLOCK_5 };


    String name;
    EnumMobFactoryModule(String name) {
        this.name = name;
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

        if (metadata < 0 || metadata > VALUES.length)
            return BLOCK_1;

        return VALUES[metadata];
    }
}
