package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AnvilManager implements IAnvilManager {

    private List<AnvilRecipe> recipes = new ArrayList<>();


    public void addRecipe(@Nonnull AnvilRecipe recipe) {

        recipes.add(recipe);
    }

    @Nullable
    public List<ItemStack> craft(ItemStack baseItem, @Nonnull List<ItemStack> items) {

        for (AnvilRecipe recipe : recipes) {
            if (AnvilRecipeMatcher.isMatch(recipe, baseItem, items)) {
                return recipe.getOutputs();
            }
        }

        return null;
    }
}
