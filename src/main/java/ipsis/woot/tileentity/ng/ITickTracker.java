package ipsis.woot.tileentity.ng;

import net.minecraft.world.World;

public interface ITickTracker {

    void setWorld(World world);
    void tick();
    boolean hasLearnTickExpired();
    boolean hasStructureTickExpired();
    void setLearnTickCount(int ticks);
    void setStructureTickCount(int ticks);
    void resetLearnTickCount();
    void resetStructureTickCount();
}
