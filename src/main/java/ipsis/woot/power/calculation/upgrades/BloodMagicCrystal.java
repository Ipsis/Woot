package ipsis.woot.power.calculation.upgrades;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.power.calculation.BigIntegerCalculator;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

import java.math.BigInteger;

public class BloodMagicCrystal implements IUpgradePowerCalculator {

    @Override
    public boolean isActive(IFarmSetup iFarmSetup) {

        return iFarmSetup.hasUpgrade(EnumFarmUpgrade.BM_CRYSTAL);
    }

    @Override
    public BigInteger calculateUpgradeCost(IFarmSetup iFarmSetup, int spawnTicks) {

        int perTick = Woot.wootConfiguration.getInteger(
                iFarmSetup.getWootMobName(),
                ConfigKeyHelper.getBmCrystalPowerPerTick(iFarmSetup.getUpgradeLevel(EnumFarmUpgrade.BM_CRYSTAL)));

        BigInteger cost = BigInteger.valueOf(spawnTicks);
        cost = cost.multiply(BigInteger.valueOf(perTick));

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "bloodMagicCrystal", "upgrade cost:" + cost);
        return cost;
    }

    @Override
    public void updateCalculatorValues(IFarmSetup iFarmSetup, BigIntegerCalculator.CalculatorValues calculatorValues) {
    }
}
