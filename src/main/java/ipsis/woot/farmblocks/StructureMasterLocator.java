package ipsis.woot.farmblocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StructureMasterLocator implements IFarmBlockMasterLocator {

    @Nullable
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFactoryGlueProvider iFactoryGlueProvider) {

        List<IFactoryGlueProvider> connected = new ArrayList<>();
        Stack<IFactoryGlueProvider> traversing = new Stack<>();

        IFarmBlockMaster tmpMaster = null;
        boolean masterFound = false;

        traversing.add(iFactoryGlueProvider);
        while (!masterFound && !traversing.isEmpty()) {
            IFactoryGlueProvider curr = traversing.pop();

            connected.add(curr);
            for (EnumFacing facing : EnumFacing.values()) {

                BlockPos pos = curr.getIFactoryGlue().getPos().offset(facing);
                if (world.isBlockLoaded(pos)) {
                    TileEntity te = world.getTileEntity(curr.getIFactoryGlue().getPos().offset(facing));
                    if (te instanceof IFactoryGlueProvider && !connected.contains(te)) {
                        IFactoryGlueProvider provider = (IFactoryGlueProvider) te;
                        if (provider.getIFactoryGlue().getType() == IFactoryGlue.FactoryBlockType.STRUCTURE)
                            traversing.add((IFactoryGlueProvider) te);
                    } else if (te instanceof IFarmBlockMaster) {
                        masterFound = true;
                        tmpMaster = (IFarmBlockMaster) te;
                    }
                }
            }
        }

        return tmpMaster;
    }
}
