package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class AnvilRecipeMatcher {

    public static boolean isMatch(IAnvilRecipe recipe, @Nonnull ItemStack baseItem, @Nonnull List<ItemStack> items) {

        if (!recipe.isMatchingBase(baseItem))
            return false;

        int recipeSize = recipe.getRecipeSize();
        if (recipeSize != items.size())
            return false;

        for (ItemStack itemStack : items) {
            if (recipe.isIngredient(itemStack))
                recipeSize--;
        }

        return  recipeSize == 0;
    }

    public static boolean isItemStackMatch(ItemStack a, ItemStack b) {

        return a.isItemEqual(b);
    }
}
