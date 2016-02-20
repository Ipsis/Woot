package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFarm;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void init() {

        GameRegistry.registerBlock(blockFactory, BlockMobFactory.BASENAME);
        GameRegistry.registerBlock(blockUpgrade, ItemBlockUpgrade.class, BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerBlock(blockStructure, ItemBlockStructure.class, BlockMobFactoryStructure.BASENAME);
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFarm.class, "tile." + BlockMobFactory.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryUpgrade.BASENAME);
    }

    public static BlockContainerWoot blockFactory = new BlockMobFactory();
    public static BlockContainerWoot blockUpgrade = new BlockMobFactoryUpgrade();
    public static BlockContainerWoot blockStructure = new BlockMobFactoryStructure();
}
