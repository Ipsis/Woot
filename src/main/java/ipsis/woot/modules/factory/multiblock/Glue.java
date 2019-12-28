package ipsis.woot.modules.factory.multiblock;

import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class controls how the block is connected to the master block.
 * The master state is set/cleared by the master calling the methods.
 * This class does NOT control its master state.
 */

public class Glue implements MultiBlockGlue {

    private static final Logger LOGGER = LogManager.getLogger();

    private MultiBlockMaster master;
    private TileEntity te;
    private MultiBlockGlueProvider iMultiBlockGlueProvider;

    public Glue(TileEntity te, MultiBlockGlueProvider iMultiBlockGlueProvider) {
        this.te = te;
        this.iMultiBlockGlueProvider = iMultiBlockGlueProvider;
    }

    @Override
    public void clearMaster() {
        if (hasMaster()) {
            LOGGER.debug("clearMaster: {}", te.getPos());
            this.master = null;
            te.getWorld().setBlockState(te.getPos(),
                    te.getBlockState().with(BlockStateProperties.ATTACHED, false), 3);

            WorldHelper.updateClient(te.getWorld(), te.getPos());
            // Update neighbours to trigger possible connection changes eg. power cables
            WorldHelper.updateNeighbours(te.getWorld(), te.getPos());
        }
    }

    @Override
    public void setMaster(MultiBlockMaster master) {
        if (master == null)
            return;

        if (this.master == null || !this.master.equals(master)) {
            LOGGER.debug("setMaster: {} has a new master {}", te.getPos(), master);
            this.master = master;
            te.getWorld().setBlockState(te.getPos(),
                    te.getBlockState().with(BlockStateProperties.ATTACHED, true), 3);
            WorldHelper.updateClient(te.getWorld(), te.getPos());
            // Update neighbours to trigger possible connection changes eg. power cables
            WorldHelper.updateNeighbours(te.getWorld(), te.getPos());
        }
    }

    @Override
    public void onGoodbye() {
        if (hasMaster()) {
            LOGGER.debug("onGoodbye: {} has no master", te.getPos());
            master.interrupt();
        }
    }

    @Override
    public void onHello(World world, BlockPos pos) {
        if (!hasMaster()) {
            LOGGER.debug("onHello: {} find master", te.getPos());
            MultiBlockMaster tmpMaster = GlueHelper.findMaster(world, iMultiBlockGlueProvider);
            if (tmpMaster != null)
                tmpMaster.interrupt();
        }
    }

    @Override
    public boolean hasMaster() {
        return master != null;
    }

    @Override
    public BlockPos getPos() {
        return te.getPos();
    }
}
