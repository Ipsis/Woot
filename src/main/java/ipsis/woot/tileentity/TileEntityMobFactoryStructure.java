package ipsis.woot.tileentity;

import ipsis.oss.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryStructure extends TileEntity  {

    TileEntityMobFarm master;
    public boolean hasMaster() { return master != null; }
    public void clearMaster() { master = null; }

    public void findMaster() {

        TileEntityMobFarm oldMaster = master;

        List<TileEntityMobFactoryStructure> connectedTEs = new ArrayList<TileEntityMobFactoryStructure>();
        Stack<TileEntityMobFactoryStructure> traversingTEs = new Stack<TileEntityMobFactoryStructure>();

        boolean masterFound = false;

        traversingTEs.add(this);
        while (!masterFound && !traversingTEs.isEmpty()) {
            TileEntityMobFactoryStructure currTE = traversingTEs.pop();

            connectedTEs.add(currTE);
            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = worldObj.getTileEntity(currTE.getPos().offset(f));
                if (te instanceof TileEntityMobFactoryStructure && !connectedTEs.contains(te)) {
                    traversingTEs.add((TileEntityMobFactoryStructure)te);
                } else if (te instanceof TileEntityMobFactory) {
                    masterFound = true;
                    master = (TileEntityMobFarm)te;
                }
            }
        }

        if (oldMaster != null && oldMaster != master)
            LogHelper.info("findMaster: changed master TE");
    }

    public void blockAdded() {

        findMaster();
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
