package ipsis.woot.power.calculation.upgrades;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.power.calculation.BigIntegerCalculator;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

import java.math.BigInteger;

public class Mass implements IUpgradePowerCalculator {

    @Override
    public boolean isActive(IFarmSetup iFarmSetup) {

        return iFarmSetup.hasUpgrade(EnumFarmUpgrade.MASS);
    }

    @Override
    public BigInteger calculateUpgradeCost(IFarmSetup iFarmSetup, int spawnTicks) {

        int perTick = Woot.wootConfiguration.getInteger(
                iFarmSetup.getWootMobName(),
                ConfigKeyHelper.getMassPowerPerTick(iFarmSetup.getUpgradeLevel(EnumFarmUpgrade.MASS)));

        BigInteger cost = BigInteger.valueOf(spawnTicks);
        cost = cost.multiply(BigInteger.valueOf(perTick));

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "mass", "upgrade cost:" + cost);
        return cost;
    }

    @Override
    public void updateCalculatorValues(IFarmSetup iFarmSetup, BigIntegerCalculator.CalculatorValues calculatorValues) {

        int v = Woot.wootConfiguration.getInteger(iFarmSetup.getWootMobName(), EnumConfigKey.MASS_FX);
        if (v == 0)
            calculatorValues.mass = EnumMassMultiplier.LINEAR;
        else if (v == 1)
            calculatorValues.mass = EnumMassMultiplier.X_BASE_2;
        else if (v == 2)
            calculatorValues.mass = EnumMassMultiplier.X_BASE_3;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "mass", "upgrade value:" + calculatorValues.mass);
    }
}
