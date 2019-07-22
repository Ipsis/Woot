package ipsis.woot.util.helper;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
/**
 * South +Z
 * North -Z
 * East +X
 * West -X
 *
 * When facing south, left is east (+z), right is west (-z)
 *
 * These functions are only to support rotating the factory patterns which are all based around facing south
 */
public class BlockPosHelper {

    public static BlockPos rotateToSouth(BlockPos blockPos, Direction from) {

        if (from == Direction.EAST)
            return new BlockPos(blockPos.getZ() * -1, blockPos.getY(), blockPos.getX());
        else if (from == Direction.NORTH)
            return new BlockPos(blockPos.getX() * -1, blockPos.getY(), blockPos.getZ() * -1);
        else if (from == Direction.WEST)
            return new BlockPos(blockPos.getZ(), blockPos.getY(), blockPos.getX() * -1);
        else
            return new BlockPos(blockPos);
    }

    public static BlockPos rotateFromSouth(BlockPos blockPos, Direction to) {

        if (to == Direction.EAST)
            return new BlockPos(blockPos.getZ(), blockPos.getY(), blockPos.getX() * -1);
        else if (to == Direction.WEST)
            return new BlockPos(blockPos.getZ() * -1, blockPos.getY(), blockPos.getX());
        else if (to == Direction.NORTH)
            return new BlockPos(blockPos.getX() * -1, blockPos.getY(), blockPos.getZ() * -1);
        else
            return new BlockPos(blockPos);
    }
}
