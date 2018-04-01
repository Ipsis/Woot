package ipsis.woot.power.calculation;

import ipsis.woot.farmstructure.IFarmSetup;

import java.math.BigInteger;

public abstract class AbstractUpgradePowerCalc {

    /**
     * Calculate how much power per tick this upgrade requires
     */
    abstract public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks);

    void updateCost(long cost, Calculator.PowerValues powerValues) {

        BigInteger bigCost = BigInteger.valueOf(cost);
        BigInteger currCost = BigInteger.valueOf(powerValues.upgradeCost);
        BigInteger newCost = bigCost.add(currCost);
        powerValues.upgradeCost = newCost.longValue();
    }
}
