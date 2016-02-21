package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

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

    public static BlockPos rotateToSouth(BlockPos blockPos, EnumFacing from) {

        if (from == EnumFacing.EAST)
            return new BlockPos(blockPos.getZ() * -1, blockPos.getY(), blockPos.getX());
        else if (from == EnumFacing.NORTH)
            return new BlockPos(blockPos.getX() * -1, blockPos.getY(), blockPos.getZ() * -1);
        else if (from == EnumFacing.WEST)
            return new BlockPos(blockPos.getZ(), blockPos.getY(), blockPos.getX() * -1);
        else
            return new BlockPos(blockPos);
    }

    public static BlockPos rotateFromSouth(BlockPos blockPos, EnumFacing to) {

        if (to == EnumFacing.EAST)
            return new BlockPos(blockPos.getZ(), blockPos.getY(), blockPos.getX() * -1);
        else if (to == EnumFacing.WEST)
            return new BlockPos(blockPos.getZ() * -1, blockPos.getY(), blockPos.getX());
        else if (to == EnumFacing.NORTH)
            return new BlockPos(blockPos.getX() * -1, blockPos.getY(), blockPos.getZ() * -1);
        else
            return new BlockPos(blockPos);
    }


    public static void writeToNBT(BlockPos p, NBTTagCompound compound) {

        compound.setInteger("xCoord", p.getX());
        compound.setInteger("yCoord", p.getY());
        compound.setInteger("zCoord", p.getZ());
    }

    public static BlockPos readFromNBT(NBTTagCompound compound) {

        return new BlockPos(compound.getInteger("xCoord"), compound.getInteger("yCoord"), compound.getInteger("zCoord"));
    }
}
