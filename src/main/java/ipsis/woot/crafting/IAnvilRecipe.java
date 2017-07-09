package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

public interface IAnvilRecipe {

    boolean shouldPreserveBase();
    ItemStack getCopyOutput();
    void addIngredient(ItemStack itemStack);
    boolean isMatchingBase(ItemStack itemStack);
    int getRecipeSize();
    boolean isIngredient(ItemStack itemStack);
}
