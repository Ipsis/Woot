package ipsis.woot.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMobFactoryUpgrade extends TileEntity {

    TileEntityMobFarm master;
    public boolean hasMaster() { return master != null; }
    public void clearMaster() { master = null; }

    public void blockAdded() {

        //findMaster();
        if (hasMaster())
            master.nudge();
    }

    @Override
    public void invalidate() {

        if (hasMaster()) {
            master.nudge();
        }
    }
}
