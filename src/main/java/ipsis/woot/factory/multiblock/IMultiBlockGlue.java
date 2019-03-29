package ipsis.woot.factory.multiblock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiBlockGlue {
    void clearMaster();
    void setMaster(IMultiBlockMaster master);
    void onHello(World world, BlockPos pos);
    void onGoodbye();
    boolean hasMaster();
    BlockPos getPos();
}
