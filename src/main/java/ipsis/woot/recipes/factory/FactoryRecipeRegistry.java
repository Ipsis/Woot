package ipsis.woot.recipes.factory;

import ipsis.woot.Woot;
import ipsis.woot.config.ConfigRegistry;
import ipsis.woot.factory.multiblock.FactoryConfig;
import ipsis.woot.util.FakeMobKey;

public class FactoryRecipeRegistry {

    public static final FactoryRecipeRegistry REGISTRY = new FactoryRecipeRegistry();

    public FactoryRecipe get(FactoryConfig factoryConfig) {

        if (factoryConfig == null)
            return new FactoryRecipe(200, 200);

        /**
         * Log the config just now
         */
        Woot.LOGGER.info("Creating recipe");
        Woot.LOGGER.info("Upgrades");
        FactoryConfig.UpgradeInfo massInfo = factoryConfig.getUpgradeInfo(FactoryConfig.FactoryConfigUpgrade.Upgrade.MASS);
        if (massInfo.isValid())
            Woot.LOGGER.info("MASS_" + massInfo.getTier() + "/" + massInfo.getParam());
        FactoryConfig.UpgradeInfo lootingInfo = factoryConfig.getUpgradeInfo(FactoryConfig.FactoryConfigUpgrade.Upgrade.LOOTING);
        if (lootingInfo.isValid())
            Woot.LOGGER.info("LOOTING_" + lootingInfo.getTier() + "/" + lootingInfo.getParam());
        FactoryConfig.UpgradeInfo rateInfo = factoryConfig.getUpgradeInfo(FactoryConfig.FactoryConfigUpgrade.Upgrade.RATE);
        if (rateInfo.isValid())
            Woot.LOGGER.info("RATE_" + rateInfo.getTier() + "/" + rateInfo.getParam());

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
