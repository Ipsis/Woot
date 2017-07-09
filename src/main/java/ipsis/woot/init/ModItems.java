package ipsis.woot.init;

import ipsis.woot.item.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemWoot itemDie;
    // public static ItemWoot itemFactoryBase;
    public static ItemWoot itemFactoryCap;
    public static ItemWoot itemFactoryCasing;
    public static ItemWoot itemFactoryCore;
    public static ItemWoot itemFerrocreteDust;
    public static ItemWoot itemFerrocreteIngot;
    public static ItemWoot itemFerrocretePlate;
    public static ItemWoot itemManual;
    public static ItemWoot itemNetherrackDust;
    public static ItemWoot itemPrism;
    public static ItemWoot itemSkull;
    public static ItemWoot itemShard;
    public static ItemWoot itemXpShard;
    public static ItemWoot itemYahHammer;



    public static ItemWoot itemFactoryConnector;
    public static ItemWoot itemFactoryFrame;
    public static ItemWoot itemFactoryUpgrade;
    public static ItemWoot itemPrismFrame;

    public static void preInit() {

    }

    public static void init() {

        itemPrism = new ItemPrism();
        itemXpShard = new ItemXpShard();
        itemSkull = new ItemSkull();
        itemShard = new ItemShard();
        itemDie = new ItemDie();
        itemFactoryFrame = new ItemFactoryFrame();
        itemFactoryUpgrade = new ItemFactoryUpgrade();
        itemFactoryConnector = new ItemFactoryConnector();
        itemPrismFrame = new ItemPrismFrame();
        itemNetherrackDust = new ItemNetherrackDust();
        itemFerrocretePlate = new ItemFerrocretePlate();
        itemFactoryCasing = new ItemFactoryCasing();
        itemYahHammer = new ItemYahHammer();
        itemFerrocreteDust = new ItemPulverisedFerrocrete();
        itemFerrocreteIngot = new ItemFerrocreteIngot();
        itemFactoryCap = new ItemFactoryCap();
        itemManual = new ItemManual();
        itemFactoryCore = new ItemFactoryCore();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModItems.itemPrism.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemSkull.initModel();
        ModItems.itemShard.initModel();
        ModItems.itemDie.initModel();
        ModItems.itemFactoryFrame.initModel();
        ModItems.itemPrismFrame.initModel();
        ModItems.itemYahHammer.initModel();
        ModItems.itemFerrocreteDust.initModel();
        ModItems.itemFerrocreteIngot.initModel();
        ModItems.itemFerrocretePlate.initModel();
        ModItems.itemFactoryCasing.initModel();
        ModItems.itemFactoryUpgrade.initModel();
        ModItems.itemFactoryCap.initModel();
        ModItems.itemFactoryConnector.initModel();
        ModItems.itemManual.initModel();
        ModItems.itemNetherrackDust.initModel();
        ModItems.itemFactoryCore.initModel();
    }

}
