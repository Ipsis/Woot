package ipsis.woot.modules.factory.multiblock;

import com.google.common.collect.Lists;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This is my workaround for Forge #5883
 *
 * The idea here is that when a MultiBlockGlueProvider is placed/loaded into the world
 * then it will add itself to the block tracker.
 * When the block tracker runs, it allows any entry in its list to try and find the
 * associated master. It only gets one try and then is removed.
 *
 * There have been bug reports of a ConcurrentModification exception with run.
 * I cannot understand why since they are all on the server thread, but I have a feeling
 * it is something to do with the world tick based MultiBlockTracker.run walking/remove from the
 * list and the MultiBlockTracker.addEntry adding to the list. So I'm switching over
 * to synchronizedList
 *
 * There is a good possibility that the onHello is actually triggering (somehow) the addEntry,
 * but I just cannot find that path, so there is some attempt at protecting that as well.
 */
public class MultiBlockTracker {

    private static final Logger LOGGER = LogManager.getLogger();

    public static MultiBlockTracker get() { return INSTANCE; }
    static MultiBlockTracker INSTANCE;
    static { INSTANCE = new MultiBlockTracker(); }

    private List<BlockPos> syncBlocks = Collections.synchronizedList(Lists.newArrayList());
    public void addEntry(BlockPos pos) {
        //LOGGER.debug("Adding entry at {} to block tracker", pos);
        synchronized ( syncBlocks) {
            syncBlocks.add(new BlockPos(pos));
        }
    }

    public void run(World world) {
        //LOGGER.debug("MultiBlockTracker run remote={} blocks={}", world.isRemote, syncBlocks.size());
        if (world.isRemote)
            return;

        List<BlockPos> helloBlocks = Lists.newArrayList();
        synchronized ( syncBlocks) {
            helloBlocks.addAll(syncBlocks);
            syncBlocks.clear();
        }

        for (BlockPos pos : helloBlocks) {
            if (world.isBlockLoaded(pos)) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider) te).getGlue().onHello(world, te.getPos());
            }
        }
    }
}
