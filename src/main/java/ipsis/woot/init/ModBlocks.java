package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.TileEntityMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFarm;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void init() {

        GameRegistry.registerBlock(blockFactory, BlockFactory.BASENAME);
        GameRegistry.registerBlock(blockUpgrade, ItemBlockUpgrade.class, BlockUpgrade.BASENAME);
        GameRegistry.registerBlock(blockStructure, ItemBlockStructure.class, BlockMobFactoryStructure.BASENAME);
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFarm.class, "tile." + BlockFactory.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockUpgrade.BASENAME);
    }

    public static BlockContainerWoot blockFactory = new BlockFactory();
    public static BlockContainerWoot blockUpgrade = new BlockUpgrade();
    public static BlockContainerWoot blockStructure = new BlockMobFactoryStructure();
}
