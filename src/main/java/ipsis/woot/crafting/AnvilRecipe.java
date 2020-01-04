package ipsis.woot.crafting;

import ipsis.woot.mod.ModItems;
import ipsis.woot.modules.factory.items.MobShardItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnvilRecipe {

    public static ArrayList<AnvilRecipe> recipeList = new ArrayList<>();
    public static void addRecipe(ItemStack baseItem, boolean preserveBase, ItemStack outputItem, ItemStack... ingredients) {
        recipeList.add(new AnvilRecipe( baseItem, preserveBase, outputItem, ingredients));
    }

    public static @Nullable AnvilRecipe findRecipe(ItemStack baseItem) {
        if (baseItem == null || baseItem.isEmpty())
            return null;

        if (baseItem.getItem() == ModItems.MOB_SHARD_ITEM) {
            if (!MobShardItem.isFullyProgrammed(baseItem))
                return null;
        }

        for (AnvilRecipe r : recipeList) {
            if (r.baseItem.getItem() == baseItem.getItem())
                return r;
        }

        return null;
    }

    private AnvilRecipe() {}
    public ItemStack baseItem;
    public boolean preserveBase;
    public ItemStack outputItem;
    private List<ItemStack> ingredients = new ArrayList<>();

    public AnvilRecipe(ItemStack baseItem, boolean preserveBase, ItemStack outputItem, ItemStack... ingredients) {
        this.baseItem = baseItem.copy();
        this.preserveBase = preserveBase;
        this.outputItem = outputItem.copy();

        for (ItemStack ingredient : ingredients) {
            if (this.ingredients.size() <= 4)
                this.ingredients.add(ingredient.copy());
        }
    }

    public List<ItemStack> getIngredients() { return Collections.unmodifiableList(ingredients); }

    public boolean checkIngredients(ItemStack[] itemStacks) {

        boolean[] checkedIn = { false, false, false, false };

        for (int i = 0; i < ingredients.size(); i++) {
            ItemStack itemStack = ingredients.get(i);
            if (itemStack.isEmpty())
                continue;

            boolean found = false;
            for (int j = 0; j < itemStacks.length; j++) {
                if (checkedIn[j])
                    continue;

                if (ItemStack.areItemStacksEqual(itemStack, itemStacks[j])) {
                    checkedIn[j] = true;
                    found = true;
                    break;
                }
            }

            if (found == false)
                return false;
        }

        return true;
    }
}
