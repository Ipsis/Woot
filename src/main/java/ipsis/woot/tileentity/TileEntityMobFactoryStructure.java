package ipsis.woot.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryStructure extends TileEntity  {

    TileEntityMobFarm master = null;
    public boolean hasMaster() { return master != null; }
    public void clearMaster() { master = null; }
    public void setMaster(TileEntityMobFarm master) { this.master = master; }

    TileEntityMobFarm findMaster() {

        List<TileEntityMobFactoryStructure> connectedTEs = new ArrayList<TileEntityMobFactoryStructure>();
        Stack<TileEntityMobFactoryStructure> traversingTEs = new Stack<TileEntityMobFactoryStructure>();

        TileEntityMobFarm tmpMaster = null;
        boolean masterFound = false;

        traversingTEs.add(this);
        while (!masterFound && !traversingTEs.isEmpty()) {
            TileEntityMobFactoryStructure currTE = traversingTEs.pop();

            connectedTEs.add(currTE);
            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = worldObj.getTileEntity(currTE.getPos().offset(f));
                if (te instanceof TileEntityMobFactoryStructure && !connectedTEs.contains(te)) {
                    traversingTEs.add((TileEntityMobFactoryStructure)te);
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
            tmpMaster.interruptStructure();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster()) {
            master.interruptStructure();
        }
    }

}
