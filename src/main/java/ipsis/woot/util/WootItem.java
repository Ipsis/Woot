package ipsis.woot.util;

import ipsis.woot.Woot;
import ipsis.woot.setup.ModSetup;
import net.minecraft.item.Item;

public class WootItem extends Item {

    public WootItem(Properties properties, String name) {
        super(properties.group(Woot.setup.getCreativeTab()));
        setRegistryName(Woot.MODID, name);
    }
}
