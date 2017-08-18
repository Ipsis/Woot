package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcMass extends AbstractUpgradePowerCalc{

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.MASS))
            return;

        powerValues.upgradeCost +=
                farmSetup.getNumMobs() * spawnTicks * Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getMassPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.MASS)));
    }
}
