package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;

import java.math.BigInteger;

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
        long mobCost = powerValues.mobCost;
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateMass", "mobs:" + mobCount + " @ cost:" + mobCost);

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.MASS)) {
            // This is because we use this calculator for spawning a single mob, even when the upgrade isn't present
            powerValues.upgradeCost += mobCost;
            return;
        }

        // Add the cost of running the upgrade
        long upgrade = spawnTicks * Woot.wootConfiguration.getInteger(
                        farmSetup.getWootMobName(),
                        ConfigKeyHelper.getRatePowerPerTick(farmSetup.getUpgradeLevel(EnumFarmUpgrade.RATE)));
        updateCost(upgrade, powerValues);

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateMass", "upgrade:" + upgrade);

        // Add the impact of spawning more mobs

        EnumMassScalar scalar = EnumMassScalar.getFromIndex(Woot.wootConfiguration.getInteger(farmSetup.getWootMobName(), EnumConfigKey.MASS_FX));
        if (scalar == EnumMassScalar.LINEAR) {
            BigInteger cost = BigInteger.valueOf(mobCost);
            cost = cost.multiply(BigInteger.valueOf(mobCount));

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateMass", "linear cost:" + cost.longValue());
            cost = cost.add(BigInteger.valueOf(powerValues.upgradeCost));
            powerValues.upgradeCost = cost.longValue();
        } else if (scalar == EnumMassScalar.X_BASE_2) {

            BigInteger totalCost = BigInteger.valueOf(0);
            for (int i = 0; i < mobCount; i++) {
                BigInteger cost = BigInteger.valueOf(mobCost);
                cost = cost.multiply(BigInteger.valueOf((int)Math.pow(2,i)));
                totalCost = totalCost.add(cost);
            }

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateMass", "base2 cost:" + totalCost.longValue());
            totalCost = totalCost.add(BigInteger.valueOf(powerValues.upgradeCost));
            powerValues.upgradeCost = totalCost.longValue();

        } else if (scalar == EnumMassScalar.X_BASE_3) {

            BigInteger totalCost = BigInteger.valueOf(0);
            for (int i = 0; i < mobCount; i++) {
                BigInteger cost = BigInteger.valueOf(mobCost);
                cost = cost.multiply(BigInteger.valueOf((int)Math.pow(3,i)));
                totalCost = totalCost.add(cost);
            }

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculateMass", "base3 cost:" + totalCost.longValue());
            totalCost = totalCost.add(BigInteger.valueOf(powerValues.upgradeCost));
            powerValues.upgradeCost = totalCost.longValue();
        }

    }
}
