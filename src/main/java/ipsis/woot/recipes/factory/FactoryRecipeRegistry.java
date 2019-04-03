package ipsis.woot.recipes.factory;

import ipsis.woot.config.ConfigRegistry;
import ipsis.woot.factory.multiblock.FactoryConfig;
import ipsis.woot.util.FakeMobKey;

public class FactoryRecipeRegistry {

    public static final FactoryRecipeRegistry REGISTRY = new FactoryRecipeRegistry();

    public FactoryRecipe get(FactoryConfig factoryConfig) {

        if (factoryConfig == null)
            return new FactoryRecipe(200, 200);

        /**
         * Spawn time is the time of the slowest mob
         */
        int time = 1;
        for (FakeMobKey fakeMobKey : factoryConfig.getValidMobs())
            time = Math.max(time, ConfigRegistry.CONFIG_REGISTRY.getSpawnTime(fakeMobKey));

        FactoryRecipe recipe = new FactoryRecipe(200, 200);
        return recipe;

    }
}
