package ipsis.woot.factory.structure;

import ipsis.woot.factory.SimpleTickTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FactoryLayout {

    private World world;
    private BlockPos pos;
    private boolean dirtyLayout = false;

    public void setWorld(World world) {
        this.world = world;
    }

    public void setPos(BlockPos pos) {
        this.pos = new BlockPos(pos);
    }

    public void setDirtyLayout() {
        this.dirtyLayout = true;
    }

    public boolean isFormed() {
        // TODO
        return true;
    }

    private void handleDirtyLayout() {

    }

    public void tick(SimpleTickTracker tickTracker) {

        if (!tickTracker.hasStructureTickExpired())
            return;

        if (dirtyLayout) {
            handleDirtyLayout();
            tickTracker.resetStructureTickCount();;
            dirtyLayout = false;
        }
    }


}
