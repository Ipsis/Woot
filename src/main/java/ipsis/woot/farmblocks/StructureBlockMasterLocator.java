package ipsis.woot.farmblocks;

import ipsis.woot.block.BlockMobFactoryHeart;
import ipsis.woot.block.BlockMobFactoryStructure;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This is used when the chunk that the origin block is in, is still loading.
 * TEs are not valid to call, as they will call the validate method which will just recurse.
 * The only blocks that can find the master will be those on the edge next to a loaded chunk.
 */

public class StructureBlockMasterLocator {

    public IFarmBlockMaster findMaster(World world, BlockPos origin) {

        IFarmBlockMaster tmpMaster = null;
        boolean masterFound = false;

        List<BlockPos> connected = new ArrayList<>();
        Stack<BlockPos> traversing = new Stack<>();

        traversing.add(origin);
        while (!masterFound && !traversing.isEmpty()) {
            BlockPos curr = traversing.pop();

            connected.add(curr);
            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos pos = curr.offset(facing);

                if (world.isBlockLoaded(pos)) {
                    Block b = world.getBlockState(pos).getBlock();
                    if (b instanceof BlockMobFactoryStructure && !connected.contains(pos)) {
                        traversing.add(pos);
                    } else if (b instanceof BlockMobFactoryHeart) {
                        TileEntity te = world.getTileEntity(pos);
                        if (te instanceof IFarmBlockMaster) {
                            masterFound = true;
                            tmpMaster = (IFarmBlockMaster)te;
                        }
                    }
                }
            }
        }

        return tmpMaster;
    }
}
