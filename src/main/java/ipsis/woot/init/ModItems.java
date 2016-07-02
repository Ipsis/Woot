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

        GameRegistry.register(itemFerrocrete);
        GameRegistry.register(itemFerrocretePlate);
        GameRegistry.register(itemFactoryCasing);

        GameRegistry.register(itemYahHammer);
        GameRegistry.register(itemIronFile);
        GameRegistry.register(itemPulverisedFerrocrete);
        GameRegistry.register(itemDyePlate);
        GameRegistry.register(itemDyeCasing);
        GameRegistry.register(itemDyeSkull);
        GameRegistry.register(itemFactoryCap);
    }

    public static ItemWoot itemPrism = new ItemPrism();
    public static ItemWoot itemXpShard = new ItemXpShard();
    public static ItemWoot itemSkull = new ItemSkull();
    public static ItemWoot itemFactoryFrame = new ItemFactoryFrame();
    public static ItemWoot itemFactoryUpgrade = new ItemFactoryUpgrade();


    public static ItemWoot itemFerrocretePlate = new ItemFerrocretePlate();
    public static ItemWoot itemFactoryCasing = new ItemFactoryCasing();

    public static ItemWoot itemYahHammer = new ItemYahHammer();
    public static ItemWoot itemIronFile = new ItemIronFile();
    public static ItemWoot itemPulverisedFerrocrete = new ItemPulverisedFerrocrete();
    public static ItemWoot itemFerrocrete = new ItemFerrocrete();
    public static ItemWoot itemDyePlate = new ItemDyePlate();
    public static ItemWoot itemDyeCasing = new ItemDyeCasing();
    public static ItemWoot itemDyeSkull = new ItemDyeSkull();
    public static ItemWoot itemFactoryCap = new ItemFactoryCap();
}
