package ipsis.woot.init;

import ipsis.woot.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void preInit() {

        GameRegistry.register(itemPrism);
        GameRegistry.register(itemXpShard);
        GameRegistry.register(itemSkull);
        GameRegistry.register(itemFactoryFrame);
        GameRegistry.register(itemFactoryUpgrade);
    }

    public static ItemWoot itemPrism = new ItemPrism();
    public static ItemWoot itemXpShard = new ItemXpShard();
    public static ItemWoot itemSkull = new ItemSkull();
    public static ItemWoot itemFactoryFrame = new ItemFactoryFrame();
    public static ItemWoot itemFactoryUpgrade = new ItemFactoryUpgrade();
}
