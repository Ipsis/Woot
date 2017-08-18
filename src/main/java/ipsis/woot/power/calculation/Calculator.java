package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.PowerRecipe;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.util.WootMobName;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Calculator implements IPowerCalculator {

    private List<AbstractUpgradePowerCalc> powerCalcList = new ArrayList<>();

    public Calculator() {

        powerCalcList.add(new UpgradePowerCalcDecapitate());
        powerCalcList.add(new UpgradePowerCalcEfficiency());
        powerCalcList.add(new UpgradePowerCalcLooting());
        powerCalcList.add(new UpgradePowerCalcMass());
        powerCalcList.add(new UpgradePowerCalcRate());
        powerCalcList.add(new UpgradePowerCalcXp());
    }

    /**
     * IPowerCalculator
     */
    @Override
    public PowerRecipe calculate(World world, IFarmSetup farmSetup) {

        WootMobName wootMobName = farmSetup.getWootMobName();
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, this, "calculate", wootMobName);

        PowerValues powerValues = new PowerValues();

        int tierPowerPerTick = Woot.wootConfiguration.getInteger(wootMobName, ConfigKeyHelper.getPowerPerTick(farmSetup.getFarmTier()));
        int spawnTicks = Woot.wootConfiguration.getInteger(wootMobName, EnumConfigKey.SPAWN_TICKS);
        int spawnXp = Woot.wootConfiguration.getSpawnCost(world, wootMobName);
        int powerPerXp = Woot.wootConfiguration.getInteger(wootMobName, EnumConfigKey.POWER_PER_XP);


        /**
         * Power calculation has multiple components
         *
         * spawnTicks is the base number of ticks and will differ depending on the mob
         * All power is based of this spawnTicks value and cannot be modified by upgrades
         *
         * 1. how much to run the base factory for spawnTicks
         * 2. how much to generate one mob
         * 3. how much to power the upgrades for spawnTicks
         *
         * Efficiency decreases the total power needed
         * Rate decreases the real time to spawn
         */
        powerValues.factoryCost = tierPowerPerTick * spawnTicks;
        powerValues.mobCost = spawnXp * powerPerXp * spawnTicks;

        for (AbstractUpgradePowerCalc calc : powerCalcList)
            calc.calculate(farmSetup, powerValues, spawnTicks);

        int totalPower = powerValues.factoryCost +
                powerValues.upgradeCost +
                powerValues.mobCost;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, this, "calculate",
                    "tierPowerPerTick:" + tierPowerPerTick + " spawnXp:" + spawnXp +
                            " powerPerXp:" + powerPerXp + " spawnTicks:" + spawnTicks);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, this, "calculate",
                "PowerValues " + powerValues);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, this, "calculate",
                "totalPower:" + totalPower);

        totalPower = (int)((totalPower / 100.0F) * powerValues.efficiency);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, this, "calculate",
                "efficiency:" + powerValues.efficiency + "->totalPower:" + totalPower);

        int finalSpawnTicks = (int)((spawnTicks / 100.0F) * powerValues.rate);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, this, "calculate",
                "rate:" + powerValues.rate + "->spawnTicks:" + finalSpawnTicks);

        return new PowerRecipe(finalSpawnTicks, totalPower);
    }

    public class PowerValues {

        public int factoryCost = 0;
        public int mobCost = 0;
        public int upgradeCost = 0;
        public int efficiency = 100;
        public int rate = 100;

        @Override
        public String toString() {

            return "factoryCost:" + factoryCost + " mobCost:" + mobCost + " upgradeCost:" + upgradeCost + " efficiency:" + efficiency + " rate:" + rate;
        }
    }

}
