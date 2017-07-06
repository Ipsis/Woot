package ipsis.woot.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AnvilRecipe {

    private ItemStack baseItem = ItemStack.EMPTY.copy();
    private List<ItemStack> items = new ArrayList<>();
    private List<String> oreDictItems = new ArrayList<>();
    private List<ItemStack> outputs = new ArrayList<>();

    public AnvilRecipe setBaseItem(ItemStack baseItem) {

        this.baseItem = baseItem;
        return this;
    }

    public AnvilRecipe addItem(ItemStack itemStack) {

        items.add(itemStack);
        return this;
    }

    public AnvilRecipe addOutput(ItemStack itemStack) {

        outputs.add(itemStack);
        return this;
    }

    public AnvilRecipe addOreDictItem(String name) {

        oreDictItems.add(name);
        return  this;
    }

    public List<ItemStack> getOutputs() {

        List<ItemStack> out = new ArrayList<>();
        for (ItemStack o : outputs)
            out.add(o.copy());
        return out;
    }

    public int getRecipeSize() {

        return items.size() + oreDictItems.size();
    }

    public boolean isBaseItem(@Nonnull ItemStack itemStack) {

        if (baseItem.isEmpty())
            return true;

        // there is a base item but none passed in
        if (itemStack.isEmpty())
            return false;

        return AnvilRecipeMatcher.isItemStackMatch(baseItem, itemStack);
    }

    public boolean isValidInput(@Nonnull ItemStack itemStack) {

        for (ItemStack c : items) {
            if (AnvilRecipeMatcher.isItemStackMatch(c, itemStack))
                return true;
        }

        // TODO oreDict

        return false;
    }
}
