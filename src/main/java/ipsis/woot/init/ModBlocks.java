package ipsis.woot.init;

import ipsis.woot.block.BlockContainerWoot;
import ipsis.woot.block.BlockFactory;
import ipsis.woot.tileentity.TileEntityFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void init() {

        GameRegistry.registerBlock(blockFactory, BlockFactory.BASENAME);
    }

    public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityFactory.class, "tile." + BlockFactory.BASENAME);
    }

    public static BlockContainerWoot blockFactory = new BlockFactory();
}
