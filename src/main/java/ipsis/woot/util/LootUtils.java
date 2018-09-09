package ipsis.woot.util;

import net.minecraft.item.ItemStack;

public class LootUtils {

    public static boolean isIdenticalForLearning(ItemStack itemStackA, ItemStack itemStackB) {

        return ItemStack.areItemsEqualIgnoreDurability(itemStackA, itemStackB);
    }


}
