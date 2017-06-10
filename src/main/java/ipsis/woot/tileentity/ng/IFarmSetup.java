package ipsis.woot.tileentity.ng;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;

import javax.annotation.Nonnull;

public interface IFarmSetup {

    @Nonnull WootMob getWootMob();
    @Nonnull WootMobName getWootMobName();
    int getNumMobs();
    int getUpgradeLevel(EnumFarmUpgrade upgrade);
    boolean hasUpgrade(EnumFarmUpgrade upgrade);
    @Nonnull EnumEnchantKey getEnchantKey();
    @Nonnull EnumMobFactoryTier getFarmTier();
    void setUpgradeLevel(EnumFarmUpgrade upgrade, int level);
    void setFarmTier(EnumMobFactoryTier tier);
    void setEnchantKey(EnumEnchantKey key);
}
