package ipsis.woot.util;

import ipsis.Woot;
import net.minecraft.item.Item;

public class WootItem extends Item {

    public WootItem(String basename) {
        setRegistryName(basename);
        setTranslationKey(Woot.MODID + "." + basename);
    }
}
