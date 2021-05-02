package ipsis.woot.modules.factory;

import ipsis.woot.modules.Module;
import ipsis.woot.modules.factory.blocks.FactoryBlock;
import ipsis.woot.modules.factory.blocks.FactoryBlockGlass;
import ipsis.woot.modules.factory.blocks.HeartBlock;
import ipsis.woot.modules.factory.blocks.LayoutBlock;
import ipsis.woot.setup.Config;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.setup.Registration;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class FactoryModule implements Module {

    public static final String LAYOUT_ID = "layout";
    public static final String HEART_ID = "heart";
    public static final String IMPORTER_ID = "importer";
    public static final String EXPORTER_ID = "exporter";
    public static final String BASE_1_ID = "base_1";
    public static final String BASE_2_ID = "base_2";
    public static final String BASE_GLASS_ID = "base_glass";
    public static final String CORE_1A_ID = "core_1a";
    public static final String CORE_1B_ID = "core_1b";

    public static final RegistryObject<Block> LAYOUT = Registration.BLOCKS.register(LAYOUT_ID, LayoutBlock::new);
    public static final RegistryObject<Item> LAYOUT_ITEM = Registration.ITEMS.register(LAYOUT_ID, () -> new BlockItem(LAYOUT.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> HEART = Registration.BLOCKS.register(HEART_ID, HeartBlock::new);
    public static final RegistryObject<Item> HEART_ITEM = Registration.ITEMS.register(HEART_ID, () -> new BlockItem(HEART.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> IMPORTER = Registration.BLOCKS.register("importer", () -> new FactoryBlock(ComponentType.IMPORTER));
    public static final RegistryObject<Item> IMPORTER_ITEM = Registration.ITEMS.register("importer", () -> new BlockItem(IMPORTER.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> EXPORTER = Registration.BLOCKS.register("exporter", () -> new FactoryBlock(ComponentType.EXPORTER));
    public static final RegistryObject<Item> EXPORTER_ITEM = Registration.ITEMS.register("exporter", () -> new BlockItem(EXPORTER.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> BASE_1 = Registration.BLOCKS.register("base_1", () -> new FactoryBlock(ComponentType.BASE_1));
    public static final RegistryObject<Item> BASE_1_ITEM = Registration.ITEMS.register("base_1", () -> new BlockItem(BASE_1.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> BASE_2 = Registration.BLOCKS.register("base_2", () -> new FactoryBlock(ComponentType.BASE_2));
    public static final RegistryObject<Item> BASE_2_ITEM = Registration.ITEMS.register("base_2", () -> new BlockItem(BASE_2.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> BASE_GLASS = Registration.BLOCKS.register("base_glass", () -> new FactoryBlockGlass());
    public static final RegistryObject<Item> BASE_GLASS_ITEM = Registration.ITEMS.register("base_glass", () -> new BlockItem(BASE_GLASS.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_1A = Registration.BLOCKS.register("core_1a", () -> new FactoryBlock(ComponentType.CORE_1A));
    public static final RegistryObject<Item> CORE_1A_ITEM = Registration.ITEMS.register("core_1a", () -> new BlockItem(CORE_1A.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_1B = Registration.BLOCKS.register("core_1b", () -> new FactoryBlock(ComponentType.CORE_1B,
            AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(3.5F).lightLevel((i) -> { return 15; })));
    public static final RegistryObject<Item> CORE_1B_ITEM = Registration.ITEMS.register("core_1b", () -> new BlockItem(CORE_1B.get(), ModSetup.createStandardProperties()));

    @Override
    public void initConfig() {
        FactoryConfig.setup(Config.SERVER_BUILDER);
    }

    // Most of the blocks in the factory have the same set of properties
    public static AbstractBlock.Properties getDefaultBlockProperties() {
        return AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.5F);
    }
}
