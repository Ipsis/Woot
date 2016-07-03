package ipsis.woot.init.recipes;

import ipsis.woot.item.ItemDye;
import ipsis.woot.item.ItemYahHammer;
import ipsis.woot.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Handler hammer recipes where you provide the hammer, obsidian and dye.
 */

public class ShapedOreHammerRecipe extends ShapedOreRecipe {

    static {
        RecipeSorter.register(Reference.MOD_ID + ":Hammer", ShapedOreHammerRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shapeless");
    }


    public ShapedOreHammerRecipe(ItemStack result, Object... recipe) {

        super(result, recipe);
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {

        ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
        for (int i = 0; i < ret.length; i++) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if (itemStack != null && itemStack.getItem() instanceof ItemYahHammer)
                ret[i] = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
            else if (itemStack != null && Block.getBlockFromItem(itemStack.getItem()) instanceof BlockObsidian)
                ret[i] = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
            else if (itemStack != null && itemStack.getItem() instanceof ItemDye)
                ret[i] = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
        }
        return ret;
    }
}
