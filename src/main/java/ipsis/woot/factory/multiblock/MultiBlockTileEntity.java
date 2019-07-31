package ipsis.woot.factory.multiblock;

import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import java.util.List;

public class MultiBlockTileEntity extends TileEntity implements MultiBlockGlueProvider, WootDebug {

    public static final String REGNAME = "multiblock";

    protected MultiBlockGlue glue;

    public MultiBlockTileEntity(TileEntityType type) {
        super(type);
        glue = new Glue(this, this);
    }

    public MultiBlockTileEntity() {
        this(ModBlocks.MULTIBLOCK_BLOCK_TILE);
    }

    @Override
    public void validate() {
        super.validate();
        if (!world.isRemote)
            MultiBlockTracker.get().addEntry(pos);
    }

    @Override
    public void remove() {
        super.remove();
        glue.onGoodbye();
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        glue.onGoodbye();
    }

    @Nonnull
    @Override
    public MultiBlockGlue getGlue() {
        return glue;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> MultiBlockTileEntity");
        debug.add("      hasMaster: " + glue.hasMaster());
        return debug;
    }
}
