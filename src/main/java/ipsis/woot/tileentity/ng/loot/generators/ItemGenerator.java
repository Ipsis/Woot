package ipsis.woot.tileentity.ng.loot.generators;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.ng.IFarmSetup;
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

        List<ItemStack> loot = Woot.lootRepository.getDrops(farmSetup.getWootMobName(), farmSetup.getEnchantKey(), farmSetup.getNumMobs());

        for (IItemHandler hdlr : itemHandlerList) {
            for (ItemStack itemStack : loot) {

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
