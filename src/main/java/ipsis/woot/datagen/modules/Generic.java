package ipsis.woot.datagen.modules;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.generic.GenericSetup;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Generic {

    public static void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(GenericSetup.SI_DUST_ITEM.get(), 3)
                .patternLine(" i ")
                .patternLine("n n")
                .patternLine(" s ")
                .key('i', Tags.Items.INGOTS_IRON)
                .key('n', Tags.Items.NETHERRACK)
                .key('s', Blocks.SOUL_SAND)
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        CookingRecipeBuilder.smeltingRecipe(
                Ingredient.fromItems(GenericSetup.SI_DUST_ITEM.get()),
                GenericSetup.SI_INGOT_ITEM.get(),
                1.0F, 200)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(GenericSetup.SI_PLATE_ITEM.get())
                .addIngredient(Ingredient.fromItems(GenericSetup.SI_INGOT_ITEM.get()))
                .addIngredient(AnvilSetup.PLATE_DIE_ITEM.get())
                .addIngredient(Ingredient.fromItems(AnvilSetup.HAMMER_ITEM.get()))
                .setGroup(Woot.MODID)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }
}
