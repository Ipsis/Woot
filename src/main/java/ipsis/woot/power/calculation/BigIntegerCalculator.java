package ipsis.woot.power.calculation;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farming.PowerRecipe;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.power.calculation.upgrades.EnumMassMultiplier;
import ipsis.woot.power.calculation.upgrades.IUpgradePowerCalculator;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.WootMobName;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.List;

/**
 * Due to the power calculation things can easily exceed Integer.MAX_VALUE and possibly
 * exceed Long.MAX_VALUE.
 * Even though the final power will be capped at Long.MAX_VALUE, we have to avoid overflow as that
 * will screw up the factory processing.
 *
 * Therefore the calculation will be performed using BigInteger.
 * This calculation is only performed on factory forming, so any performance hit from BigInteger,
 * should not be an issue.
 *
 */
public class BigIntegerCalculator implements IPowerCalculator {

    @Override
    public @Nonnull PowerRecipe calculate(World world, IFarmSetup farmSetup) {

        WootMobName wootMobName = farmSetup.getWootMobName();
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", wootMobName);

        int tierPowerPerTick = Woot.wootConfiguration.getInteger(wootMobName, ConfigKeyHelper.getPowerPerTick(farmSetup.getFarmTier()));
        int spawnTicks = Woot.wootConfiguration.getInteger(wootMobName, EnumConfigKey.SPAWN_TICKS);
        int spawnUnits = Woot.wootConfiguration.getSpawnCost(world, wootMobName);
        int powerPerSpawnUnit = Woot.wootConfiguration.getInteger(wootMobName, EnumConfigKey.POWER_PER_UNIT);
        int mobCount = farmSetup.getNumMobs();

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "####");
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "tierPowerPerTick: " + tierPowerPerTick);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "spawnUnits: " + spawnUnits);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "powerPerSpawnUnit: " + powerPerSpawnUnit);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "spawnTicks: " + spawnTicks);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "number of mobs: " + mobCount);

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
        CalculatorValues v = new CalculatorValues();

        // The full power cost of using the factory
        BigInteger factoryCost = BigInteger.valueOf(tierPowerPerTick);
        factoryCost = factoryCost.multiply(BigInteger.valueOf(spawnTicks));

        // The cost to spawn a single mob
        BigInteger mobCost = BigInteger.valueOf(spawnUnits);
        mobCost = mobCost.multiply(BigInteger.valueOf(powerPerSpawnUnit));
        mobCost = mobCost.multiply(BigInteger.valueOf(spawnTicks));

        // Apply the cost of upgrades
        List<IUpgradePowerCalculator> calculators = Woot.calculatorRepository.getActiveCalculators(farmSetup);
        for (IUpgradePowerCalculator calculator : calculators) {
            BigInteger upgradeCost = calculator.calculateUpgradeCost(farmSetup, spawnTicks);
            factoryCost = factoryCost.add(upgradeCost);
            calculator.updateCalculatorValues(farmSetup, v);
        }

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "factory with upgrades: " + factoryCost);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "single mob: " + mobCost);

        // Initial mob cost
        BigInteger totalMobCost = BigInteger.ZERO;
        if (v.mass == EnumMassMultiplier.X_BASE_2) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "2^(count - 1)");

            for (int i = 0; i < mobCount; i++) {
                BigInteger m = BigInteger.valueOf((int)(Math.pow(2, i)));
                BigInteger c = mobCost.multiply(m);
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "cost * " + m + " -> " + c);
                totalMobCost = totalMobCost.add(c);
            }

        } else if (v.mass == EnumMassMultiplier.X_BASE_3) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "3^(count - 1)");

            for (int i = 0; i < mobCount; i++) {
                BigInteger m = BigInteger.valueOf((int)(Math.pow(3, i)));
                BigInteger c = mobCost.multiply(m);
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "cost * " + m + " -> " + c);
                totalMobCost = totalMobCost.add(mobCost.multiply(m));
            }

        } else { // LINEAR
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "linear");
            totalMobCost = mobCost.multiply(BigInteger.valueOf(mobCount));
        }

        factoryCost = factoryCost.add(totalMobCost);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", mobCount + " cost: " + totalMobCost);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "factory: " + factoryCost);

        // Rate upgrade reduces the spawn time
        int finalSpawnTicks = spawnTicks;
        if (v.rate > 0) {
            int reduction = (int)((spawnTicks / 100.0F) * v.rate);
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "rate reduction: " + reduction);
            finalSpawnTicks -= reduction;
            finalSpawnTicks = MathHelper.clamp(finalSpawnTicks, 1, Integer.MAX_VALUE);
        }
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "ticks: " + finalSpawnTicks);


        // Cap total cost
        long totalCost = Long.MAX_VALUE;
        if (factoryCost.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0)
            totalCost = factoryCost.longValue();

        // Efficiency reduces the total power cost
        if (v.efficiency > 0) {
            long reduction = (long)((totalCost / 100.0F) * v.efficiency);
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "efficiency reduction: " + reduction);
            totalCost -= reduction;
            if (totalCost < 1)
                totalCost = 1;
        }

        PowerRecipe powerRecipe = new PowerRecipe(finalSpawnTicks, totalCost);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "recipe: " + powerRecipe);
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "calculate", "####");
        return powerRecipe;
    }

    public class CalculatorValues {

        public int rate = 0;
        public int efficiency = 0;
        public EnumMassMultiplier mass = EnumMassMultiplier.LINEAR;
    }
}
