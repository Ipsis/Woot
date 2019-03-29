package ipsis.woot.factory.heart;

import net.minecraft.world.World;

public class TickTracker {

    private long lastGameTime = -1;
    private int structureTicksTimeout = 0;
    private int currStructureTicks = 0;

    public boolean tick(World world) {

        // @todo config for tick acceleration

        boolean realTick = false;
        long currGameTime = world.getGameTime();
        if (lastGameTime != currGameTime) {
            // actual time has passed - no acceleration
            lastGameTime = currGameTime;
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
