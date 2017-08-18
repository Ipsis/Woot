package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcRate extends AbstractUpgradePowerCalc {

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.RATE))
            return;

        powerValues.upgradeCost +=
                spawnTicks * Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getRatePowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));

        powerValues.rate = Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getRateParam(farmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));
    }
}
