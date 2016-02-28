package ipsis.woot.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryUpgrade extends TileEntity {

    TileEntityMobFactory master = null;
    boolean clientHasMaster;

    private static final int SET_MASTER = 0;
    public boolean isClientHasMaster() { return clientHasMaster; }

    public boolean hasMaster() { return master != null; }
    public void clearMaster() {
        master = null;
        worldObj.addBlockEvent(this.getPos(), this.getBlockType(), SET_MASTER, 0);
    }
    public void setMaster(TileEntityMobFactory master) {
        this.master = master;
        worldObj.addBlockEvent(this.getPos(), this.getBlockType(), SET_MASTER, 1);
    }

    TileEntityMobFactory findMaster() {

        List<TileEntityMobFactoryUpgrade> connectedTEs = new ArrayList<TileEntityMobFactoryUpgrade>();
        Stack<TileEntityMobFactoryUpgrade> traversingTEs = new Stack<TileEntityMobFactoryUpgrade>();

        TileEntityMobFactory tmpMaster = null;
        boolean masterFound = false;

        traversingTEs.add(this);
        while (!masterFound && !traversingTEs.isEmpty()) {
            TileEntityMobFactoryUpgrade currTE = traversingTEs.pop();

            connectedTEs.add(currTE);
            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = worldObj.getTileEntity(currTE.getPos().offset(f));
                if (te instanceof TileEntityMobFactoryUpgrade && !connectedTEs.contains(te)) {
                    traversingTEs.add((TileEntityMobFactoryUpgrade)te);
                } else if (te instanceof TileEntityMobFactory) {
                    masterFound = true;
                    tmpMaster = (TileEntityMobFactory)te;
                }
            }
        }

        return tmpMaster;
    }

    public void blockAdded() {

        TileEntityMobFactory tmpMaster = findMaster();
        if (tmpMaster != null)
            tmpMaster.interruptUpgrade();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster()) {
            master.interruptUpgrade();
        }
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (worldObj.isRemote) {
            if (id == SET_MASTER) {
                if (type == 1)
                    clientHasMaster = true;
                else
                    clientHasMaster = false;
            }
            worldObj.markBlockForUpdate(pos);
        }

        return true;
    }
}
