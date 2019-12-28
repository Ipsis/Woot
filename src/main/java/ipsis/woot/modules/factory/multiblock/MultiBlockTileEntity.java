package ipsis.woot.modules.factory.multiblock;

import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;

public class MultiBlockTileEntity extends TileEntity implements MultiBlockGlueProvider, WootDebug {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String REGNAME = "multiblock";

    protected MultiBlockGlue glue;

    public MultiBlockTileEntity(TileEntityType type) {
        super(type);
        glue = new Glue(this, this);
    }

    public MultiBlockTileEntity() {
        this(FactorySetup.MULTIBLOCK_BLOCK_TILE.get());
    }

    @Override
    public void validate() {
        super.validate();
        if (!world.isRemote) {
            LOGGER.debug("validate");
            MultiBlockTracker.get().addEntry(pos);
        }
    }

    @Override
    public void remove() {
        super.remove();
        if (!world.isRemote) {
            LOGGER.debug("remove");
            glue.onGoodbye();
        }
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        if (!world.isRemote) {
            LOGGER.debug("onChunkUnloaded");
            glue.onGoodbye();
        }
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
