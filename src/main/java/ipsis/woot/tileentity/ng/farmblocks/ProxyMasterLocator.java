package ipsis.woot.tileentity.ng.farmblocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ProxyMasterLocator implements IFarmBlockMasterLocator {

    @Nullable
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFarmBlockConnection farmBlockStructure) {

        IFarmBlockMaster tmpMaster = null;

        BlockPos blockPos = origin.up(1);
        TileEntity te = world.getTileEntity(blockPos);
        while (te != null && te instanceof IFarmBlockProxy && ((IFarmBlockProxy) te).isExtender()) {
            blockPos = blockPos.up(1);
            te = world.getTileEntity(blockPos);

        }

        if (te instanceof IFarmBlockMaster)
            tmpMaster = (IFarmBlockMaster)te;

        return tmpMaster;
    }
}
