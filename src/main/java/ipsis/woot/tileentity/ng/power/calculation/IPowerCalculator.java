package ipsis.woot.tileentity.ng.power.calculation;

import ipsis.woot.tileentity.ng.IFarmSetup;
import ipsis.woot.tileentity.ng.PowerRecipe;

public interface IPowerCalculator {

    PowerRecipe calculate(IFarmSetup farmSetup);
}
