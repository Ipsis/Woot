package ipsis.woot.tileentity.ng.farmstructure;

import ipsis.woot.tileentity.ng.IFarmSetup;
import ipsis.woot.tileentity.ng.ITickTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public interface IFarmStructure {

    void setStructureDirty();
    void setUpgradeDirty();
    void setProxyDirty();

    IFarmStructure setWorld(@Nonnull World world);
    IFarmStructure setPosition(BlockPos origin);

    void tick(ITickTracker tickTracker);
    IFarmSetup createSetup();

    boolean isFormed();
    boolean hasChanged();
    void clearChanged();

    @Nonnull List<IFluidHandler> getConnectedTanks();
    @Nonnull List<IItemHandler> getConnectedChests();

}
