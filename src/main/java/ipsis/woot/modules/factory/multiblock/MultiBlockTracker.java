package ipsis.woot.modules.factory.multiblock;

import com.google.common.collect.Lists;
import ipsis.woot.Woot;
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

    private List<MultiBlock> syncBlocks = new ArrayList<>();
    public void addEntry(World world, BlockPos pos) {
        //LOGGER.info("Adding entry {} at {} to block tracker", world.getDimensionKey(), pos);
        syncBlocks.add(new MultiBlock(pos, world));
    }

    public void run(World world) {
        //LOGGER.info("MultiBlockTracker run world={} blocks={}", world.getDimensionKey(), syncBlocks.size());
        if (world.isRemote)
            return;

        List<MultiBlock> helloBlocks = Lists.newArrayList();
        Iterator i = syncBlocks.iterator();
        while (i.hasNext()) {
            MultiBlock m = (MultiBlock)i.next();
            if (m.world == world) {
                //Woot.setup.getLogger().info("run {}: process {} {}", world.getDimensionKey(), m.world.getDimensionKey(), m.pos);
                helloBlocks.add(m);
                i.remove();
            }
        }

        if (helloBlocks.isEmpty())
            return;

        //Woot.setup.getLogger().info("run: {} blocks {}", world.getDimensionKey(), helloBlocks.size());
        for (MultiBlock m : helloBlocks) {
            if (world.isBlockLoaded(m.pos)) {
                //Woot.setup.getLogger().info("run {}: loaded", world.getDimensionKey());
                TileEntity te = world.getTileEntity(m.pos);
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider) te).getGlue().onHello(world, te.getPos());
            } else {
                //Woot.setup.getLogger().info("run {}: not loaded", world.getDimensionKey());
            }
        }
    }

    private static class MultiBlock {
        BlockPos pos;
        World world;

        public MultiBlock(BlockPos pos, World world) {
            this.world = world;
            this.pos = new BlockPos(pos);
        }
    }
}
