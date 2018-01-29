package ipsis.woot.farmblocks;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Walks upwards until it finds the master
 */
public class SimpleMasterLocator implements IFarmBlockMasterLocator {

    @Nullable
    @Override
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFactoryGlueProvider iFactoryGlueProvider) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, "findMaster(Proxy)", origin);

        IFarmBlockMaster tmpMaster = null;

        for (int step = 0; step < 10; step++) {

            BlockPos blockPos = origin.up(step + 1);
            TileEntity te = world.getTileEntity(blockPos);
            if (te instanceof IFarmBlockMaster) {
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, "IFarmMaster", blockPos);
                tmpMaster = (IFarmBlockMaster) te;
            }
        }

        return tmpMaster;
    }
}
