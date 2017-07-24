package ipsis.woot.plugins.jei.anvil;

import ipsis.Woot;
import ipsis.woot.crafting.IAnvilRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class StygianAnvilRecipeMaker {

    @Nonnull
    public static List<StygianAnvilRecipeJEI> getRecipes() {

        List<IAnvilRecipe> recipeList = Woot.anvilManager.getRecipes();
        ArrayList<StygianAnvilRecipeJEI> recipes = new ArrayList<>();

        for (IAnvilRecipe recipe : recipeList)
            recipes.add(new StygianAnvilRecipeJEI(recipe));

        return recipes;
    }
}
