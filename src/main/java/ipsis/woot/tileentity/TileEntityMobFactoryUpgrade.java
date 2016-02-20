package ipsis.woot.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryUpgrade extends TileEntity {

    TileEntityMobFarm master = null;
    public boolean hasMaster() { return master != null; }
    public void clearMaster() { master = null; }
    public void setMaster(TileEntityMobFarm master) { this.master = master; }

    TileEntityMobFarm findMaster() {

        List<TileEntityMobFactoryUpgrade> connectedTEs = new ArrayList<TileEntityMobFactoryUpgrade>();
        Stack<TileEntityMobFactoryUpgrade> traversingTEs = new Stack<TileEntityMobFactoryUpgrade>();

        TileEntityMobFarm tmpMaster = null;
        boolean masterFound = false;

        traversingTEs.add(this);
        while (!masterFound && !traversingTEs.isEmpty()) {
            TileEntityMobFactoryUpgrade currTE = traversingTEs.pop();

            connectedTEs.add(currTE);
            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = worldObj.getTileEntity(currTE.getPos().offset(f));
                if (te instanceof TileEntityMobFactoryUpgrade && !connectedTEs.contains(te)) {
                    traversingTEs.add((TileEntityMobFactoryUpgrade)te);
                } else if (te instanceof TileEntityMobFarm) {
                    masterFound = true;
                    tmpMaster = (TileEntityMobFarm)te;
                }
            }
        }

        return tmpMaster;
    }

    public void blockAdded() {

        TileEntityMobFarm tmpMaster = findMaster();
        if (tmpMaster != null)
            tmpMaster.interruptUpgrade();
    }

    @Override
    public void invalidate() {

        if (hasMaster()) {
            master.interruptUpgrade();
        }
    }
}
