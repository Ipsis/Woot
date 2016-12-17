package ipsis.woot.init.recipes;

import ipsis.woot.item.ItemDye;
import ipsis.woot.item.ItemYahHammer;
import ipsis.woot.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {

        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < ret.size(); i++) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemYahHammer)
                ret.set(i, ItemHandlerHelper.copyStackWithSize(itemStack, 1));
            else if (!itemStack.isEmpty() && Block.getBlockFromItem(itemStack.getItem()) instanceof BlockObsidian)
                ret.set(i, ItemHandlerHelper.copyStackWithSize(itemStack, 1));
            else if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemDye)
                ret.set(i, ItemHandlerHelper.copyStackWithSize(itemStack, 1));
        }
        return ret;
    }
}
