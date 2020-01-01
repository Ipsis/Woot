package ipsis.woot.util.helper;


import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nullable;

/**
 * Vanilla has multiple tag schemes for enchanted "items"
 * The ItemStack.isEnchanted only checks for the !book version
 *
 * Enchanted Books -> StoredEnchantments id/(short)lvl
 * Enchanted Item  -> Enchantments id/(short)lvl
 */
public class EnchantmentHelper {

    public static boolean isEnchanted(@Nullable ItemStack itemStack) {

        if (itemStack.getItem() == Items.ENCHANTED_BOOK)
            return !EnchantedBookItem.getEnchantments(itemStack).isEmpty();
        else
            return itemStack.isEnchanted();
    }
}
