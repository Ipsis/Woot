package ipsis.woot.modules.anvil;

import ipsis.woot.mod.ModItems;
import ipsis.woot.shards.MobShardItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnvilCraftingManager {

    static AnvilCraftingManager INSTANCE = new AnvilCraftingManager();
    public static AnvilCraftingManager get() { return INSTANCE; }
    private List<AnvilRecipe> recipes = new ArrayList<>();

    public void addRecipe(ItemStack baseItem, ItemStack outputItem, ItemStack... ingredients) {

        recipes.add(new AnvilRecipe( baseItem, outputItem, ingredients));
    }

    public @Nullable AnvilRecipe getRecipe(ItemStack baseItem) {
        AnvilRecipe recipe = null;

        if (baseItem.getItem() == ModItems.MOB_SHARD_ITEM) {
            if (!MobShardItem.isFullyProgrammed(baseItem))
                return null;
        }

        for (AnvilRecipe r : recipes) {
            if (r.baseItem.getItem() == baseItem.getItem()) {
                recipe = r;
                break;
            }
        }

        return recipe;
    }

    public static class AnvilRecipe {

        private AnvilRecipe() {}
        public ItemStack baseItem;
        public ItemStack outputItem;
        private List<ItemStack> ingredients = new ArrayList<>();

        public AnvilRecipe(ItemStack baseItem, ItemStack outputItem, ItemStack... ingredients) {
            this.baseItem = baseItem.copy();
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
}
