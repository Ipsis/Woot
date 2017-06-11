package ipsis.woot.tileentity.ng.farmblocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ControllerMasterLocator implements IFarmBlockMasterLocator {


    @Nullable
    @Override
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFarmBlockConnection farmBlockStructure) {

        StructureMasterLocator structureMasterLocator = new StructureMasterLocator();

        BlockPos blockPos = origin.down(1);
        TileEntity te = world.getTileEntity(blockPos);
        if (te instanceof IFarmBlockStructure && te instanceof IFarmBlockConnection)
            return structureMasterLocator.findMaster(world, blockPos, (IFarmBlockConnection) te);

        return null;
    }
}
