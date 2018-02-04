package ipsis.woot.farmblocks;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UpgradeMasterLocator implements IFarmBlockMasterLocator {


    @Nullable
    @Override
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFactoryGlueProvider iFactoryGlueProvider) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, "findMaster(Upgrade)", origin);

        List<IFactoryGlueProvider> connected= new ArrayList<>();
        Stack<IFactoryGlueProvider> traversing= new Stack<>();

        IFarmBlockMaster tmpMaster = null;
        boolean masterFound = false;

        traversing.add(iFactoryGlueProvider);
        while (!masterFound && !traversing.isEmpty()) {
            IFactoryGlueProvider curr = traversing.pop();

            connected.add(curr);
            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = world.getTileEntity(curr.getIFactoryGlue().getPos().offset(f));
                if (te instanceof IFactoryGlueProvider && ((IFactoryGlueProvider) te).getIFactoryGlue().getType() == IFactoryGlue.FactoryBlockType.UPGRADE && !connected.contains(te)) {
                    traversing.add((IFactoryGlueProvider) te);
                } else if (te instanceof IFactoryGlueProvider && ((IFactoryGlueProvider) te).getIFactoryGlue().getType() == IFactoryGlue.FactoryBlockType.STRUCTURE && !connected.contains(te)) {
                    traversing.add((IFactoryGlueProvider) te);
                } else if (te instanceof IFarmBlockMaster) {
                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, "IFarmMaster", curr.getIFactoryGlue().getPos().offset(f));
                    masterFound = true;
                    tmpMaster = (IFarmBlockMaster) te;
                }
            }
        }

        return tmpMaster;
    }
}
