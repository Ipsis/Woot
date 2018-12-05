package ipsis.woot.factory.structure;

import ipsis.Woot;
import ipsis.woot.blocks.heart.BlockHeart;
import ipsis.woot.factory.SimpleTickTracker;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.util.Debug;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FactoryLayout {

    private World world;
    private BlockPos pos;
    private EnumFacing facing = EnumFacing.SOUTH;
    private boolean dirtyLayout = false;
    private ScannedPattern scanned = null;
    private boolean changed = false;

    public void setWorldPos(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.facing = world.getBlockState(pos).getValue(BlockHeart.FACING);
    }

    public void setDirtyLayout() {
        this.dirtyLayout = true;
    }

    public boolean isFormed() {
        return scanned != null;
    }

    public boolean hasChanged() { return this.changed; }
    public void clearChanged() { this.changed = false; }
    public void setChanged() { this.changed = true; }

    public @Nullable ScannedPattern getScanned() { return this.scanned; }

    private void handleDirtyLayout() {

        /**
         * Rescan the factory and compare against the current
         */
        Woot.debugging.trace(Debug.Group.BUILDING, "Scan factory " + pos + " " + facing);
        ScannedPattern rescanned = FactoryScanner.scan(world, pos, facing);
        if (scanned == null && rescanned == null) {
            // was nothing and there still is nothing
        } else if (scanned == null && rescanned != null) {
            // was nothing and now there is something
            Woot.debugging.trace(Debug.Group.BUILDING, "Hello new factory");
            FactoryBuilder.connectNew(world, pos, rescanned);
            scanned = rescanned;
            setChanged();
        } else if (scanned != null && rescanned == null) {
            // was something and now there is nothing
            Woot.debugging.trace(Debug.Group.BUILDING, "Goodbye factory");
            FactoryBuilder.disconnectOld(world, pos, scanned);
            scanned = null;
            setChanged();
        } else if ((scanned != null && rescanned != null)) {
           // was something and there still is something but it might be different
            Woot.debugging.trace(Debug.Group.BUILDING, "Factory changed");
            FactoryBuilder.disconnectOld(world, pos, scanned);
            FactoryBuilder.connectNew(world, pos, rescanned);
            scanned = rescanned;
            setChanged();
        }

    }

    public void tick(SimpleTickTracker tickTracker) {

        if (!tickTracker.hasStructureTickExpired())
            return;

        if (dirtyLayout) {
            handleDirtyLayout();
            tickTracker.resetStructureTickCount();
            dirtyLayout = false;
        }
    }


}
