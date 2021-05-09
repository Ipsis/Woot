package ipsis.woot.modules.factory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.HashMap;

public enum ComponentType {

    HEART,
    BASE_1,
    BASE_2,
    BASE_3,
    BASE_GLASS,
    CORE_1A,
    CORE_1B,
    CORE_2A,
    CORE_2B,
    CORE_3A,
    CORE_3B,
    CORE_4A,
    CORE_4B,
    CORE_5A,
    CORE_5B,
    IMPORTER,
    EXPORTER;

    private static HashMap<ComponentType, Block> blockHashMap = new HashMap<>();
    public static void registerBlock(ComponentType componentType, Block b) {
        blockHashMap.put(componentType, b);
    }
    public static BlockState getDefaultBlockState(ComponentType componentType) {
        Block b = blockHashMap.get(componentType);
        if (b == null)
            return Blocks.AIR.defaultBlockState();
        return b.defaultBlockState();
    }

    public static Block getBlock(ComponentType componentType) {
        Block b = blockHashMap.get(componentType);
        if (b == null)
            return Blocks.AIR;
        return b;
    }

    public static void setup() {
        registerBlock(HEART, FactoryModule.HEART.get());
        registerBlock(BASE_1, FactoryModule.BASE_1.get());
        registerBlock(BASE_2, FactoryModule.BASE_2.get());
        registerBlock(BASE_3, FactoryModule.BASE_3.get());
        registerBlock(BASE_GLASS, FactoryModule.BASE_GLASS.get());
        registerBlock(CORE_1A, FactoryModule.CORE_1A.get());
        registerBlock(CORE_1B, FactoryModule.CORE_1B.get());
        registerBlock(CORE_2A, FactoryModule.CORE_2A.get());
        registerBlock(CORE_2B, FactoryModule.CORE_2B.get());
        registerBlock(CORE_3A, FactoryModule.CORE_3A.get());
        registerBlock(CORE_3B, FactoryModule.CORE_3B.get());
        registerBlock(CORE_4A, FactoryModule.CORE_4A.get());
        registerBlock(CORE_4B, FactoryModule.CORE_4B.get());
        registerBlock(CORE_5A, FactoryModule.CORE_5A.get());
        registerBlock(CORE_5B, FactoryModule.CORE_5B.get());
        registerBlock(IMPORTER, FactoryModule.IMPORTER.get());
        registerBlock(EXPORTER, FactoryModule.EXPORTER.get());
    }
}
