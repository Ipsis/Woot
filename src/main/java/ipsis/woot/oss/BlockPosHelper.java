package ipsis.woot.oss;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * These are the functions from BlockPosition in CofhLIB
 */
public class BlockPosHelper {

    public static BlockPos moveRight(BlockPos p, EnumFacing f, int step) {

        switch (f) {
            case UP:
            case SOUTH:
                return p.add(-step, 0, 0);
            case DOWN:
            case NORTH:
                return p.add(step, 0, 0);
            case EAST:
                return p.add(0, 0, step);
            case WEST:
                return p.add(0, 0, -step);
            default:
                break;
        }

        // Not possible
        return null;
    }

    public static BlockPos moveLeft(BlockPos p, EnumFacing f, int step) {

        return moveRight(p, f, -step);
    }

    public static BlockPos moveForwards(BlockPos p, EnumFacing f, int step) {

        switch (f) {
            case UP:
                return p.add(0, step, 0);
            case DOWN:
                return p.add(0, -step, 0);
            case SOUTH:
                return p.add(0, 0, step);
            case NORTH:
                return p.add(0, 0, -step);
            case EAST:
                return p.add(step, 0, 0);
            case WEST:
                return p.add(-step, 0, 0);
            default:
                break;
        }

        // Not possible
        return null;
    }

    public static BlockPos moveBackwards(BlockPos p, EnumFacing f, int step) {

        return moveForwards(p, f, -step);
    }
}
