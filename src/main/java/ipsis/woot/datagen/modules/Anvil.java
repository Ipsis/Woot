package ipsis.woot.datagen.modules;

import ipsis.woot.crafting.AnvilRecipeBuilder;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.generic.GenericSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Anvil {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(AnvilSetup.ANVIL_BLOCK.get())
                .pattern("iii")
                .pattern(" a ")
                .pattern("ooo")
                .define('i', GenericSetup.SI_INGOT_ITEM.get())
                .define('a', Blocks.ANVIL)
                .define('o', Tags.Items.OBSIDIAN)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(AnvilSetup.HAMMER_ITEM.get())
                .pattern(" si")
                .pattern(" ws")
                .pattern("w  ")
                .define('i', GenericSetup.SI_INGOT_ITEM.get())
                .define('s', Tags.Items.STRING)
                .define('w', Items.STICK)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);

        AnvilRecipeBuilder.anvilRecipe(
                AnvilSetup.PLATE_DIE_ITEM.get(), 1,
                Ingredient.of(Items.STONE_SLAB))
                .addIngredient(Ingredient.of(Blocks.OBSIDIAN))
                .build(consumer, "plate_die");

        AnvilRecipeBuilder.anvilRecipe(
                AnvilSetup.SHARD_DIE_ITEM.get(), 1,
                Ingredient.of(Items.QUARTZ))
                .addIngredient(Ingredient.of(Blocks.OBSIDIAN))
                .build(consumer, "shard_die");

        AnvilRecipeBuilder.anvilRecipe(
                AnvilSetup.DYE_DIE_ITEM.get(), 1,
                Ingredient.of(Items.GUNPOWDER))
                .addIngredient(Ingredient.of(Blocks.OBSIDIAN))
                .build(consumer, "dye_die");

        AnvilRecipeBuilder.anvilRecipe(
                FactorySetup.CONTROLLER_BLOCK.get(), 1,
                Ingredient.of(FactorySetup.MOB_SHARD_ITEM.get()))
                .addIngredient(Ingredient.of(GenericSetup.PRISM_ITEM.get()))
                .addIngredient(Ingredient.of(GenericSetup.SI_PLATE_ITEM.get()))
                .build(consumer, "controller");
    }
}
