package ipsis.woot.init;

import ipsis.woot.item.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemWoot itemDye;
    public static ItemWoot itemFactoryCap;
    public static ItemWoot itemFactoryCasing;
    public static ItemWoot itemFactoryConnector;
    public static ItemWoot itemFactoryFrame;
    public static ItemWoot itemFactoryUpgrade;
    public static ItemWoot itemFerrocrete;
    public static ItemWoot itemFerrocretePlate;
    public static ItemWoot itemManual;
    public static ItemWoot itemPrism2;
    public static ItemWoot itemPrismFrame;
    public static ItemWoot itemPulverisedFerrocrete;
    public static ItemWoot itemShard;
    public static ItemWoot itemSkull;
    public static ItemWoot itemXpShard;
    public static ItemWoot itemYahHammer;

    public static void preInit() {

    }

    public static void init() {

        itemPrism2 = new ItemPrism2();
        itemXpShard = new ItemXpShard();
        itemSkull = new ItemSkull();
        itemShard = new ItemShard();
        itemDye = new ItemDye();
        itemFactoryFrame = new ItemFactoryFrame();
        itemFactoryUpgrade = new ItemFactoryUpgrade();
        itemFactoryConnector = new ItemFactoryConnector();
        itemPrismFrame = new ItemPrismFrame();
        itemFerrocretePlate = new ItemFerrocretePlate();
        itemFactoryCasing = new ItemFactoryCasing();
        itemYahHammer = new ItemYahHammer();
        itemPulverisedFerrocrete = new ItemPulverisedFerrocrete();
        itemFerrocrete = new ItemFerrocrete();
        itemFactoryCap = new ItemFactoryCap();
        itemManual = new ItemManual();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModItems.itemPrism2.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemSkull.initModel();
        ModItems.itemShard.initModel();
        ModItems.itemDye.initModel();
        ModItems.itemFactoryFrame.initModel();
        ModItems.itemPrismFrame.initModel();
        ModItems.itemYahHammer.initModel();
        ModItems.itemPulverisedFerrocrete.initModel();
        ModItems.itemFerrocrete.initModel();
        ModItems.itemFerrocretePlate.initModel();
        ModItems.itemFactoryCasing.initModel();
        ModItems.itemFactoryUpgrade.initModel();
        ModItems.itemFactoryCap.initModel();
        ModItems.itemFactoryConnector.initModel();
        ModItems.itemManual.initModel();
    }

}
