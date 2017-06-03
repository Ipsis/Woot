package ipsis.woot.tileentity.ng;

import net.minecraft.world.World;

public class SimpleTickTracker implements ITickTracker {

    private World world;
    private long lastWorldTime = -1;
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

    public void setWorld(World world) {

        this.world = world;
    }

    @Override
    public void tick() {

        long currWorldTime = world.getTotalWorldTime();
        if (lastWorldTime != currWorldTime) {
            // Time has passed and this is not a world acceleration call
            currWorldTime = lastWorldTime;

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
