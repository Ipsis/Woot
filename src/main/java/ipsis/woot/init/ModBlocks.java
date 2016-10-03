package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockController;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.item.ItemBlockUpgradeB;
import ipsis.woot.tileentity.*;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void preInit() {

        GameRegistry.register(blockFactory);
        GameRegistry.register(blockUpgrade);
        GameRegistry.register(blockUpgradeB);
        GameRegistry.register(blockStructure);
        GameRegistry.register(blockController);
        GameRegistry.register(blockLayout);
        GameRegistry.register(blockProxy);
        GameRegistry.register(blockExtender);
        GameRegistry.register(blockSkyblockFactory);
        GameRegistry.register(new ItemBlock(blockFactory).setRegistryName(blockFactory.getRegistryName()));
        GameRegistry.register(new ItemBlockUpgrade(blockUpgrade).setRegistryName(blockUpgrade.getRegistryName()));
        GameRegistry.register(new ItemBlockUpgradeB(blockUpgradeB).setRegistryName(blockUpgradeB.getRegistryName()));
        GameRegistry.register(new ItemBlockStructure(blockStructure).setRegistryName(blockStructure.getRegistryName()));
        GameRegistry.register(new ItemBlockController(blockController).setRegistryName(blockController.getRegistryName()));
        GameRegistry.register(new ItemBlock(blockLayout).setRegistryName(blockLayout.getRegistryName()));
        GameRegistry.register(new ItemBlock(blockProxy).setRegistryName(blockProxy.getRegistryName()));
        GameRegistry.register(new ItemBlock(blockExtender).setRegistryName(blockExtender.getRegistryName()));
        GameRegistry.register(new ItemBlock(blockSkyblockFactory).setRegistryName(blockSkyblockFactory.getRegistryName()));
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFactory.class, "tile." + BlockMobFactory.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, "tile." + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, "tile." + BlockMobFactoryController.BASENAME);
        GameRegistry.registerTileEntity(TileEntityLayout.class, "tile." + BlockLayout.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryProxy.class, "tile." + BlockMobFactoryProxy.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryExtender.class, "tile." + BlockMobFactoryExtender.BASENAME);
        GameRegistry.registerTileEntity(TileEntitySkyblockFactory.class, "tile." + BlockSkyblockFactory.BASENAME);
    }

    public static BlockWoot blockFactory = new BlockMobFactory();
    public static BlockWoot blockUpgrade = new BlockMobFactoryUpgrade();
    public static BlockWoot blockUpgradeB = new BlockMobFactoryUpgradeB();
    public static BlockWoot blockStructure = new BlockMobFactoryStructure();
    public static BlockWoot blockController = new BlockMobFactoryController();
    public static BlockWoot blockLayout = new BlockLayout();
    public static BlockWoot blockProxy = new BlockMobFactoryProxy();
    public static BlockWoot blockExtender = new BlockMobFactoryExtender();
    public static BlockWoot blockSkyblockFactory = new BlockSkyblockFactory();
}
