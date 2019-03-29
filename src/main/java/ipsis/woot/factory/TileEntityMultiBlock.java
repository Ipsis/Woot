package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.multiblock.*;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public abstract class TileEntityMultiBlock extends TileEntity implements IMultiBlockGlueProvider {

    public TileEntityMultiBlock(TileEntityType tileEntityType) {
        super(tileEntityType);
        iMultiBlockGlue = new Glue(this, this);
    }

    @Override
    public void validate() {
        super.validate();
        if (WorldHelper.isServerWorld(getWorld())) {
            // This must NOT use the TE version
            IMultiBlockMaster iMultiBlockMaster = LocatorHelper.findMasterNoTE(getWorld(), getPos());
            if (iMultiBlockMaster != null)
                iMultiBlockMaster.interrupt();
        }
    }

    @Override
    public void remove() {
        super.remove();
        iMultiBlockGlue.onHello(getWorld(), getPos()); // Why hello??
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        iMultiBlockGlue.onGoodbye();
    }

    /**
     * IMultiBlockGlueProvider
     */
    protected IMultiBlockGlue iMultiBlockGlue;

    @Nonnull
    @Override
    public IMultiBlockGlue getIMultiBlockGlue() {
        return iMultiBlockGlue;
    }
}
