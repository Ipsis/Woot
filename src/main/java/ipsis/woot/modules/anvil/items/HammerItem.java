package ipsis.woot.modules.anvil.items;

import ipsis.woot.Woot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HammerItem extends Item{

    public HammerItem() {
        super(new Item.Properties().maxStackSize(1).group(Woot.itemGroup));
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }
}
