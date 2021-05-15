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
        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_A_BLOCK.get(), 4)
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_B_BLOCK.get(), 4)
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.BROWN_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_C_BLOCK.get(), 4)
                .patternLine("zs ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get())
                .key('z', GenericSetup.T1_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_D_BLOCK.get(), 2)
                .patternLine("zsz")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.GREEN_DYE_PLATE_ITEM.get())
                .key('z', GenericSetup.T2_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_E_BLOCK.get(), 2)
                .patternLine("zsz")
                .patternLine("sps")
                .patternLine("zsz")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.BLUE_DYE_PLATE_ITEM.get())
                .key('z', GenericSetup.T3_SHARD_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_UPGRADE_BLOCK.get())
                .patternLine(" s ")
                .patternLine("sps")
                .patternLine(" s ")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.PURPLE_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get())
                .patternLine(" s ")
                .patternLine("s s")
                .patternLine("psx")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get())
                .key('x', InfuserSetup.PINK_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get())
                .patternLine(" s ")
                .patternLine("s s")
                .patternLine("psx")
                .key('s', Tags.Items.STONE)
                .key('p', InfuserSetup.BLUE_DYE_PLATE_ITEM.get())
                .key('x', InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.FACTORY_CONNECT_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.REDSTONE)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_A_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.IRON_NUGGET)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_B_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.GOLD_INGOT)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_C_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.DIAMOND)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CAP_D_BLOCK.get())
                .addIngredient(ModTags.Items.FACTORY_BLOCK)
                .addIngredient(Items.EMERALD)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        /**
         * Cells
         */
        ShapedRecipeBuilder.shapedRecipe(FactorySetup.CELL_1_BLOCK.get())
                .patternLine("igi")
                .patternLine("gcg")
                .patternLine("igi")
                .key('g', Tags.Items.GLASS)
                .key('i', Items.IRON_BARS)
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CELL_2_BLOCK.get())
                .addIngredient(FactorySetup.CELL_1_BLOCK.get())
                .addIngredient(GenericSetup.ENCH_PLATE_1.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CELL_3_BLOCK.get())
                .addIngredient(FactorySetup.CELL_2_BLOCK.get())
                .addIngredient(GenericSetup.ENCH_PLATE_2.get())
                .addIngredient(GenericSetup.ENCH_PLATE_2.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FactorySetup.CELL_4_BLOCK.get())
                .addIngredient(FactorySetup.CELL_3_BLOCK.get())
                .addIngredient(GenericSetup.ENCH_PLATE_3.get())
                .addIngredient(GenericSetup.ENCH_PLATE_3.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

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
            ShapedRecipeBuilder.shapedRecipe(recipePerk.perkItem1.get())
                    .patternLine(" 1 ")
                    .patternLine(" e ")
                    .patternLine(" 2 ")
                    .key('e', GenericSetup.ENCH_PLATE_1.get())
                    .key('1', recipePerk.i1)
                    .key('2', recipePerk.i2)
                    .setGroup(Woot.MODID)
                    .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                    .build(consumer);
            ShapedRecipeBuilder.shapedRecipe(recipePerk.perkItem2.get())
                    .patternLine(" 1 ")
                    .patternLine("e e")
                    .patternLine(" 2 ")
                    .key('e', GenericSetup.ENCH_PLATE_2.get())
                    .key('1', recipePerk.i1)
                    .key('2', recipePerk.i2)
                    .setGroup(Woot.MODID)
                    .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                    .build(consumer);
            ShapedRecipeBuilder.shapedRecipe(recipePerk.perkItem3.get())
                    .patternLine(" 1 ")
                    .patternLine("eee")
                    .patternLine(" 2 ")
                    .key('e', GenericSetup.ENCH_PLATE_3.get())
                    .key('1', recipePerk.i1)
                    .key('2', recipePerk.i2)
                    .setGroup(Woot.MODID)
                    .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                    .build(consumer);
        }

        /**
         * Other
         */
        ShapedRecipeBuilder.shapedRecipe(FactorySetup.HEART_BLOCK.get())
                .patternLine(" s ")
                .patternLine("scs")
                .patternLine(" s ")
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('s', Tags.Items.HEADS)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.IMPORT_BLOCK.get())
                .patternLine(" h ")
                .patternLine(" c ")
                .patternLine("   ")
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('h', Blocks.HOPPER)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.EXPORT_BLOCK.get())
                .patternLine("   ")
                .patternLine(" c ")
                .patternLine(" h ")
                .key('c', GenericSetup.MACHINE_CASING_ITEM.get())
                .key('h', Blocks.HOPPER)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FactorySetup.XP_SHARD_ITEM.get())
                .patternLine("sss")
                .patternLine("sss")
                .patternLine("sss")
                .key('s', FactorySetup.XP_SPLINTER_ITEM.get())
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        /* Now using modded drops as well
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
                .build(consumer, "ender_dragon"); */

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
