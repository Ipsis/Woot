package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.loot.repository.ILootRepositoryLookup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemGenerator implements ILootGenerator {

    public void generate(@Nonnull List<IFluidHandler> fluidHandlerList, @Nonnull List<IItemHandler> itemHandlerList, @Nonnull IFarmSetup farmSetup) {

        if (itemHandlerList.size() == 0)
            return;

        List<ILootRepositoryLookup.LootItemStack> loot = Woot.lootRepository.getDrops(farmSetup.getWootMobName(), farmSetup.getEnchantKey());
        // TODO handle more than one mob spawned

        for (IItemHandler hdlr : itemHandlerList) {
            for (ILootRepositoryLookup.LootItemStack lootItemStack : loot) {
                ItemStack itemStack = lootItemStack.itemStack;

                if (itemStack.isEmpty())
                    continue;

                boolean success = true;
                while (success && !itemStack.isEmpty()) {

                    /**
                     * We try to insert 1 item and decrease itemStack.stackSize
                     * if it is successfull
                     * This is not very efficient
                     */
                    ItemStack result = ItemHandlerHelper.insertItem(hdlr, ItemHandlerHelper.copyStackWithSize(itemStack, 1), false);
                    if (result.isEmpty())
                        itemStack.shrink(1);
                    else
                        success = false;
                }
            }
        }
    }
}
