package ipsis.woot.init;

import ipsis.woot.item.ItemPrism;
import ipsis.woot.item.ItemWoot;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void init() {

        GameRegistry.registerItem(itemPrism, ItemPrism.BASENAME);
    }

    public static ItemWoot itemPrism = new ItemPrism();
}
