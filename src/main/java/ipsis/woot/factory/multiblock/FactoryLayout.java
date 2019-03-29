package ipsis.woot.factory.multiblock;

import ipsis.woot.factory.heart.BlockHeart;
import ipsis.woot.factory.heart.TickTracker;
import ipsis.woot.factory.layout.AbsolutePattern;
import ipsis.woot.tools.FactoryHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FactoryLayout {

    private boolean dirty = false;
    public void setDirty() { this.dirty = true; }

    private AbsolutePattern absolutePattern = null;
    public boolean isFormed() { return absolutePattern != null; }
    public @Nullable AbsolutePattern getAbsolutePattern() { return this.absolutePattern; }

    private World world;
    private BlockPos pos;
    private EnumFacing facing = EnumFacing.SOUTH;
    public void setWorldPos(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.facing = world.getBlockState(pos).get(BlockHeart.FACING);
    }

    private boolean changed = false;
    public boolean hasChanged() { return this.changed; }

    private void handleDirty() {

        AbsolutePattern rescannedPattern = FactoryScanner.scan(world, pos, facing);
        if (absolutePattern == null && rescannedPattern == null) {
            // was nothing and there is still nothing
        } else if (absolutePattern == null && rescannedPattern != null) {
            // there was nothing and now there is something
            FactoryHelper.connectNew(world, rescannedPattern, (IMultiBlockMaster)world.getTileEntity(pos));
            absolutePattern = rescannedPattern;
            changed = true;
        } else if (absolutePattern != null && rescannedPattern == null) {
            // there was something and now this is nothing
            FactoryHelper.disconnectOld(world, absolutePattern);
            absolutePattern = null;
            changed = true;
        } else if (absolutePattern != null && rescannedPattern != null)  {
            // was something but now there might be something different
            FactoryHelper.disconnectOld(world, absolutePattern);
            FactoryHelper.connectNew(world, rescannedPattern, (IMultiBlockMaster)world.getTileEntity(pos));
            absolutePattern = rescannedPattern;
            changed = true;
        }
    }

    public void tick(TickTracker tickTracker) {

        if (!tickTracker.hasStructureTickExpired())
            return;

        if (dirty) {
            handleDirty();
            tickTracker.resetStructureTickCount();
            dirty = false;
        }
    }

}
