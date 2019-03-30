package ipsis.woot.anvil;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TileEntityAnvil extends TileEntity implements IWootDebug {

    public TileEntityAnvil() {
        super(ModTileEntities.anvilTileEntityType);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityAnvil");
        return debug;
    }
}
