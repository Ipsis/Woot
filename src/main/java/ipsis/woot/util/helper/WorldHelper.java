package ipsis.woot.util.helper;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {

    public static void updateClient(World world, BlockPos pos) {
        if (world != null) {
            BlockState blockState = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, blockState, blockState, 4);
        }
    }
}
