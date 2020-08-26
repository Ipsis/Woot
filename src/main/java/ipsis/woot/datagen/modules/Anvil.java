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

        ShapedRecipeBuilder.shapedRecipe(AnvilSetup.ANVIL_BLOCK.get())
                .patternLine("iii")
                .patternLine(" a ")
                .patternLine("ooo")
                .key('i', GenericSetup.SI_INGOT_ITEM.get())
                .key('a', Blocks.ANVIL)
                .key('o', Tags.Items.OBSIDIAN)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AnvilSetup.HAMMER_ITEM.get())
                .patternLine(" si")
                .patternLine(" ws")
                .patternLine("w  ")
                .key('i', GenericSetup.SI_INGOT_ITEM.get())
                .key('s', Tags.Items.STRING)
                .key('w', Items.STICK)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        AnvilRecipeBuilder.anvilRecipe(
                AnvilSetup.PLATE_DIE_ITEM.get(), 1,
                Ingredient.fromItems(Items.STONE_SLAB))
                .addIngredient(Ingredient.fromItems(Blocks.OBSIDIAN))
                .build(consumer, "plate_die");

        AnvilRecipeBuilder.anvilRecipe(
                AnvilSetup.SHARD_DIE_ITEM.get(), 1,
                Ingredient.fromItems(Items.QUARTZ))
                .addIngredient(Ingredient.fromItems(Blocks.OBSIDIAN))
                .build(consumer, "shard_die");

        AnvilRecipeBuilder.anvilRecipe(
                AnvilSetup.DYE_DIE_ITEM.get(), 1,
                Ingredient.fromItems(Items.GUNPOWDER))
                .addIngredient(Ingredient.fromItems(Blocks.OBSIDIAN))
                .build(consumer, "dye_die");

        AnvilRecipeBuilder.anvilRecipe(
                FactorySetup.CONTROLLER_BLOCK.get(), 1,
                Ingredient.fromItems(FactorySetup.MOB_SHARD_ITEM.get()))
                .addIngredient(Ingredient.fromItems(GenericSetup.PRISM_ITEM.get()))
                .addIngredient(Ingredient.fromItems(GenericSetup.SI_PLATE_ITEM.get()))
                .build(consumer, "controller");
    }
}
