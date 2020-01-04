package ipsis.woot.modules.generic.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.generic.GenericItemType;
import net.minecraft.item.Item;

public class GenericItem extends Item {

    final GenericItemType itemType;
    public GenericItem(GenericItemType itemType) {
        super(new Item.Properties().maxStackSize(1).group(Woot.itemGroup));
        this.itemType = itemType;
    }

    public GenericItemType genericItemType() { return this.itemType; }
}
