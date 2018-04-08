package ipsis.woot.farming;

import ipsis.woot.oss.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SpawnRecipeConsumer implements ISpawnRecipeConsumer {

    /**
     * Returns the number of exact matching items in the attached inventories
     */
    private int getItemCount(List<IItemHandler> itemHandlerList, ItemStack itemStack) {

        int count = 0;

        for (IItemHandler iItemHandler : itemHandlerList) {

            if (iItemHandler.getSlots() == 0)
                continue;

            for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {

                ItemStack stack = iItemHandler.getStackInSlot(slot);
                if (stack.isEmpty())
                    continue;

                // item, damage and NBT match
                if (ItemHelper.itemsIdentical(itemStack, stack))
                    count += stack.getCount();
            }
        }

        return count;
    }

    private int getFluidCount(List<IFluidHandler> fluidHandlerList, FluidStack fluidStack) {

        int amount = 0;

        for (IFluidHandler iFluidHandler : fluidHandlerList) {

            for (IFluidTankProperties iFluidTankProperties : iFluidHandler.getTankProperties()) {
                FluidStack contentStack = iFluidTankProperties.getContents();
                if (contentStack == null)
                    continue;

                if (contentStack.isFluidEqual(fluidStack))
                    amount += contentStack.amount;
            }
        }

        return amount;
    }

    private int consumeItems(List<IItemHandler> itemHandlerList, ItemStack itemStack, int count) {

        int left = count;
        for (IItemHandler iItemHandler : itemHandlerList) {

            if (iItemHandler.getSlots() == 0)
                continue;

            for (int slot = 0; slot < iItemHandler.getSlots(); slot++) {

                ItemStack stack = iItemHandler.getStackInSlot(slot);
                if (stack.isEmpty())
                    continue;

                // item, damage and NBT match
                if (ItemHelper.itemsIdentical(itemStack, stack)) {

                    ItemStack t = iItemHandler.extractItem(slot, left, false);
                    left -= t.getCount();
                }
            }
        }

        return left;
    }

    private int consumeFluids(List<IFluidHandler> fluidHandlerList, FluidStack fluidStack, int amount) {

        FluidStack drainStack = fluidStack.copy();
        drainStack.amount = amount;
        for (IFluidHandler iFluidHandler : fluidHandlerList) {


            FluidStack successStack = iFluidHandler.drain(drainStack, true);
            if (successStack != null) {
                drainStack.amount -= successStack.amount;
                if (drainStack.amount < 0)
                    drainStack.amount = 0;
            }

        }

        return drainStack.amount;
    }

    private boolean processItems(List<IItemHandler> itemHandlerList, @Nonnull ISpawnRecipe spawnRecipe, int mobCount, boolean simulate) {

        if (spawnRecipe.getItems().isEmpty())
            return true;


        boolean allItemsPresent = true;

        // Do we have all items
        for (ItemStack itemStack : spawnRecipe.getItems()) {

            int need = itemStack.getCount() * mobCount;
            int found = getItemCount(itemHandlerList, itemStack);

            if (found < need) {
                allItemsPresent = false;
                break;
            }
        }

        if (simulate)
            return allItemsPresent;

        if (allItemsPresent) {
            // Consume all the items which we have already checked are present
            for (ItemStack itemStack : spawnRecipe.getItems())
                consumeItems(itemHandlerList, itemStack, itemStack.getCount() * mobCount);

            return true;
        }

        return false;
    }

    private boolean processFluids(List<IFluidHandler> fluidHandlerList, @Nonnull ISpawnRecipe spawnRecipe, int mobCount, boolean simulate) {

        if (spawnRecipe.getFluids().isEmpty())
            return true;

        boolean allFluidPresent = true;

        // Do we have all fluids
        for (FluidStack fluidStack :spawnRecipe.getFluids()) {

            int need = fluidStack.amount * mobCount;
            int found = getFluidCount(fluidHandlerList, fluidStack);

            if (found < need) {
                allFluidPresent = false;
                break;
            }
        }

        if (simulate)
            return allFluidPresent;

        if (allFluidPresent) {
            // Consume all the fluids which we have already checked are present
            for (FluidStack fluidStack : spawnRecipe.getFluids())
                consumeFluids(fluidHandlerList, fluidStack, fluidStack.amount * mobCount);

            return true;
        }

        return false;
    }

    @Override
    public boolean consume(World world, BlockPos pos, List<IFluidHandler> fluidHandlerList, List<IItemHandler> itemHandlerList, @Nullable ISpawnRecipe spawnRecipe, int mobCount, boolean simulate) {

        if (spawnRecipe == null)
            return true;

        if (!processItems(itemHandlerList, spawnRecipe, mobCount, simulate) || !processFluids(fluidHandlerList, spawnRecipe, mobCount, simulate))
            return false;

        return true;
    }
}
