package ipsis.woot.factory.structure;

import ipsis.Woot;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.upgrades.FactoryUpgrade;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;

public class FactoryConfigBuilder {

    public static FactoryConfig create(@Nonnull FactoryLayout factoryLayout) {

        if (!factoryLayout.isFormed())
            Woot.logger.log(Level.ERROR, "Creating factory config from unformed factory");

        ScannedPattern scannedPattern = factoryLayout.getScanned();
        if (scannedPattern == null)
            Woot.logger.log(Level.ERROR, "Creating factory config from incomplete factory");

        FactoryConfig factoryConfig = new FactoryConfig();

        factoryConfig.setFactoryTier(scannedPattern.getFactoryTier());
        factoryConfig.setFakeMobKey(scannedPattern.getControllerMob());

        factoryConfig.setGeneratorPos(scannedPattern.getPowerPos());
        factoryConfig.setImportPos(scannedPattern.getImportPos());
        factoryConfig.setExportPos(scannedPattern.getExportPos());

        // TODO work out the upgrade position
        factoryConfig.getFactoryUpgradeConfig().addUpgrade(FactoryUpgrade.MASS, 1);
        factoryConfig.getFactoryUpgradeConfig().addUpgrade(FactoryUpgrade.RATE, 1);
        factoryConfig.getFactoryUpgradeConfig().addUpgrade(FactoryUpgrade.XP_GEN, 1);

        return factoryConfig;

    }
}
