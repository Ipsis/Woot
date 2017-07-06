package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockWoot blockAnvil;
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

        blockAnvil = new BlockWootAnvil();
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

        ModBlocks.blockAnvil.initModel();
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
        GameRegistry.registerTileEntity(TileEntityAnvil.class, "tile." + BlockWootAnvil.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, "tile." + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, "tile." + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, "tile." + BlockMobFactoryController.BASENAME);
        GameRegistry.registerTileEntity(TileEntityLayout.class, "tile." + BlockLayout.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryProxy.class, "tile." + BlockMobFactoryProxy.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryExtender.class, "tile." + BlockMobFactoryExtender.BASENAME);
    }

}
