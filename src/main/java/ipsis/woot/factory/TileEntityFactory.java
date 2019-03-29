package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.multiblock.*;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemUseContext;

import java.util.List;

public class TileEntityFactory extends TileEntityMultiBlock implements IWootDebug {

    public static final String BASENAME = "factory";
    public TileEntityFactory() {
        super(ModTileEntities.factoryTileEntityType);
        iMultiBlockGlue = new Glue(this, this);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityFactory");
        debug.add("Has Master: " + iMultiBlockGlue.hasMaster());
        return debug;
    }
}
