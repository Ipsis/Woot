package ipsis.woot.power.calculation;

import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.PowerRecipe;
import net.minecraft.world.World;

public interface IPowerCalculator {

    PowerRecipe calculate(World world, IFarmSetup farmSetup);
}
