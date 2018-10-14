package ipsis.woot.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {

    public static boolean isClientWorld(World world) {
        return world != null && world.isRemote;
    }

    public static boolean isServerWorld(World world) {
        return !isClientWorld(world);
    }

    public static void updateClient(World world, BlockPos pos) {

        if (world == null)
            return;

        IBlockState iBlockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, iBlockState, iBlockState, 4);
    }

    public static void updateNeighbors(World world, BlockPos pos, Block b) {

        if (world == null)
            return;

        world.notifyNeighborsOfStateChange(pos, b, false);
    }
}
