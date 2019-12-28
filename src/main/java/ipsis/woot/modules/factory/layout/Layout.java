package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.multiblock.MultiBlockMaster;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

/**
 * Defines the layout of the factory which are the block locations
 */
public class Layout {

    private static final Logger LOGGER = LogManager.getLogger();

    World world;
    BlockPos pos;
    Direction facing= Direction.SOUTH;
    AbsolutePattern absolutePattern = null;
    boolean changed = false;
    boolean dirty = false;

    public boolean hasChanged() { return this.changed; }
    public void clearChanged() { this.changed = false;}
    public void setDirty() { this.dirty = true; }
    public boolean isFormed() { return absolutePattern != null; }
    public @Nullable AbsolutePattern getAbsolutePattern() { return this.absolutePattern; }

    public void setLocation(World world, BlockPos pos, Direction facing) {
        this.world = world;
        this.pos = pos;
        this.facing = facing;
    }

    void handleDirty(MultiBlockMaster master) {

        AbsolutePattern rescannedPattern = FactoryScanner.scanForTier(world, pos, facing);
        if (absolutePattern == null && rescannedPattern == null) {
            LOGGER.debug("was:nothing now:nothing");
        } else if (absolutePattern == null && rescannedPattern != null) {
            LOGGER.debug("was:nothing now:{}", rescannedPattern.getTier());
            FactoryHelper.connectNew(world, rescannedPattern, master);
            absolutePattern = rescannedPattern;
            changed = true;
        } else if (absolutePattern != null && rescannedPattern == null) {
            LOGGER.debug("was:{} now:nothing", absolutePattern.getTier());
            FactoryHelper.disconnectOld(world, absolutePattern);
            absolutePattern = null;
            changed = true;
        } else if (absolutePattern != null && rescannedPattern != null) {
            if (!FactoryScanner.isPatternEqual(world, absolutePattern, rescannedPattern)) {
                LOGGER.debug("was:{} now:{}", absolutePattern.getTier(), rescannedPattern.getTier());
                FactoryHelper.disconnectOld(world, absolutePattern);
                FactoryHelper.connectNew(world, rescannedPattern, master);
                absolutePattern = rescannedPattern;
                changed = true;
            }
        }
    }

    public void tick(HeartTileEntity.TickTracker tickTracker, MultiBlockMaster master) {

        if (!tickTracker.hasStructureTickExpired())
            return;

        if (dirty) {
            handleDirty(master);
            tickTracker.resetStructureTickCount();
            dirty = false;
        }
    }

    public void fullDisconnect() {
        if (absolutePattern != null)
            FactoryHelper.disconnectOld(world, absolutePattern);
    }

    @Override
    public String toString() {
        return "facing:" + facing + " pattern:" + (absolutePattern != null ? "yes" : "no") + " changed:" + changed + " dirty:" + dirty;
    }
}
