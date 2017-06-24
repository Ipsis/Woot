package ipsis.woot.power.calculation;

import ipsis.woot.tileentity.ng.IFarmSetup;

public abstract class AbstractUpgradePowerCalc {

    /**
     * Calculate how much power per tick this upgrade requires
     */
    abstract public int calculate(IFarmSetup farmSetup);
}
