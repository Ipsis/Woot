package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemUseContext;

import java.util.List;

public class TileEntityExport extends TileEntityMultiBlock implements IWootDebug {

    public TileEntityExport() {
        super(ModTileEntities.exportTileEntityType);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityExport");
        debug.add("Has Master: " + iMultiBlockGlue.hasMaster());
        return debug;
    }
}
