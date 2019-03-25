package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TileEntityTotem extends TileEntity implements IWootDebug {

    public static final String BASENAME = "factory_totem";

    private int level;
    public TileEntityTotem() {
        super(ModTileEntities.totemTileEntityType);
        this.level = 1;
    }

    public void setLevel(int level) {
        this.level = level;
        markDirty();
    }

    /**
     * NBT
     */

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> TileEntityTotem");
        debug.add("level " + level);
        return debug;
    }
}
