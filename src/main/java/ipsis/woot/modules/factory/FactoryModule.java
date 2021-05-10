package ipsis.woot.modules.factory;

import ipsis.woot.Woot;
import ipsis.woot.datagen.Languages;
import ipsis.woot.modules.Module;
import ipsis.woot.modules.factory.blocks.*;
import ipsis.woot.modules.factory.items.InternItem;
import ipsis.woot.setup.Config;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.setup.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class FactoryModule implements Module {

    public static final String LAYOUT_ID = "layout";
    public static final String HEART_ID = "heart";
    public static final String IMPORTER_ID = "importer";
    public static final String EXPORTER_ID = "exporter";
    public static final String BASE_1_ID = "base_1";
    public static final String BASE_2_ID = "base_2";
    public static final String BASE_3_ID = "base_3";
    public static final String BASE_GLASS_ID = "base_glass";
    public static final String CORE_1A_ID = "core_1a";
    public static final String CORE_1B_ID = "core_1b";
    public static final String CORE_2A_ID = "core_2a";
    public static final String CORE_2B_ID = "core_2b";
    public static final String CORE_3A_ID = "core_3a";
    public static final String CORE_3B_ID = "core_3b";
    public static final String CORE_4A_ID = "core_4a";
    public static final String CORE_4B_ID = "core_4b";
    public static final String CORE_5A_ID = "core_5a";
    public static final String CORE_5B_ID = "core_5b";

    public static final String INTERN_ID = "intern";

    public static final RegistryObject<Block> LAYOUT = Registration.BLOCKS.register(LAYOUT_ID,
            () -> new LayoutBlock().addSimpleTooltip(LAYOUT_ID).addSneakTooltip("info.woot.sneak.0." + LAYOUT_ID).addSneakTooltip("info.woot.sneak.1." + LAYOUT_ID));
    public static final RegistryObject<Item> LAYOUT_ITEM = Registration.ITEMS.register(LAYOUT_ID, () -> new BlockItem(LAYOUT.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<TileEntityType<LayoutTileEntity>> LAYOUT_TE = Registration.TILES.register(LAYOUT_ID, () -> TileEntityType.Builder.of(() -> new LayoutTileEntity(), LAYOUT.get()).build(null));

    public static final RegistryObject<Block> HEART = Registration.BLOCKS.register(HEART_ID, HeartBlock::new);
    public static final RegistryObject<Item> HEART_ITEM = Registration.ITEMS.register(HEART_ID, () -> new BlockItem(HEART.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> IMPORTER = Registration.BLOCKS.register(IMPORTER_ID, () -> new FactoryBlock(ComponentType.IMPORTER));
    public static final RegistryObject<Item> IMPORTER_ITEM = Registration.ITEMS.register(IMPORTER_ID, () -> new BlockItem(IMPORTER.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> EXPORTER = Registration.BLOCKS.register(EXPORTER_ID, () -> new FactoryBlock(ComponentType.EXPORTER));
    public static final RegistryObject<Item> EXPORTER_ITEM = Registration.ITEMS.register(EXPORTER_ID, () -> new BlockItem(EXPORTER.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> BASE_1 = Registration.BLOCKS.register(BASE_1_ID, () -> new FactoryBlock(ComponentType.BASE_1));
    public static final RegistryObject<Item> BASE_1_ITEM = Registration.ITEMS.register(BASE_1_ID, () -> new BlockItem(BASE_1.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> BASE_2 = Registration.BLOCKS.register(BASE_2_ID, () -> new FactoryBlock(ComponentType.BASE_2));
    public static final RegistryObject<Item> BASE_2_ITEM = Registration.ITEMS.register(BASE_2_ID, () -> new BlockItem(BASE_2.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> BASE_3 = Registration.BLOCKS.register(BASE_3_ID, () -> new FactoryBlock(ComponentType.BASE_3));
    public static final RegistryObject<Item> BASE_3_ITEM = Registration.ITEMS.register(BASE_3_ID, () -> new BlockItem(BASE_3.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> BASE_GLASS = Registration.BLOCKS.register(BASE_GLASS_ID, () -> new FactoryBlockGlass());
    public static final RegistryObject<Item> BASE_GLASS_ITEM = Registration.ITEMS.register(BASE_GLASS_ID, () -> new BlockItem(BASE_GLASS.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_1A = Registration.BLOCKS.register(CORE_1A_ID, () -> new FactoryBlock(ComponentType.CORE_1A));
    public static final RegistryObject<Item> CORE_1A_ITEM = Registration.ITEMS.register(CORE_1A_ID, () -> new BlockItem(CORE_1A.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> CORE_1B = Registration.BLOCKS.register(CORE_1B_ID, () -> new FactoryBlock(ComponentType.CORE_1B, AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(3.5F).lightLevel((i) -> { return 15; })));
    public static final RegistryObject<Item> CORE_1B_ITEM = Registration.ITEMS.register(CORE_1B_ID, () -> new BlockItem(CORE_1B.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_2A = Registration.BLOCKS.register(CORE_2A_ID, () -> new FactoryBlock(ComponentType.CORE_2A));
    public static final RegistryObject<Item> CORE_2A_ITEM = Registration.ITEMS.register(CORE_2A_ID, () -> new BlockItem(CORE_2A.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> CORE_2B = Registration.BLOCKS.register(CORE_2B_ID, () -> new FactoryBlock(ComponentType.CORE_2B, AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(3.5F).lightLevel((i) -> { return 15; })));
    public static final RegistryObject<Item> CORE_2B_ITEM = Registration.ITEMS.register(CORE_2B_ID, () -> new BlockItem(CORE_2B.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_3A = Registration.BLOCKS.register(CORE_3A_ID, () -> new FactoryBlock(ComponentType.CORE_3A));
    public static final RegistryObject<Item> CORE_3A_ITEM = Registration.ITEMS.register(CORE_3A_ID, () -> new BlockItem(CORE_3A.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> CORE_3B = Registration.BLOCKS.register(CORE_3B_ID, () -> new FactoryBlock(ComponentType.CORE_3B, AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(3.5F).lightLevel((i) -> { return 15; })));
    public static final RegistryObject<Item> CORE_3B_ITEM = Registration.ITEMS.register(CORE_3B_ID, () -> new BlockItem(CORE_3B.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_4A = Registration.BLOCKS.register(CORE_4A_ID, () -> new FactoryBlock(ComponentType.CORE_4A));
    public static final RegistryObject<Item> CORE_4A_ITEM = Registration.ITEMS.register(CORE_4A_ID, () -> new BlockItem(CORE_4A.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> CORE_4B = Registration.BLOCKS.register(CORE_4B_ID, () -> new FactoryBlock(ComponentType.CORE_4B, AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(3.5F).lightLevel((i) -> { return 15; })));
    public static final RegistryObject<Item> CORE_4B_ITEM = Registration.ITEMS.register(CORE_4B_ID, () -> new BlockItem(CORE_4B.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Block> CORE_5A = Registration.BLOCKS.register(CORE_5A_ID, () -> new FactoryBlock(ComponentType.CORE_5A));
    public static final RegistryObject<Item> CORE_5A_ITEM = Registration.ITEMS.register(CORE_5A_ID, () -> new BlockItem(CORE_5A.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<Block> CORE_5B = Registration.BLOCKS.register(CORE_5B_ID, () -> new FactoryBlock(ComponentType.CORE_5B, AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_PINK).sound(SoundType.STONE).strength(3.5F).lightLevel((i) -> { return 15; })));
    public static final RegistryObject<Item> CORE_5B_ITEM = Registration.ITEMS.register(CORE_5B_ID, () -> new BlockItem(CORE_5B.get(), ModSetup.createStandardProperties()));

    public static final RegistryObject<Item> INTERN_ITEM = Registration.ITEMS.register(INTERN_ID, () -> new InternItem().addSimpleTooltip(INTERN_ID).addSimpleSneakTooltip(INTERN_ID));


    // Most of the blocks in the factory have the same set of properties
    public static AbstractBlock.Properties getDefaultBlockProperties() {
        return AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.5F);
    }

    /**
     * Module interface
     */

    @Override
    public void initConfig() {
        FactoryConfig.setup(Config.SERVER_BUILDER, Config.CLIENT_BUILDER);
    }

    @Override
    public void runRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(LAYOUT.get())
                .pattern("grg")
                .pattern("ytb")
                .pattern("gwg")
                .define('g', Tags.Items.GLASS) .define('r', Tags.Items.DYES_RED)
                .define('y', Tags.Items.DYES_YELLOW) .define('b', Tags.Items.DYES_BLACK)
                .define('w', Tags.Items.DYES_WHITE) .define('t', Blocks.GLOWSTONE)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(INTERN_ITEM.get())
                .pattern(" si")
                .pattern(" ws")
                .pattern("w  ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.DUSTS_REDSTONE)
                .define('w', Items.STICK)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);
    }

    @Override
    public void addTranslations(Languages languages) {

        languages.add(LAYOUT.get(), "Layout Guide");
        languages.add(HEART.get(), "Heart");
        languages.add(IMPORTER.get(), "Importer");
        languages.add(EXPORTER.get(), "Exporter");
        languages.add(BASE_1.get(), "Casing 1");
        languages.add(BASE_2.get(), "Casing 2");
        languages.add(BASE_3.get(), "Casing 3");
        languages.add(BASE_GLASS.get(), "Factory Glass");
        languages.add(CORE_1A.get(), "Core 1A");
        languages.add(CORE_1B.get(), "Core 1B");
        languages.add(CORE_2A.get(), "Core 2A");
        languages.add(CORE_2B.get(), "Core 2B");
        languages.add(CORE_3A.get(), "Core 3A");
        languages.add(CORE_3B.get(), "Core 3B");
        languages.add(CORE_4A.get(), "Core 4A");
        languages.add(CORE_4B.get(), "Core 4B");
        languages.add(CORE_5A.get(), "Core 5A");
        languages.add(CORE_5B.get(), "Core 5B");

        languages.add(INTERN_ITEM.get(), "Intern");

        languages.add("info.woot." + LAYOUT_ID, "Shows layout of factory");
        languages.add("info.woot.sneak.0." + LAYOUT_ID, "Right click to change factory tiers");
        languages.add("info.woot.sneak.1." + LAYOUT_ID, "Sneak right click to change displayed y level");

        languages.add("info.woot." + INTERN_ID, "Use on the Heart to build, destroy and validate");
        languages.add("info.woot.sneak." + INTERN_ID, "Sneak right click to change modes");

        languages.add("info.woot." + INTERN_ID + ".modes.build_tier_1", "Build Tier I factory");
        languages.add("info.woot." + INTERN_ID + ".modes.build_tier_2", "Build Tier II factory");
        languages.add("info.woot." + INTERN_ID + ".modes.build_tier_3", "Build Tier III factory");
        languages.add("info.woot." + INTERN_ID + ".modes.build_tier_4", "Build Tier IV factory");
        languages.add("info.woot." + INTERN_ID + ".modes.build_tier_5", "Build Tier V factory");
        languages.add("info.woot." + INTERN_ID + ".modes.validate_tier_1", "Validate Tier I factory");
        languages.add("info.woot." + INTERN_ID + ".modes.validate_tier_2", "Validate Tier II factory");
        languages.add("info.woot." + INTERN_ID + ".modes.validate_tier_3", "Validate Tier III factory");
        languages.add("info.woot." + INTERN_ID + ".modes.validate_tier_4", "Validate Tier IV factory");
        languages.add("info.woot." + INTERN_ID + ".modes.validate_tier_5", "Validate Tier V factory");
    }
}