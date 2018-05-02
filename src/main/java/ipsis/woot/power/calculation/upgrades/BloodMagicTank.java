package ipsis.woot.power.calculation.upgrades;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.power.calculation.BigIntegerCalculator;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

import java.math.BigInteger;

public class BloodMagicTank implements IUpgradePowerCalculator {

    @Override
    public boolean isActive(IFarmSetup iFarmSetup) {

        return iFarmSetup.hasUpgrade(EnumFarmUpgrade.BM_LE_TANK);
    }

    @Override
    public BigInteger calculateUpgradeCost(IFarmSetup iFarmSetup, int spawnTicks) {

        int perTick = Woot.wootConfiguration.getInteger(
                iFarmSetup.getWootMobName(),
                ConfigKeyHelper.getBmLeTankPowerPerTick(iFarmSetup.getUpgradeLevel(EnumFarmUpgrade.BM_LE_TANK)));

        BigInteger cost = BigInteger.valueOf(spawnTicks);
        cost = cost.multiply(BigInteger.valueOf(perTick));

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "bloodMagicTank", "upgrade cost:" + cost);
        return cost;
    }

    @Override
    public void updateCalculatorValues(IFarmSetup iFarmSetup, BigIntegerCalculator.CalculatorValues calculatorValues) {
    }
}
