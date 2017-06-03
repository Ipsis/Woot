package ipsis.woot.tileentity.ng;

import ipsis.woot.manager.EnumEnchantKey;

import javax.annotation.Nonnull;

public interface IFarmSetup {

    @Nonnull WootMob getWootMob();
    @Nonnull WootMobName getWootMobName();
    int getNumMobs();
    int getUpgradeLevel(EnumFarmUpgrade upgrade);
    void setUpgradeLevel(EnumFarmUpgrade upgrade, int level);
    boolean hasUpgrade(EnumFarmUpgrade upgrade);
    EnumEnchantKey getEnchantKey();
}
