package ipsis.woot.init;

import ipsis.woot.item.ItemPrism;
import ipsis.woot.item.ItemSkull;
import ipsis.woot.item.ItemWoot;
import ipsis.woot.item.ItemXpShard;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void init() {

        GameRegistry.registerItem(itemPrism, ItemPrism.BASENAME);
        GameRegistry.registerItem(itemXpShard, ItemXpShard.BASENAME);
        GameRegistry.registerItem(itemSkull, ItemSkull.BASENAME);
    }

    public static ItemWoot itemPrism = new ItemPrism();
    public static ItemWoot itemXpShard = new ItemXpShard();
    public static ItemWoot itemSkull = new ItemSkull();

}
