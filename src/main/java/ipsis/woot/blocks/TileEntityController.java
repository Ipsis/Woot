package ipsis.woot.blocks;

import ipsis.woot.factory.structure.locator.*;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import ipsis.woot.util.IDebug;
import ipsis.woot.util.IRestorableTileEntity;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityController extends TileEntity implements IDebug, IRestorableTileEntity, IMultiBlockGlueProvider {

    private FakeMobKey fakeMobKey = new FakeMobKey();
    private IMultiBlockGlue iMultiBlockGlue;

    public TileEntityController() {
        super();
        iMultiBlockGlue = new Glue(this, this);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }


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


    /**
     * IDebug
     */
    @Override
    public void getDebugText(List<String> debug) {
        debug.add(fakeMobKey.toString());
        debug.add("Has Master:" + iMultiBlockGlue.hasMaster());
    }

    /**
     * IRestorableTileEntity
     */
    @Override
    public void writeRestorableToNBT(NBTTagCompound nbtTagCompound) {
        FakeMobKey.writeToNBT(fakeMobKey, nbtTagCompound);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound nbtTagCompound) {
        fakeMobKey = FakeMobKeyFactory.createFromNBT(nbtTagCompound);
    }

    /**
     * IMultiBlockGlueProvider
     */
    @Nonnull
    @Override
    public IMultiBlockGlue getIMultiBlockGlue() {
        return iMultiBlockGlue;
    }
}
