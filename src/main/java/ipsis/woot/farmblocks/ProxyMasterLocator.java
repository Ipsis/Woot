package ipsis.woot.farmblocks;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ProxyMasterLocator implements IFarmBlockMasterLocator {

    @Nullable
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFarmBlockConnection farmBlockStructure) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, this, "findMaster(Proxy)", origin);

        IFarmBlockMaster tmpMaster = null;

        BlockPos blockPos = origin.up(1);
        TileEntity te = world.getTileEntity(blockPos);
        while (te instanceof IFarmBlockProxy && ((IFarmBlockProxy) te).isExtender()) {

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, this, "IFarmBlockProxy(Extender)", blockPos);
            blockPos = blockPos.up(1);
            te = world.getTileEntity(blockPos);
        }

        if (te instanceof IFarmBlockMaster) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, this, "IFarmMaster", blockPos);
            tmpMaster = (IFarmBlockMaster) te;
        }

        return tmpMaster;
    }
}
