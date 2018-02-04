package ipsis.woot.loot;

import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.ITickTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILootLearner {

    void tick(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup);
}
