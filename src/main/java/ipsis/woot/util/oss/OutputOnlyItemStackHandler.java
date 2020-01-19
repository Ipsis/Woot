package ipsis.woot.util.oss;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * This is copied from Draco18s OutputItemStackHandler
 * github.com/Draco18s/ReasonableRealism
 */

public class OutputOnlyItemStackHandler extends ItemStackHandler {
    private final ItemStackHandler wrappedHandler;

    public OutputOnlyItemStackHandler(ItemStackHandler wrappedHandler) {
        super();
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        wrappedHandler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return wrappedHandler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return wrappedHandler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        // Not allowed to insert an item into an output slot
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return wrappedHandler.extractItem(slot, amount, simulate);
    }
}
