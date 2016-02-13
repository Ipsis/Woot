package ipsis.woot.init;

import ipsis.woot.block.BlockContainerWoot;
import ipsis.woot.block.BlockFactory;
import ipsis.woot.block.BlockUpgrade;
import ipsis.woot.block.BlockWoot;
import ipsis.woot.item.ItemBlockUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void init() {

        GameRegistry.registerBlock(blockFactory, BlockFactory.BASENAME);
        GameRegistry.registerBlock(blockUpgrade, ItemBlockUpgrade.class, BlockUpgrade.BASENAME);
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMobFactory.class, "tile." + BlockFactory.BASENAME);
    }

    public static BlockContainerWoot blockFactory = new BlockFactory();
    public static BlockWoot blockUpgrade = new BlockUpgrade();
}
