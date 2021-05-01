package ipsis.woot.modules.infuser.items;

import ipsis.woot.Woot;
import ipsis.woot.setup.ModSetup;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;

public class DyeCasingItem extends Item {

    final DyeColor color;

    public DyeCasingItem(DyeColor color) {
        super(new Item.Properties().stacksTo(64).tab(Woot.setup.getCreativeTab()));
        this.color = color;
    }

    public DyeColor getColor() { return this.color; }
}
