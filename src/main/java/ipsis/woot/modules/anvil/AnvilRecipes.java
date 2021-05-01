package ipsis.woot.modules.anvil;

import ipsis.woot.crafting.AnvilRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nonnull;

public class AnvilRecipes {

    public static void load(@Nonnull RecipeManager manager) {
        // Setup the valid items for slot 0
        AnvilRecipe.clearValidInputs();
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof AnvilRecipe) {
                AnvilRecipe dRecipe = (AnvilRecipe) recipe;
                Ingredient ingredient = dRecipe.getBaseIngredient();
                for (ItemStack itemStack :  ingredient.getItems())
                    AnvilRecipe.addValidInput(itemStack);
            }
        }
    }
}
