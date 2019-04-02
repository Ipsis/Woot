package ipsis.woot.factory.generators;

import ipsis.woot.factory.multiblock.FactoryConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface ILootGenerator {

    void generate(@Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull FactoryConfig factoryConfig);
}
