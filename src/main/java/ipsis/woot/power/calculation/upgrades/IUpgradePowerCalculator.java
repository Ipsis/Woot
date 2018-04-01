package ipsis.woot.power.calculation.upgrades;

import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.power.calculation.BigIntegerCalculator;

import java.math.BigInteger;

public interface IUpgradePowerCalculator {

    BigInteger calculateUpgradeCost(IFarmSetup iFarmSetup, int spawnTicks);
    void updateCalculatorValues(IFarmSetup iFarmSetup, BigIntegerCalculator.CalculatorValues calculatorValues);
    boolean isActive(IFarmSetup iFarmSetup);
}
