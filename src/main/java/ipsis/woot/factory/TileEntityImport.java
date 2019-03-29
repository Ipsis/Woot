package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemUseContext;

import java.util.List;

public class TileEntityImport extends TileEntityMultiBlock implements IWootDebug {

    public TileEntityImport() {
        super(ModTileEntities.importTileEntityType);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityImport");
        debug.add("Has Master: " + iMultiBlockGlue.hasMaster());
        return debug;
    }
}
