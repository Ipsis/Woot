package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.crafting.FactoryRecipe;
import ipsis.woot.crafting.FactoryRecipeBuilder;
import ipsis.woot.mod.ModTags;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.generic.items.GenericItem;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.util.FakeMob;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class Factory {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        /**
         * Basic factory blocks
         */
        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_A_BLOCK.get(), 4)
                .pattern(" s ")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_B_BLOCK.get(), 4)
                .pattern(" s ")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.BROWN_DYE_PLATE_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_C_BLOCK.get(), 4)
                .pattern("zs ")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get())
                .define('z', GenericSetup.T1_SHARD_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_D_BLOCK.get(), 2)
                .pattern("zsz")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.GREEN_DYE_PLATE_ITEM.get())
                .define('z', GenericSetup.T2_SHARD_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_E_BLOCK.get(), 2)
                .pattern("zsz")
                .pattern("sps")
                .pattern("zsz")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.BLUE_DYE_PLATE_ITEM.get())
                .define('z', GenericSetup.T3_SHARD_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_UPGRADE_BLOCK.get())
                .pattern(" s ")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.PURPLE_DYE_PLATE_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get())
                .pattern(" s ")
                .pattern("s s")
                .pattern("psx")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get())
                .define('x', InfuserSetup.PINK_DYE_PLATE_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get())
                .pattern(" s ")
                .pattern("s s")
                .pattern("psx")
                .define('s', Tags.Items.STONE)
                .define('p', InfuserSetup.BLUE_DYE_PLATE_ITEM.get())
                .define('x', InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.FACTORY_CONNECT_BLOCK.get())
                .requires(ModTags.Items.FACTORY_BLOCK)
                .requires(Items.REDSTONE)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CAP_A_BLOCK.get())
                .requires(ModTags.Items.FACTORY_BLOCK)
                .requires(Items.IRON_NUGGET)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CAP_B_BLOCK.get())
                .requires(ModTags.Items.FACTORY_BLOCK)
                .requires(Items.GOLD_INGOT)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CAP_C_BLOCK.get())
                .requires(ModTags.Items.FACTORY_BLOCK)
                .requires(Items.DIAMOND)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CAP_D_BLOCK.get())
                .requires(ModTags.Items.FACTORY_BLOCK)
                .requires(Items.EMERALD)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        /**
         * Cells
         */
        ShapedRecipeBuilder.shaped(FactorySetup.CELL_1_BLOCK.get())
                .pattern("igi")
                .pattern("gcg")
                .pattern("igi")
                .define('g', Tags.Items.GLASS)
                .define('i', Items.IRON_BARS)
                .define('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CELL_2_BLOCK.get())
                .requires(FactorySetup.CELL_1_BLOCK.get())
                .requires(GenericSetup.ENCH_PLATE_1.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CELL_3_BLOCK.get())
                .requires(FactorySetup.CELL_2_BLOCK.get())
                .requires(GenericSetup.ENCH_PLATE_2.get())
                .requires(GenericSetup.ENCH_PLATE_2.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(FactorySetup.CELL_4_BLOCK.get())
                .requires(FactorySetup.CELL_3_BLOCK.get())
                .requires(GenericSetup.ENCH_PLATE_3.get())
                .requires(GenericSetup.ENCH_PLATE_3.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        /**
         * Perks
         */
        class RecipePerk {
            RegistryObject<PerkItem> perkItem1;
            RegistryObject<PerkItem> perkItem2;
            RegistryObject<PerkItem> perkItem3;
            IItemProvider i1;
            IItemProvider i2;
            public RecipePerk(RegistryObject<PerkItem> perkItem1, RegistryObject<PerkItem> perkItem2,
                    RegistryObject<PerkItem> perkItem3, IItemProvider i1, IItemProvider i2) {
                this.perkItem1 = perkItem1;
                this.perkItem2 = perkItem2;
                this.perkItem3 = perkItem3;
                this.i1 = i1;
                this.i2 = i2;
            }

        }
        RecipePerk[] recipePerks = {
                new RecipePerk(
                        FactorySetup.EFFICIENCY_1_ITEM,
                        FactorySetup.EFFICIENCY_2_ITEM,
                        FactorySetup.EFFICIENCY_3_ITEM,
                        Blocks.REDSTONE_BLOCK,
                        Blocks.BLAST_FURNACE),
                new RecipePerk(
                        FactorySetup.LOOTING_1_ITEM,
                        FactorySetup.LOOTING_2_ITEM,
                        FactorySetup.LOOTING_3_ITEM,
                        Items.DIAMOND_SWORD,
                        Blocks.LAPIS_BLOCK),
                new RecipePerk(
                        FactorySetup.MASS_1_ITEM,
                        FactorySetup.MASS_2_ITEM,
                        FactorySetup.MASS_3_ITEM,
                        Items.MAGMA_CREAM,
                        Blocks.NETHER_WART),
                new RecipePerk(
                        FactorySetup.RATE_1_ITEM,
                        FactorySetup.RATE_2_ITEM,
                        FactorySetup.RATE_3_ITEM,
                        Blocks.REDSTONE_BLOCK,
                        Blocks.REDSTONE_BLOCK),
                new RecipePerk(
                        FactorySetup.XP_1_ITEM,
                        FactorySetup.XP_2_ITEM,
                        FactorySetup.XP_3_ITEM,
                        Blocks.ENCHANTING_TABLE,
                        Items.LAPIS_LAZULI),
                new RecipePerk(
                        FactorySetup.TIER_SHARD_1_ITEM,
                        FactorySetup.TIER_SHARD_2_ITEM,
                        FactorySetup.TIER_SHARD_3_ITEM,
                        Items.GOLDEN_CARROT,
                        Items.NETHER_WART),
                new RecipePerk(
                        FactorySetup.HEADLESS_1_ITEM,
                        FactorySetup.HEADLESS_2_ITEM,
                        FactorySetup.HEADLESS_3_ITEM,
                        Items.ZOMBIE_HEAD,
                        Items.SKELETON_SKULL)
        };

        for (RecipePerk recipePerk : recipePerks) {
            ShapedRecipeBuilder.shaped(recipePerk.perkItem1.get())
                    .pattern(" 1 ")
                    .pattern(" e ")
                    .pattern(" 2 ")
                    .define('e', GenericSetup.ENCH_PLATE_1.get())
                    .define('1', recipePerk.i1)
                    .define('2', recipePerk.i2)
                    .group(Woot.MODID)
                    .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                    .save(consumer);
            ShapedRecipeBuilder.shaped(recipePerk.perkItem2.get())
                    .pattern(" 1 ")
                    .pattern("e e")
                    .pattern(" 2 ")
                    .define('e', GenericSetup.ENCH_PLATE_2.get())
                    .define('1', recipePerk.i1)
                    .define('2', recipePerk.i2)
                    .group(Woot.MODID)
                    .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                    .save(consumer);
            ShapedRecipeBuilder.shaped(recipePerk.perkItem3.get())
                    .pattern(" 1 ")
                    .pattern("eee")
                    .pattern(" 2 ")
                    .define('e', GenericSetup.ENCH_PLATE_3.get())
                    .define('1', recipePerk.i1)
                    .define('2', recipePerk.i2)
                    .group(Woot.MODID)
                    .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                    .save(consumer);
        }

        /**
         * Other
         */
        ShapedRecipeBuilder.shaped(FactorySetup.HEART_BLOCK.get())
                .pattern(" s ")
                .pattern("scs")
                .pattern(" s ")
                .define('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .define('s', Tags.Items.HEADS)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.IMPORT_BLOCK.get())
                .pattern(" h ")
                .pattern(" c ")
                .pattern("   ")
                .define('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .define('h', Blocks.HOPPER)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.EXPORT_BLOCK.get())
                .pattern("   ")
                .pattern(" c ")
                .pattern(" h ")
                .define('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .define('h', Blocks.HOPPER)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(FactorySetup.XP_SHARD_ITEM.get())
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .define('s', FactorySetup.XP_SPLINTER_ITEM.get())
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        FactoryRecipeBuilder.factoryRecipe(new FakeMob("minecraft:ender_dragon"))
                .addIngredient(new ItemStack(Items.END_CRYSTAL, 4))
                .addDrop(new FactoryRecipe.Drop(
                        new ItemStack(Blocks.DRAGON_EGG),
                        1, 1, 1, 1,
                        100.0F, 100.0F, 100.0F, 100.0F))
                .addDrop(new FactoryRecipe.Drop(
                        new ItemStack(Items.DRAGON_BREATH),
                        2, 4, 6, 8,
                        100.0F, 100.0F, 100.0F, 100.0F))
                .build(consumer, "ender_dragon");

        FactoryRecipeBuilder.factoryRecipe(new FakeMob("minecraft:wither"))
                .addIngredient(new ItemStack(Items.WITHER_SKELETON_SKULL, 3))
                .addIngredient(new ItemStack(Blocks.SOUL_SAND, 4))
                .build(consumer, "wither");

        float chance = 100.0F/16.0F;
        FactoryRecipeBuilder.factoryRecipe(new FakeMob("minecraft:sheep"))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.BLACK_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.BLUE_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.BROWN_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.CYAN_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.GRAY_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.GREEN_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.LIGHT_BLUE_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.LIGHT_GRAY_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.LIME_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.MAGENTA_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.ORANGE_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.PINK_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.PURPLE_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.RED_WOOL), 1, 1, 1, 1,  chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.WHITE_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .addDrop(new FactoryRecipe.Drop(new ItemStack(Items.YELLOW_WOOL), 1, 1, 1, 1, chance, chance, chance, chance))
                .build(consumer, "sheep");
    }
}
