package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockController;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.item.ItemBlockUpgradeB;
import ipsis.woot.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static BlockWoot blockFactory;
    public static BlockWoot blockUpgrade;
    public static BlockWoot blockUpgradeB;
    public static BlockWoot blockStructure;
    public static BlockWoot blockController;
    public static BlockWoot blockLayout;
    public static BlockWoot blockProxy;
    public static BlockWoot blockExtender;

    public static void preInit() {

    }

    public static void init() {

        blockFactory = new BlockMobFactory();
        blockUpgrade = new BlockMobFactoryUpgrade();
        blockUpgradeB = new BlockMobFactoryUpgradeB();
        blockStructure = new BlockMobFactoryStructure();
        blockController = new BlockMobFactoryController();
        blockLayout = new BlockLayout();
        blockProxy = new BlockMobFactoryProxy();
        blockExtender = new BlockMobFactoryExtender();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModBlocks.blockFactory.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockUpgradeB.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockController.initModel();
        ModBlocks.blockLayout.initModel();
        ModBlocks.blockProxy.initModel();
        ModBlocks.blockExtender.initModel();
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFarm.class, "tile." + BlockMobFactory.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, "tile." + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, "tile." + BlockMobFactoryController.BASENAME);
        GameRegistry.registerTileEntity(TileEntityLayout.class, "tile." + BlockLayout.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryProxy.class, "tile." + BlockMobFactoryProxy.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryExtender.class, "tile." + BlockMobFactoryExtender.BASENAME);
    }

}
