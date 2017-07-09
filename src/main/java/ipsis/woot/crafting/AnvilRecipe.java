package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AnvilRecipe implements IAnvilRecipe {

    private ItemStack base;
    private ItemStack output;
    private List<ItemStack> ingredients = new ArrayList<>();
    private boolean preserveBase;

    public AnvilRecipe(ItemStack output, ItemStack base, boolean preserveBase) {

        this.base = base;
        this.output = output;
        this.preserveBase = preserveBase;
    }

    @Override
    public void addIngredient(ItemStack itemStack) {

        ingredients.add(itemStack);
    }

    @Override
    public boolean shouldPreserveBase() {

        return this.preserveBase;
    }

    @Override
    public ItemStack getCopyOutput() {

        return output.copy();
    }

    @Override
    public boolean isMatchingBase(ItemStack itemStack) {

        if (itemStack.isEmpty())
            return false;

        return base.isItemEqual(itemStack);
    }

    @Override
    public int getRecipeSize() {

        return ingredients.size();
    }

    @Override
    public boolean isIngredient(ItemStack itemStack) {

        if (itemStack.isEmpty())
            return false;

        for (ItemStack r : ingredients)
            if (r.isItemEqual(itemStack))
                return true;

        return false;
    }
}
