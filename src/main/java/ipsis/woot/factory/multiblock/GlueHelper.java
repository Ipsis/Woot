package ipsis.woot.factory.multiblock;

import ipsis.woot.factory.blocks.HeartBlock;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GlueHelper {

    private static boolean isGlueBlock(Block b) {
        return b instanceof MultiBlockGlueProvider;
    }

    private static boolean isMaster(Block b) {
        return b instanceof HeartBlock;
    }

    /**
     * This is safe to use when the chunk that the origin block is in, is still loading.
     * TEs are not valid to call, as they will call the validate method which will just recurse.
     * The only blocks that can find the master will be those on the edge next to a loaded chunk.
     */
    public static MultiBlockMaster findMasterNoTE(World world, BlockPos origin) {

        List<BlockPos> connected = new ArrayList<>();
        Stack<BlockPos> traversing = new Stack<>();

        MultiBlockMaster master = null;

        traversing.add(origin);
        while (master == null && !traversing.isEmpty()) {
            BlockPos currPos = traversing.pop();
            connected.add(currPos);
            for (Direction facing : Direction.values()) {
                BlockPos pos = currPos.offset(facing);
                if (world.isBlockLoaded(pos)) {
                    Block b = world.getBlockState(pos).getBlock();
                    if (isGlueBlock(b) && !connected.contains(pos)) {
                        traversing.add(pos);
                    } else if (isMaster(b)) {
                        TileEntity te = world.getTileEntity(pos);
                        if (te instanceof MultiBlockMaster) {
                            master = (MultiBlockMaster)te;
                        }
                    }
                }
            }
        }

        return master;
    }
}
