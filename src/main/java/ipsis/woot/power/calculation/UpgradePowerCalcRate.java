package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcRate extends AbstractUpgradePowerCalc {

    @Override
    public int calculate(IFarmSetup farmSetup) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.RATE))
            return 0;

        return Woot.wootConfiguration.getInteger(
                farmSetup.getWootMobName(),
                ConfigKeyHelper.getMassPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));
    }
}
