package ipsis.woot.init;

import ipsis.woot.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static void preInit() {

        GameRegistry.register(itemPrism);
        GameRegistry.register(itemPrism2);
        GameRegistry.register(itemXpShard);
        GameRegistry.register(itemSkull);
        GameRegistry.register(itemShard);
        GameRegistry.register(itemDye);
        GameRegistry.register(itemFactoryFrame);
        GameRegistry.register(itemFactoryUpgrade);

        GameRegistry.register(itemFerrocrete);
        GameRegistry.register(itemFerrocretePlate);
        GameRegistry.register(itemFactoryCasing);
        GameRegistry.register(itemPrismFrame);

        GameRegistry.register(itemYahHammer);

        GameRegistry.register(itemPulverisedFerrocrete);

        GameRegistry.register(itemFactoryCap);
        GameRegistry.register(itemFactoryConnector);

        GameRegistry.register(itemManual);
    }

    public static ItemWoot itemPrism = new ItemPrism();
    public static ItemWoot itemPrism2 = new ItemPrism2();
    public static ItemWoot itemXpShard = new ItemXpShard();
    public static ItemWoot itemSkull = new ItemSkull();
    public static ItemWoot itemShard = new ItemShard();
    public static ItemWoot itemDye = new ItemDye();
    public static ItemWoot itemFactoryFrame = new ItemFactoryFrame();
    public static ItemWoot itemFactoryUpgrade = new ItemFactoryUpgrade();
    public static ItemWoot itemFactoryConnector = new ItemFactoryConnector();
    public static ItemWoot itemPrismFrame = new ItemPrismFrame();

    public static ItemWoot itemFerrocretePlate = new ItemFerrocretePlate();
    public static ItemWoot itemFactoryCasing = new ItemFactoryCasing();

    public static ItemWoot itemYahHammer = new ItemYahHammer();
    public static ItemWoot itemPulverisedFerrocrete = new ItemPulverisedFerrocrete();
    public static ItemWoot itemFerrocrete = new ItemFerrocrete();
    public static ItemWoot itemFactoryCap = new ItemFactoryCap();

    public static ItemWoot itemManual = new ItemManual();
}
