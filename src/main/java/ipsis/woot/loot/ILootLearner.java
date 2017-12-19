package ipsis.woot.loot;

import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.ITickTracker;
import ipsis.woot.loot.schools.ILootSchool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public interface ILootLearner {

    void tick(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup);
}
