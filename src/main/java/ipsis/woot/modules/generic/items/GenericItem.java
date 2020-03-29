package ipsis.woot.modules.generic.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.generic.GenericItemType;
import ipsis.woot.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GenericItem extends Item {

    final GenericItemType itemType;
    public GenericItem(GenericItemType itemType, int stackSize) {
        super(new Item.Properties().maxStackSize(stackSize).group(Woot.setup.getCreativeTab()));
        this.itemType = itemType;
    }

    public GenericItem(GenericItemType itemType) {
        this(itemType, 64);
    }

    public GenericItemType genericItemType() { return this.itemType; }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return itemType == GenericItemType.ENCH_PLATE_1 || itemType == GenericItemType.ENCH_PLATE_2 || itemType == GenericItemType.ENCH_PLATE_3;
    }
}
