package ipsis.woot.power.calculation;

import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.PowerRecipe;

public interface IPowerCalculator {

    PowerRecipe calculate(IFarmSetup farmSetup);
}
