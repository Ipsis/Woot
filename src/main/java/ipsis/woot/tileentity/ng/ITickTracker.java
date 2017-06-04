package ipsis.woot.tileentity.ng;

import net.minecraft.world.World;

public interface ITickTracker {

    void tick(World world);
    boolean hasLearnTickExpired();
    boolean hasStructureTickExpired();
    void setLearnTickCount(int ticks);
    void setStructureTickCount(int ticks);
    void resetLearnTickCount();
    void resetStructureTickCount();
}
