package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockController;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.TileEntityMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactory;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void preInit() {

        GameRegistry.register(blockFactory);
        GameRegistry.register(blockUpgrade);
        GameRegistry.register(blockStructure);
        GameRegistry.register(blockController);
        GameRegistry.register(new ItemBlock(blockFactory).setRegistryName(blockFactory.getRegistryName()));
        GameRegistry.register(new ItemBlockUpgrade(blockUpgrade).setRegistryName(blockUpgrade.getRegistryName()));
        GameRegistry.register(new ItemBlockStructure(blockStructure).setRegistryName(blockStructure.getRegistryName()));
        GameRegistry.register(new ItemBlockController(blockController).setRegistryName(blockController.getRegistryName()));
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
