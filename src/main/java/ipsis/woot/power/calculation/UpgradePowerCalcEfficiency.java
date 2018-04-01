package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcEfficiency extends  AbstractUpgradePowerCalc {

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.EFFICIENCY))
            return;

        long cost = spawnTicks * Woot.wootConfiguration.getInteger(
                farmSetup.getWootMobName(),
                ConfigKeyHelper.getEffPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.EFFICIENCY)));
        updateCost(cost, powerValues);
        powerValues.efficiency = Woot.wootConfiguration.getInteger(
                farmSetup.getWootMobName(),
                ConfigKeyHelper.getEffParam(farmSetup.getUpgradeLevel(EnumFarmUpgrade.EFFICIENCY)));

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateEfficiency", "cost:" + cost);
    }
}
