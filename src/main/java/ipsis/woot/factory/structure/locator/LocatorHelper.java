package ipsis.woot.factory.structure.locator;

import ipsis.woot.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LocatorHelper {

    private static boolean isGlueBlock(Block b) {
        return b instanceof BlockStructure || b instanceof BlockExport || b instanceof BlockImport || b instanceof BlockController || b instanceof BlockPower;
    }

    private static boolean isMaster(Block b) {
        return b instanceof BlockHeart;
    }

    /**
     * This is used when the chunk that the origin block is in, is still loading.
     * TEs are not valid to call, as they will call the validate method which will just recurse.
     * The only blocks that can find the master will be those on the edge next to a loaded chunk.
     */
    public static IMultiBlockMaster findMasterNoTE(World world, BlockPos origin) {

        List<BlockPos> connected = new ArrayList<>();
        Stack<BlockPos> traversing = new Stack<>();

        IMultiBlockMaster master = null;

        traversing.add(origin);
        while (master == null && !traversing.isEmpty()) {
            BlockPos currPos = traversing.pop();
            connected.add(currPos);
            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos pos = currPos.offset(facing);
                if (world.isBlockLoaded(pos)) {
                    Block b = world.getBlockState(pos).getBlock();
                    if (isGlueBlock(b) && !connected.contains(pos)) {
                        traversing.add(pos);
                    } else if (isMaster(b)) {
                        TileEntity te = world.getTileEntity(pos);
                        if (te instanceof IMultiBlockMaster) {
                            master = (IMultiBlockMaster)te;
                        }
                    }
                }

            }
        }

        return master;
    }

    public static IMultiBlockMaster findMaster(World world, IMultiBlockGlueProvider iMultiBlockGlueProvider) {

        List<IMultiBlockGlueProvider> connected = new ArrayList<>();
        Stack<IMultiBlockGlueProvider> traversing = new Stack<>();

        IMultiBlockMaster master = null;

        traversing.add(iMultiBlockGlueProvider);
        while (master == null && !traversing.isEmpty()) {
            IMultiBlockGlueProvider curr = traversing.pop();
            connected.add(curr);
            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos pos = curr.getIMultiBlockGlue().getPos().offset(facing);
                if (world.isBlockLoaded(pos)) {
                    TileEntity te = world.getTileEntity(curr.getIMultiBlockGlue().getPos().offset(facing));
                    if (te instanceof  IMultiBlockGlueProvider && !connected.contains(te)) {
                        traversing.add((IMultiBlockGlueProvider)te);
                    } else if (te instanceof IMultiBlockMaster) {
                        master = (IMultiBlockMaster) te;
                    }
                }
            }
        }

        return master;
    }
}
