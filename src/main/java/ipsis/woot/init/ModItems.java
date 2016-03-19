package ipsis.woot.init;

import ipsis.woot.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void preInit() {

        GameRegistry.registerItem(itemPrism, ItemPrism.BASENAME);
        GameRegistry.registerItem(itemXpShard, ItemXpShard.BASENAME);
        GameRegistry.registerItem(itemSkull, ItemSkull.BASENAME);
        GameRegistry.registerItem(itemFactoryFrame, ItemFactoryFrame.BASENAME);
        GameRegistry.registerItem(itemFactoryUpgrade, ItemFactoryUpgrade.BASENAME);
    }

    public static ItemWoot itemPrism = new ItemPrism();
    public static ItemWoot itemXpShard = new ItemXpShard();
    public static ItemWoot itemSkull = new ItemSkull();
    public static ItemWoot itemFactoryFrame = new ItemFactoryFrame();
    public static ItemWoot itemFactoryUpgrade = new ItemFactoryUpgrade();
}
