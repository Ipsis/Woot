package ipsis.woot.farming;

import net.minecraft.world.World;

public class SimpleTickTracker implements ITickTracker {

    private long lastWorldTime;
    private int structureTicksTimeout;
    private int learnTicksTimeout;
    private int currStructureTicks;
    private int currLearnTicks;

    public SimpleTickTracker() {

        lastWorldTime = -1;
        structureTicksTimeout = 0;
        learnTicksTimeout = 0;
        currStructureTicks = 0;
        currLearnTicks = 0;
    }

    @Override
    public void tick(World world) {

        long currWorldTime = world.getTotalWorldTime();
        if (lastWorldTime != currWorldTime) {
            // Time has passed and this is not a world acceleration call
            lastWorldTime = currWorldTime;

            if (structureTicksTimeout > 0)
                currStructureTicks++;
            if (learnTicksTimeout > 0)
                currLearnTicks++;
        }
    }

    @Override
    public boolean hasLearnTickExpired() {

        return learnTicksTimeout > 0 && currLearnTicks >= learnTicksTimeout;
    }

    @Override
    public boolean hasStructureTickExpired() {

        return structureTicksTimeout > 0 && currStructureTicks >= structureTicksTimeout;
    }

    @Override
    public void setLearnTickCount(int ticks) {

        learnTicksTimeout = ticks;
    }

    @Override
    public void setStructureTickCount(int ticks) {

        structureTicksTimeout = ticks;
    }

    @Override
    public void resetLearnTickCount() {

        currLearnTicks = 0;
    }

    @Override
    public void resetStructureTickCount() {

        currStructureTicks = 0;
    }
}
