package ipsis.woot.tileentity.ng.farmblocks;

import net.minecraft.util.math.BlockPos;

public interface IFarmBlockConnection {

    void setMaster(IFarmBlockMaster master);
    void clearMaster();
    BlockPos getStructurePos();
}
