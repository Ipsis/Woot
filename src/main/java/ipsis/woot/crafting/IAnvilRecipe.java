package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IAnvilRecipe {

    boolean shouldPreserveBase();
    ItemStack getCopyOutput();
    ItemStack getBaseItem();
    void addIngredient(ItemStack itemStack);
    boolean isMatchingBase(ItemStack itemStack);
    int getRecipeSize();
    boolean isIngredient(ItemStack itemStack);
    boolean isOutput(ItemStack itemStack);
    List<ItemStack> getInputs();}
