package ipsis.woot.util;

import net.minecraft.block.Block;
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

    public static void updateNeighbors(World world, BlockPos pos, Block b) {

        if (world != null) {
            IBlockState iBlockState = world.getBlockState(pos);
            world.notifyNeighborsOfStateChange(pos, b);
        }
    }
}
