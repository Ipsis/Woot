package ipsis.woot.modules.infuser.items;

import ipsis.woot.Woot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;

public class DyeShardItem extends Item {

    final DyeColor color;

    public DyeShardItem(DyeColor color) {
        super(new Item.Properties().group(Woot.itemGroup));
        this.color = color;
    }

    public DyeColor getColor() { return this.color; }

}
