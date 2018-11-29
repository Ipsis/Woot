package ipsis.woot.blocks.generators;

import ipsis.woot.factory.recipes.IWootUnitProvider;
import ipsis.woot.factory.structure.locator.*;
import ipsis.woot.util.IDebug;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityGenerator extends TileEntity implements IWootUnitProvider, IMultiBlockGlueProvider, IDebug {

    @Override
    public int consume(int units) {
        return units;
    }

    private IMultiBlockGlue iMultiBlockGlue;
    public TileEntityGenerator() {
        iMultiBlockGlue = new Glue(this, this);
    }

    @Override
    public void validate() {
        super.validate();
        if (WorldHelper.isServerWorld(getWorld())) {
            // This must NOT use the TE version
            IMultiBlockMaster master = LocatorHelper.findMasterNoTE(getWorld(), getPos());
            if (master != null)
                master.interrupt();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        iMultiBlockGlue.onHello(getWorld(), getPos());
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        iMultiBlockGlue.onGoodbye();
    }

    @Nonnull
    @Override
    public IMultiBlockGlue getIMultiBlockGlue() {
        return iMultiBlockGlue;
    }

    /**
     * IDebug
     */
    @Override
    public void getDebugText(List<String> debug) {
        debug.add("Has Master:" + iMultiBlockGlue.hasMaster());
    }
}
