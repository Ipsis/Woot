package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AnvilManager implements IAnvilManager {

    private List<ItemStack> validBaseItems = new ArrayList<>();
    private List<IAnvilRecipe> recipes = new ArrayList<>();

    private void addToBaseItems(ItemStack base) {

        boolean found = false;
        for (ItemStack itemStack : validBaseItems) {

            if (itemStack.isItemEqual(base)) {
                found = true;
                break;
            }
        }

        if (!found)
            validBaseItems.add(base);
    }

    @Override
    public void addRecipe(ItemStack output, ItemStack base, boolean preserveBase, Object ... ingredients) {

        if (ingredients.length == 0)
            return;

        addToBaseItems(base);

        IAnvilRecipe recipe = new AnvilRecipe(output, base, preserveBase);
        for (Object o : ingredients) {
            if (o instanceof ItemStack)
                recipe.addIngredient((ItemStack)o);
        }

        recipes.add(recipe);
    }

    @Nullable
    @Override
    public IAnvilRecipe tryCraft(ItemStack baseItem, @Nonnull List<ItemStack> items) {

        if (!isValidBaseItem(baseItem))
            return null;

        for (IAnvilRecipe recipe : recipes) {
            if (AnvilRecipeMatcher.isMatch(recipe, baseItem, items)) {
                items.clear();
                // TODO only remove the matching items
                return recipe;
            }
        }

        return null;
    }

    @Override
    public boolean isValidBaseItem(ItemStack itemStack) {

        boolean found = false;

        for (ItemStack c : validBaseItems) {
            if (c.isItemEqual(itemStack)) {
                found = true;
                break;
            }
        }

        return found;
    }

    @Override
    public List<IAnvilRecipe> getRecipes() {

        return recipes;
    }

    public IAnvilRecipe getRecipe(ItemStack output) {

        for (IAnvilRecipe recipe : recipes) {
            if (recipe.isOutput(output))
                return recipe;
        }

        return null;
    }
}
