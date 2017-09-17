package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockWoot blockAnvil;
    public static BlockWoot blockFactoryHeart;
    public static BlockWoot blockSoulStone;
    public static BlockWoot blockStygianIron;
    public static BlockWoot blockStygianIronOre;
    public static BlockWoot blockUpgrade;
    public static BlockWoot blockUpgradeB;
    public static BlockWoot blockStructure;
    public static BlockWoot blockFactoryController;
    public static BlockWoot blockLayout;
    public static BlockWoot blockImporter;
    public static BlockWoot blockExporter;
    public static BlockWoot blockCell;

    public static void preInit() {

    }

    public static void init() {

        blockAnvil = new BlockWootAnvil();
        blockFactoryHeart = new BlockMobFactoryHeart();
        blockSoulStone = new BlockSoulStone();
        blockStygianIron = new BlockStygianIron();
        blockStygianIronOre = new BlockStygianIronOre();
        blockUpgrade = new BlockMobFactoryUpgrade();
        blockUpgradeB = new BlockMobFactoryUpgradeB();
        blockStructure = new BlockMobFactoryStructure();
        blockFactoryController = new BlockMobFactoryController();
        blockLayout = new BlockLayout();
        blockImporter = new BlockMobFactoryImporter();
        blockExporter = new BlockMobFactoryExporter();
        blockCell = new BlockMobFactoryCell();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModBlocks.blockAnvil.initModel();
        ModBlocks.blockFactoryHeart.initModel();
        ModBlocks.blockSoulStone.initModel();
        ModBlocks.blockStygianIron.initModel();
        ModBlocks.blockStygianIronOre.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockUpgradeB.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockFactoryController.initModel();
        ModBlocks.blockLayout.initModel();
        ModBlocks.blockImporter.initModel();
        ModBlocks.blockExporter.initModel();
        ModBlocks.blockCell.initModel();
    }

    public static void registerTileEntities() {

        String modkey = "tile." + Reference.MOD_ID + "_";

        GameRegistry.registerTileEntity(TileEntityMobFactoryHeart.class, modkey + BlockMobFactoryHeart.BASENAME);
        GameRegistry.registerTileEntity(TileEntityAnvil.class, modkey + BlockWootAnvil.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, modkey + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, modkey + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, modkey + BlockMobFactoryController.BASENAME);
        GameRegistry.registerTileEntity(TileEntityLayout.class, modkey + BlockLayout.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryImporter.class, modkey + BlockMobFactoryImporter.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryExporter.class, modkey + BlockMobFactoryExporter.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryCell.class, modkey + BlockMobFactoryCell.BASENAME);
    }

}
