package ipsis.woot.util;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class BlockPosHelper {

    public static BlockPos rotateFromSouth(BlockPos blockPos, Direction to) {

        Rotation rotation = Rotation.NONE;
        if (to == Direction.EAST)
            rotation = Rotation.COUNTERCLOCKWISE_90;
        else if (to == Direction.WEST)
            rotation = Rotation.CLOCKWISE_90;
        else if (to == Direction.NORTH)
            rotation = Rotation.CLOCKWISE_180;
        return blockPos.rotate(rotation);
    }
}
