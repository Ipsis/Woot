package ipsis.woot.modules.factory.multiblock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface MultiBlockGlue {

    void clearMaster();
    void setMaster(MultiBlockMaster master);
    void onHello(World world, BlockPos pos);
    void onGoodbye();
    boolean hasMaster();
    BlockPos getPos();
}
