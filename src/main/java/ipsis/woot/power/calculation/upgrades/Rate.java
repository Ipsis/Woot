package ipsis.woot.power.calculation.upgrades;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.power.calculation.BigIntegerCalculator;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

import java.math.BigInteger;

public class Rate implements IUpgradePowerCalculator {

    @Override
    public boolean isActive(IFarmSetup iFarmSetup) {

        return iFarmSetup.hasUpgrade(EnumFarmUpgrade.RATE);
    }

    @Override
    public BigInteger calculateUpgradeCost(IFarmSetup iFarmSetup, int spawnTicks) {

        int perTick = Woot.wootConfiguration.getInteger(
                iFarmSetup.getWootMobName(),
                ConfigKeyHelper.getRatePowerPerTick(iFarmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));

        BigInteger cost = BigInteger.valueOf(spawnTicks);
        cost = cost.multiply(BigInteger.valueOf(perTick));

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "rate", "upgrade cost:" + cost);
        return cost;
    }

    @Override
    public void updateCalculatorValues(IFarmSetup iFarmSetup, BigIntegerCalculator.CalculatorValues calculatorValues) {

        calculatorValues.rate = Woot.wootConfiguration.getInteger(
                iFarmSetup.getWootMobName(),
                ConfigKeyHelper.getRateParam(iFarmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "rate", "upgrade value:" + calculatorValues.rate);
    }
}
