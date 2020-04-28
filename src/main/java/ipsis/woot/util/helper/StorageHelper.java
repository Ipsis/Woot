package ipsis.woot.util.helper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageHelper {

    public static void insertItems(List<ItemStack> items, List<LazyOptional<IItemHandler>> hdlrs) {

        if (items.isEmpty() || hdlrs.isEmpty())
            return;

        for (LazyOptional<IItemHandler> hdlr : hdlrs) {
            hdlr.ifPresent(h -> {
                for (int i = 0; i < items.size(); i++) {
                    ItemStack itemStack = items.get(i);
                    if (itemStack.isEmpty())
                        continue;

                    ItemStack remainder = ItemHandlerHelper.insertItemStacked(h, itemStack.copy(), false);
                    items.set(i, remainder);
                }
            });
        }
    }

    /**
     * Flattens the list and returns a list of unique items
     * The item counts may break the max stack size
     */
    public static List<ItemStack> flattenItemStackList(List<ItemStack> items) {
        List<ItemStack> flattened = new ArrayList<>();

        for (ItemStack itemStack : items) {
            for (ItemStack flatStack : flattened) {
                if (itemStack.isEmpty())
                    break;

                if (ItemHandlerHelper.canItemStacksStack(flatStack, itemStack)) {
                    flatStack.grow(itemStack.getCount());
                    itemStack.setCount(0);
                }
            }

            if (!itemStack.isEmpty())
                flattened.add(itemStack.copy());
        }

        return flattened;
    }

    public static int getCount(ItemStack itemStack, List<LazyOptional<IItemHandler>> hdlrs) {

        AtomicInteger count = new AtomicInteger();
        for (LazyOptional<IItemHandler> hdlr : hdlrs) {
            hdlr.ifPresent(h -> {
                for (int slot = 0; slot < h.getSlots(); slot++) {
                    ItemStack slotStack = h.getStackInSlot(slot);
                    if (slotStack.isEmpty())
                        continue;
                    if (ItemStack.areItemsEqual(itemStack, slotStack))
                        count.getAndAdd(slotStack.getCount());
                }
            });
        }

        return count.get();
    }
}
