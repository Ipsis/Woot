package ipsis.woot.factory.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiBlockTracker {

    public static MultiBlockTracker get() { return INSTANCE; }
    static MultiBlockTracker INSTANCE;
    static { INSTANCE = new MultiBlockTracker(); }

    List<BlockPos> blocks = new ArrayList<>();
    // TODO make it dimension specific ??
    public void addEntry(BlockPos pos) {
        blocks.add(new BlockPos(pos));
    }

    public void run(World world) {
        Iterator<BlockPos> iter = blocks.iterator();
        while (iter.hasNext()) {
            BlockPos pos = iter.next();
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof MultiBlockGlueProvider) {
                ((MultiBlockGlueProvider) te).getGlue().onHello(world, pos);
                iter.remove();
            }
        }
    }
}
