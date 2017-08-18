package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcDecapitate extends AbstractUpgradePowerCalc {

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.DECAPITATE))
            return;

        powerValues.upgradeCost +=
                spawnTicks * Woot.wootConfiguration.getInteger(
                                farmSetup.getWootMobName(),
                                ConfigKeyHelper.getDecapPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.DECAPITATE)));
    }
}
