package ipsis.woot.util;

import ipsis.woot.Woot;
import net.minecraft.item.Item;

public class WootItem extends Item {

    public WootItem(Properties properties, String name) {
        super(properties.group(Woot.itemGroup));
        setRegistryName(Woot.MODID, name);
    }
}
