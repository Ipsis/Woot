package ipsis.woot.factory.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is my workaround for Forge #5883
 *
 * The idea here is that when a MultiBlockGlueProvider is placed/loaded into the world
 * then it will add itself to the block tracker.
 * When the block tracker runs, it allows any entry in its list to try and find the
 * associated master. It only gets one try and then is removed.
 */
public class MultiBlockTracker {

    private static final Logger LOGGER = LogManager.getLogger();

    public static MultiBlockTracker get() { return INSTANCE; }
    static MultiBlockTracker INSTANCE;
    static { INSTANCE = new MultiBlockTracker(); }

    List<BlockPos> blocks = new ArrayList<>();
    // TODO make it dimension specific ??
    public void addEntry(BlockPos pos) {
        LOGGER.debug("Adding entry at {} to block tracker", pos);
        blocks.add(new BlockPos(pos));
    }

    public void run(World world) {

        if (world.isRemote || blocks.isEmpty())
            return;

        LOGGER.debug("Running multiblock tracker");
        Iterator<BlockPos> iter = blocks.iterator();
        while (iter.hasNext()) {
            BlockPos pos = iter.next();
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof MultiBlockGlueProvider) {
                LOGGER.debug("GlueProvider at {} saying hello", pos);
                ((MultiBlockGlueProvider) te).getGlue().onHello(world, pos);
                iter.remove();
            }
        }
    }
}
