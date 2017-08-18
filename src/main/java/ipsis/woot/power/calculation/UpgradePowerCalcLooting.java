package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcLooting extends AbstractUpgradePowerCalc {

    @Override
    public int calculate(IFarmSetup farmSetup) {

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.LOOTING))
            return 0;

        return Woot.wootConfiguration.getInteger(
                farmSetup.getWootMobName(),
                ConfigKeyHelper.getMassPowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.LOOTING)));
    }
}
