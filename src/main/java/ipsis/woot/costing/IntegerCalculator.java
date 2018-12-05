package ipsis.woot.costing;

import ipsis.woot.configuration.ConfigRegistry;
import ipsis.woot.factory.recipes.FactoryRecipe;
import ipsis.woot.factory.structure.FactoryConfig;
import ipsis.woot.util.FakeMobKey;

public class IntegerCalculator {

    public static FactoryRecipe createFactoryRecipe(int mobHealth, FactoryConfig factoryConfig) {

        FakeMobKey fakeMobKey = factoryConfig.getFakeMobKey();

        // See costings.txt
        int tier = ConfigRegistry.INSTANCE.getFactoryTier(fakeMobKey, mobHealth);
        int tierUnitsPerTick = 1;
        int rawSpawnUnits = ConfigRegistry.INSTANCE.getIntegerConfig(ConfigRegistry.Key.SPAWN_TIME, fakeMobKey);
        int rawMobCost = mobHealth * ConfigRegistry.INSTANCE.getIntegerConfig(ConfigRegistry.Key.UNITS_PER_HEALTH, fakeMobKey);
        int ticks = rawSpawnUnits;
        return new FactoryRecipe(ticks, rawMobCost);
    }

}
