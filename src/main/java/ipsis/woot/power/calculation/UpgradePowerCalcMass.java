package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.EnumFarmUpgrade;

public class UpgradePowerCalcMass extends AbstractUpgradePowerCalc{

    enum EnumMassScalar {
        LINEAR,
        X_BASE_2, // multiply by 2^(mob-1) (eg 1,2,4,8)
        X_BASE_3 // multiply by 3^(mob-1) (eg 1,3,9,27)
        ;

        public static EnumMassScalar SCALARS[] = { LINEAR, X_BASE_2, X_BASE_3 };

        public static EnumMassScalar getFromIndex(int index) {

            if (index > 0 && index < SCALARS.length)
                return SCALARS[index];

            return LINEAR;
        }
    }

    @Override
    public void calculate(IFarmSetup farmSetup, Calculator.PowerValues powerValues, int spawnTicks) {

        int mobCount = farmSetup.getNumMobs();
        int mobCost = powerValues.mobCost;

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.MASS)) {
            powerValues.upgradeCost += mobCost;
            return;
        }

        // Add the cost of running the upgrade
        powerValues.upgradeCost +=
                spawnTicks * Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getRatePowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));


        // Add the impact of spawning more mobs
        EnumMassScalar scalar = EnumMassScalar.getFromIndex(Woot.wootConfiguration.getInteger(farmSetup.getWootMobName(), EnumConfigKey.MASS_FX));
        if (scalar == EnumMassScalar.LINEAR) {
            powerValues.upgradeCost += (mobCost * mobCount);
        } else if (scalar == EnumMassScalar.X_BASE_2) {
            for (int i = 0; i < mobCount; i++) {
                int cost = mobCost * (int) Math.pow(2, i);
                powerValues.upgradeCost += cost;
            }
        } else if (scalar == EnumMassScalar.X_BASE_3) {
            for (int i = 0; i < mobCount; i++) {
                int cost = mobCost * (int) Math.pow(3, i);
                powerValues.upgradeCost += cost;
            }
        }
    }
}
