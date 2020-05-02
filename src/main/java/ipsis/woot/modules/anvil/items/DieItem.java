package ipsis.woot.modules.anvil.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.AnvilSetup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * These items are NEVER consumed in recipes
 */
public class DieItem extends Item {

    final DieType dieType;
    public DieItem(DieType dieType) {
        super(new Item.Properties().maxStackSize(1).group(Woot.setup.getCreativeTab()));
        this.dieType = dieType;
    }

    public DieType getDieType() { return this.dieType; }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }

    public static ItemStack getItemStack(DieType dieType) {
        if (dieType == DieType.PLATE)
            return new ItemStack(AnvilSetup.PLATE_DIE_ITEM.get());
        if (dieType == DieType.SHARD)
            return new ItemStack(AnvilSetup.SHARD_DIE_ITEM.get());
        if (dieType == DieType.DYE)
            return new ItemStack(AnvilSetup.DYE_DIE_ITEM.get());
        return ItemStack.EMPTY;
    }

    public enum DieType {
        PLATE,
        SHARD,
        DYE
    }
}
