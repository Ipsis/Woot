package ipsis.woot.modules.generator;

import ipsis.woot.crafting.ConatusGeneratorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class ConatusGeneratorRecipes {

    private static ArrayList<ConatusGeneratorRecipe> recipes = new ArrayList<>();

    public static void load(@Nonnull RecipeManager manager) {

        ConatusGeneratorRecipe.clearValidInputs();
        ConatusGeneratorRecipe.clearValidCatalysts();
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof ConatusGeneratorRecipe) {
                ConatusGeneratorRecipe dRecipe = (ConatusGeneratorRecipe)recipe;
                for (ItemStack itemStack : dRecipe.getCatalyst().getMatchingStacks())
                    ConatusGeneratorRecipe.addValidCatalyst(itemStack);

                ConatusGeneratorRecipe.addValidInput(dRecipe.getInputFluid());
            }
        }
    }
}
