package ipsis.woot.factory.power;

import ipsis.woot.Woot;
import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.TileEntityMultiBlock;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;

import java.util.List;
public abstract class TileEntityCell extends TileEntityMultiBlock implements IWootDebug {

    public TileEntityCell(TileEntityType type) {
        super(type);
    }

    public int consume(int extractUnits) {
        int extracted = Math.min(stored, Math.min(Integer.MAX_VALUE, extractUnits));
        stored -= extracted;
        markDirty();
        return extracted;
    }

    public int fill(int receivedUnits) {
        int received = Math.min(capacity - stored, Math.min(receivedUnits, Integer.MAX_VALUE));
        stored += received;
        markDirty();
        return received;
    }

    /**
     * NBT
     */
    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    public void readRestorableFromNBT(NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("stored")) {
            stored = nbtTagCompound.getInt("stored");
        }
    }

    public void writeRestorableToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInt("stored", stored);
    }

    /**
     * Storage
     */
    private int stored = 0;
    protected int capacity = 0;

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("=====> TileEntityCell");
        debug.add("Has Master: " + iMultiBlockGlue.hasMaster());
        debug.add("capacity:" + capacity + "/stored:" + stored);
        return debug;
    }
}
