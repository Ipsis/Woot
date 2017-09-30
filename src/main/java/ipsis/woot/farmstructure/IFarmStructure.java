package ipsis.woot.farmstructure;

import ipsis.woot.farming.ITickTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IFarmStructure {

    void setStructureDirty();
    void setUpgradeDirty();
    void fullDisconnect();

    IFarmStructure setWorld(@Nonnull World world);
    IFarmStructure setPosition(BlockPos origin);

    void tick(ITickTracker tickTracker);
    IFarmSetup createSetup();

    boolean isFormed();
    boolean hasChanged();
    void clearChanged();


}
