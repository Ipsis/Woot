package ipsis.woot.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryExtender extends TileEntity {

    TileEntityMobFactory master = null;

    public boolean hasMaster() { return master != null; }

    public void setMaster(TileEntityMobFactory master) { this.master = master; }

    TileEntityMobFactory findMaster() {

        TileEntityMobFactory tmpMaster = null;

        TileEntity te = getWorld().getTileEntity(pos.offset(EnumFacing.UP, 1));
        while (te != null && te instanceof TileEntityMobFactoryExtender) {
            pos = pos.up(1);
            te = getWorld().getTileEntity(pos);
        }

        if (te instanceof TileEntityMobFactory)
            tmpMaster = (TileEntityMobFactory)te;

        return tmpMaster;
    }

    public void blockAdded() {

        TileEntityMobFactory tmpMaster = findMaster();
        if (tmpMaster != null)
            tmpMaster.interruptProxy();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            master.interruptProxy();
    }

    public TileEntityMobFactoryExtender() {

    }
}
