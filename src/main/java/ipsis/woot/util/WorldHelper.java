package ipsis.woot.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {

    public static void updateClient(World world, BlockPos pos) {

        if (world != null) {
            IBlockState iblockstate = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, iblockstate, iblockstate, 4);
        }
    }
}
