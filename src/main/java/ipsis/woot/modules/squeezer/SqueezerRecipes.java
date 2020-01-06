package ipsis.woot.modules.squeezer;

import ipsis.woot.crafting.DyeSqueezerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nonnull;

public class SqueezerRecipes {

    public static void load(@Nonnull RecipeManager manager) {

        // Setup the valid items for slot 0
        DyeSqueezerRecipe.clearValidInputs();
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof DyeSqueezerRecipe) {
                DyeSqueezerRecipe dRecipe = (DyeSqueezerRecipe)recipe;
                Ingredient ingredient = dRecipe.getIngredient();
                for (ItemStack itemStack :  ingredient.getMatchingStacks())
                    DyeSqueezerRecipe.addValidInput(itemStack);
            }
        }
    }
}
