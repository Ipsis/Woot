package ipsis.woot.crafting;

import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.item.ItemStack;

public class SqueezerRecipe {
    private ItemStack input = ItemStack.EMPTY;
    private DyeMakeup dyeMakeup;

    public SqueezerRecipe(ItemStack itemStack, DyeMakeup dyeMakeup) {
        this.input = itemStack;
        this.dyeMakeup = dyeMakeup;
    }

    public ItemStack getInput() { return this.input; }
    public DyeMakeup getDyeMakeup() { return this.dyeMakeup; }
    public boolean isInput(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty())
            return false;

        // This should never be true
        if (input.isEmpty())
            return false;

        if (input.isItemEqual(itemStack))
            return true;

        return false;
    }
}
