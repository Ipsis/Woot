package ipsis.woot.farmblocks;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ControllerMasterLocator implements IFarmBlockMasterLocator {


    @Nullable
    @Override
    public IFarmBlockMaster findMaster(World world, BlockPos origin, IFarmBlockConnection farmBlockStructure) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, this, "findMaster(Controller)", origin);

        StructureMasterLocator structureMasterLocator = new StructureMasterLocator();

        BlockPos blockPos = origin.down(1);
        TileEntity te = world.getTileEntity(blockPos);
        if (te instanceof IFarmBlockStructure && te instanceof IFarmBlockConnection) {

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.FARM_SCAN, this, "IFarmBlockStructre(Controller)", blockPos);
            return structureMasterLocator.findMaster(world, blockPos, (IFarmBlockConnection) te);
        }

        return null;
    }
}
