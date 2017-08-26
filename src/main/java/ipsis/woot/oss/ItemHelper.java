package ipsis.woot.oss;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Selected methods from CoFhLib/ItemHelper.java
 * License - https://github.com/CoFH/CoFHLib/blob/master/README.md
 */
public class ItemHelper {

    /**
     * This prevents an overridden getDamage() call from messing up metadata acquisition.
     */
    public static int getItemDamage(ItemStack stack) {

        return Items.DIAMOND.getDamage(stack);
    }

    public static boolean areItemsEqual(Item itemA, Item itemB) {

        if (itemA == null | itemB == null) {
            return false;
        }
        return itemA == itemB || itemA.equals(itemB);
    }

    /**
     * Determine if the damage of two ItemStacks is equal. Assumes both itemstacks are of type A.
     */
    public static boolean itemsDamageEqual(ItemStack stackA, ItemStack stackB) {

        return (!stackA.getHasSubtypes() && stackA.getMaxDamage() == 0) || (getItemDamage(stackA) == getItemDamage(stackB));
    }

    /**
     * Determine if two ItemStacks have the same Item.
     */
    public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB) {

        if (stackA.isEmpty() || stackB.isEmpty()) {
            return false;
        }
        return areItemsEqual(stackA.getItem(), stackB.getItem());
    }

    /**
     * Determine if two ItemStacks have the same Item and NBT.
     */
    public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT) {

        return itemsEqualWithoutMetadata(stackA, stackB) && (!checkNBT || doNBTsMatch(stackA.getTagCompound(), stackB.getTagCompound()));
    }

    /**
     * Determine if two ItemStacks have the same Item and damage.
     */
    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB) {

        return itemsEqualWithoutMetadata(stackA, stackB) && itemsDamageEqual(stackA, stackB);
    }

    /**
     * Determine if two ItemStacks have the same Item, damage, and NBT.
     */
    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT) {

        return itemsEqualWithMetadata(stackA, stackB) && (!checkNBT || doNBTsMatch(stackA.getTagCompound(), stackB.getTagCompound()));
    }

    /**
     * Determine if two ItemStacks have the same Item, identical damage, and NBT.
     */
    public static boolean itemsIdentical(ItemStack stackA, ItemStack stackB) {

        return itemsEqualWithoutMetadata(stackA, stackB) && getItemDamage(stackA) == getItemDamage(stackB) && doNBTsMatch(stackA.getTagCompound(), stackB.getTagCompound());
    }

    /**
     * Determine if two NBTTagCompounds are equal.
     */
    public static boolean doNBTsMatch(NBTTagCompound nbtA, NBTTagCompound nbtB) {

        if (nbtA == null & nbtB == null) {
            return true;
        }
        if (nbtA != null & nbtB != null) {
            return nbtA.equals(nbtB);
        }
        return false;
    }
}
