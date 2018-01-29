package ipsis.woot.farmblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IFarmBlockMasterLocator {

    @Nullable  IFarmBlockMaster findMaster(World world, BlockPos origin, IFactoryGlueProvider iFactoryGlueProvider);
}
