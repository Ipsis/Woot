package ipsis.woot.factory;

import net.minecraft.world.World;

public class SimpleTickTracker {

    private long lastWorldTime = -1;
    private int structureTicksTimeout = 0;
    private int currStructureTicks = 0;

    public boolean tick(World world) {

        boolean realTick = false;
        long currWorldTime = world.getWorldTime();
        if (lastWorldTime != currWorldTime) {
            // actual time has passed - no acceleration
            lastWorldTime = currWorldTime;
            realTick = true;

            if (structureTicksTimeout > 0)
                currStructureTicks++;
        }

        return realTick;
    }

    public boolean hasStructureTickExpired() {
        return structureTicksTimeout > 0 && currStructureTicks >= structureTicksTimeout;
    }

    public void setStructureTickCount(int ticks) {
        structureTicksTimeout = ticks;
    }

    public void resetStructureTickCount() {
        currStructureTicks = 0;
    }

}
