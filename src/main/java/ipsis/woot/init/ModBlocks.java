package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.item.ItemBlockController;
import ipsis.woot.item.ItemBlockStructure;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.tileentity.*;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void preInit() {

        GameRegistry.register(blockFactory);
        GameRegistry.register(blockUpgrade);
        GameRegistry.register(blockStructure);
        GameRegistry.register(blockController);
        GameRegistry.register(blockLayout);
        GameRegistry.register(new ItemBlock(blockFactory).setRegistryName(blockFactory.getRegistryName()));
        GameRegistry.register(new ItemBlockUpgrade(blockUpgrade).setRegistryName(blockUpgrade.getRegistryName()));
        GameRegistry.register(new ItemBlockStructure(blockStructure).setRegistryName(blockStructure.getRegistryName()));
        GameRegistry.register(new ItemBlockController(blockController).setRegistryName(blockController.getRegistryName()));
        GameRegistry.register(new ItemBlock(blockLayout).setRegistryName(blockLayout.getRegistryName()));
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFactory.class, "tile." + BlockMobFactory.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, "tile." + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, "tile." + BlockMobFactoryController.BASENAME);
        GameRegistry.registerTileEntity(TileEntityLayout.class, "tile." + BlockLayout.BASENAME);
    }

    public static BlockWoot blockFactory = new BlockMobFactory();
    public static BlockWoot blockUpgrade = new BlockMobFactoryUpgrade();
    public static BlockWoot blockStructure = new BlockMobFactoryStructure();
    public static BlockWoot blockController = new BlockMobFactoryController();
    public static BlockWoot blockLayout = new BlockLayout();
}
