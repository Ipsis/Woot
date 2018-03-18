package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcLooting extends AbstractUpgradePowerCalc {

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.LOOTING))
            return;

        long cost = spawnTicks * Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getLootingPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.LOOTING)));
        powerValues.upgradeCost += cost;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateLooting", "cost:" + cost);
    }
}
