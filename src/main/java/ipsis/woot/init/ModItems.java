package ipsis.woot.init;

import ipsis.woot.item.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static ItemWoot itemDie;
    public static ItemWoot itemFactoryBase;
    public static ItemWoot itemFactoryCore;
    public static ItemWoot itemSoulSandDust;
    public static ItemWoot itemStygianIronDust;
    public static ItemWoot itemStygianIronIngot;
    public static ItemWoot itemStygianIronPlate;
    public static ItemWoot itemEnderShard;
    public static ItemWoot itemShard;
    public static ItemWoot itemXpShard;
    public static ItemWoot itemYahHammer;


    public static void preInit() {

    }

    public static void init() {

        itemEnderShard = new ItemEnderShard();
        itemFactoryBase = new ItemFactoryBase();
        itemXpShard = new ItemXpShard();
        itemShard = new ItemShard();
        itemDie = new ItemDie();
        itemStygianIronPlate = new ItemStygianIronPlate();
        itemYahHammer = new ItemYahHammer();
        itemSoulSandDust = new ItemSoulSandDust();
        itemStygianIronDust = new ItemStygianIronDust();
        itemStygianIronIngot = new ItemStygianIronIngot();
        itemFactoryCore = new ItemFactoryCore();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModItems.itemEnderShard.initModel();
        ModItems.itemFactoryBase.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemShard.initModel();
        ModItems.itemDie.initModel();
        ModItems.itemYahHammer.initModel();
        ModItems.itemSoulSandDust.initModel();
        ModItems.itemStygianIronDust.initModel();
        ModItems.itemStygianIronIngot.initModel();
        ModItems.itemStygianIronPlate.initModel();
        ModItems.itemFactoryCore.initModel();
    }

}
