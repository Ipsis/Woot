package ipsis.woot.init.recipes;

import ipsis.woot.reference.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShapedOreEnchBookRecipe extends ShapedOreRecipe {


    static {
        RecipeSorter.register(Reference.MOD_ID + ":EnchBook", ShapedOreEnchBookRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shapeless");
    }

    public ShapedOreEnchBookRecipe(ItemStack result, Object... recipe) {

        super(result, recipe);
    }

    @Override
    protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {


        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++)
        {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++)
            {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height)
                {
                    if (mirror)
                    {
                        target = input[width - subX - 1 + subY * width];
                    }
                    else
                    {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);

                if (target instanceof ItemStack && ((ItemStack) target).getItem() == Items.ENCHANTED_BOOK &&
                        slot instanceof ItemStack && ((ItemStack)slot).getItem() == Items.ENCHANTED_BOOK)
                {

                    Map<Enchantment, Integer> slotEnchantMap = EnchantmentHelper.getEnchantments((ItemStack)slot);
                    Map<Enchantment, Integer> targetEnchantMap = EnchantmentHelper.getEnchantments((ItemStack)target);

                    /**
                     * Only allow one enchantment books
                     */
                    if (slotEnchantMap.size() != 1)
                        return false;

                    for (Enchantment enchantment : targetEnchantMap.keySet()) {
                        /* Target will only have one enchant, slot may have more */
                        int lvl = targetEnchantMap.get(enchantment).intValue();
                        if (slotEnchantMap.containsKey(enchantment) && slotEnchantMap.get(enchantment).intValue() == lvl)
                            return true;
                    }

                    return false;
                }
                else if (target instanceof ItemStack)
                {
                    if (!OreDictionary.itemMatches((ItemStack)target, slot, false))
                    {
                        return false;
                    }
                }
                else if (target instanceof List)
                {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((List<ItemStack>)target).iterator();
                    while (itr.hasNext() && !matched)
                    {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if (!matched)
                    {
                        return false;
                    }
                }
                else if (target == null && slot != null)
                {
                    return false;
                }
            }
        }

        return true;
    }
}
