package ipsis.woot.blocks;

import ipsis.woot.factory.structure.locator.Glue;
import ipsis.woot.factory.structure.locator.IMultiBlockGlue;
import ipsis.woot.factory.structure.locator.IMultiBlockGlueProvider;
import ipsis.woot.factory.structure.locator.LocatorHelper;
import ipsis.woot.util.WorldHelper;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class TileEntityStructure extends TileEntity implements IMultiBlockGlueProvider {

    private IMultiBlockGlue iMultiBlockGlue;

    public TileEntityStructure() {
        iMultiBlockGlue = new Glue(this, this);
    }

    @Override
    public void validate() {
        super.validate();
        if (WorldHelper.isServerWorld(getWorld())) {
            // This must NOT use the TE version
            TileEntityHeart master = LocatorHelper.findMasterNoTE(getWorld(), getPos());
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
}
