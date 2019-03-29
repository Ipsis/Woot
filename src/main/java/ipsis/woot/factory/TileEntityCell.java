package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemUseContext;

import java.util.List;

public class TileEntityCell extends TileEntityMultiBlock implements IWootDebug {

    public static final String BASENAME = "cell";
    public TileEntityCell() {
        super(ModTileEntities.cellTileEntityType);
    }

    public int consume(int units) {
        // @todo cell contents processing
        return units;
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityCell");
        debug.add("Has Master: " + iMultiBlockGlue.hasMaster());
        return debug;
    }
}
