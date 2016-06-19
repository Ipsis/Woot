package ipsis.woot.init.recipes;

import ipsis.woot.item.ItemIronFile;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessOreFileRecipe extends ShapelessOreRecipe {

    public ShapelessOreFileRecipe(ItemStack result, Object... recipe) {

        super(result, recipe);
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {

        ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
        for (int i = 0; i < ret.length; i++) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if (itemStack != null && itemStack.getItem() instanceof ItemIronFile)
                ret[i] = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
        }
        return ret;
    }
}
