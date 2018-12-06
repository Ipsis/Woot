package ipsis.woot.upgrades;

import ipsis.woot.factory.structure.FactoryConfig;
import net.minecraft.world.World;

public interface IFactoryCost {

    void calculate(World world, FactoryConfig factoryConfig);
}
