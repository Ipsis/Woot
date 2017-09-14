package ipsis.woot.farming;

import ipsis.woot.oss.ItemHelper;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
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

    private boolean processItems(List<IItemHandler> itemHandlerList, @Nonnull ISpawnRecipe spawnRecipe, int mobCount) {

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

        if (allItemsPresent) {
            // Consume all the items which we have already checked are present
            for (ItemStack itemStack : spawnRecipe.getItems())
                consumeItems(itemHandlerList, itemStack, itemStack.getCount() * mobCount);

            return true;
        }

        return false;
    }

    private boolean processFluids(List<IFluidHandler> fluidHandlerList, @Nonnull ISpawnRecipe spawnRecipe, int mobCount) {

        if (spawnRecipe.getFluids().isEmpty())
            return true;

        return true;
    }

    @Override
    public boolean consume(World world, BlockPos pos, List<IFluidHandler> fluidHandlerList, List<IItemHandler> itemHandlerList, @Nullable ISpawnRecipe spawnRecipe, int mobCount) {

        if (spawnRecipe == null)
            return true;

        if (!processItems(itemHandlerList, spawnRecipe, mobCount) || !processFluids(fluidHandlerList, spawnRecipe, mobCount))
            return false;

        return true;
    }
}
