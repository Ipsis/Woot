package ipsis.woot.power.calculation;

import ipsis.woot.farmstructure.IFarmSetup;

public abstract class AbstractUpgradePowerCalc {

    /**
     * Calculate how much power per tick this upgrade requires
     */
    abstract public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks);
}
