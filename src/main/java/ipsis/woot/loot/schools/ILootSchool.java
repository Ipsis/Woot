package ipsis.woot.loot.schools;

import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILootSchool {

    void tick(World world, BlockPos origin, IFarmSetup farmSetup);
}
