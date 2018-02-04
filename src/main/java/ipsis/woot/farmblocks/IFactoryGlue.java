package ipsis.woot.farmblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFactoryGlue {

    void clearMaster();
    void setMaster(IFarmBlockMaster master);
    void onHello(World world, BlockPos pos);
    void onGoodbye();
    boolean hasMaster();
    BlockPos getPos();

    FactoryBlockType getType();

    enum FactoryBlockType {
        STRUCTURE,
        CONTROLLER,
        UPGRADE,
        CELL,
        IMPORTER,
        EXPORTER
    }
}
