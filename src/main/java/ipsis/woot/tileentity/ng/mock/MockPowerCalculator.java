package ipsis.woot.tileentity.ng.mock;

import ipsis.woot.tileentity.ng.IFarmSetup;
import ipsis.woot.tileentity.ng.IPowerCalculator;
import ipsis.woot.tileentity.ng.PowerRecipe;

public class MockPowerCalculator implements IPowerCalculator {

    public PowerRecipe calculate(IFarmSetup farmSetup) {

        return new PowerRecipe(320, 320);
    }
}
