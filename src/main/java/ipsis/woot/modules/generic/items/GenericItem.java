package ipsis.woot.modules.generic.items;

import ipsis.woot.Woot;
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

    public enum GenericItemType {
        SI_INGOT,
        SI_DUST,
        SI_PLATE,
        PRISM,
        ENCH_PLATE_1,
        ENCH_PLATE_2,
        ENCH_PLATE_3,
        BASIC_UP_SHARD,
        ADVANCED_UP_SHARD,
        ELITE_UP_SHARD,
        MACHINE_CASING
    }
}
