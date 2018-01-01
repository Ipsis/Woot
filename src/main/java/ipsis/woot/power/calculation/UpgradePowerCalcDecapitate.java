package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcDecapitate extends AbstractUpgradePowerCalc {

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.DECAPITATE))
            return;

        int cost = spawnTicks * Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getDecapPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.DECAPITATE)));
        powerValues.upgradeCost += cost;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateDecapitate", "cost:" + cost);
    }
}
