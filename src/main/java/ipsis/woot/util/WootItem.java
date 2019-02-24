package ipsis.woot.util;

import ipsis.woot.Woot;
import net.minecraft.item.Item;

public class WootItem extends Item {

    public WootItem(Item.Properties properties, String basename) {
        super(properties);
        setRegistryName(Woot.MODID, basename);
    }
}
