package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockController;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.TileEntityMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void init() {

        GameRegistry.registerBlock(blockFactory, BlockMobFactory.BASENAME);
        GameRegistry.registerBlock(blockUpgrade, ItemBlockUpgrade.class, BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerBlock(blockStructure, ItemBlockStructure.class, BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerBlock(blockController, ItemBlockController.class, BlockMobFactoryController.BASENAME);
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFactory.class, "tile." + BlockMobFactory.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, "tile." + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, "tile." + BlockMobFactoryController.BASENAME);
    }

    public static BlockContainerWoot blockFactory = new BlockMobFactory();
    public static BlockContainerWoot blockUpgrade = new BlockMobFactoryUpgrade();
    public static BlockContainerWoot blockStructure = new BlockMobFactoryStructure();
    public static BlockContainerWoot blockController = new BlockMobFactoryController();
}
