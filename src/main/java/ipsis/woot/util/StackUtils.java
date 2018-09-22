package ipsis.woot.util;

import net.minecraft.item.ItemStack;

public class StackUtils {

    public static boolean isEqualForLearning(ItemStack itemStackA, ItemStack itemStackB) {

        return ItemStack.areItemsEqualIgnoreDurability(itemStackA, itemStackB);
    }
}
