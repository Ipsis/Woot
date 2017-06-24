package ipsis.woot.tileentity.ng;

import ipsis.Woot;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobName;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class FarmSetup implements IFarmSetup {

    private Map<EnumFarmUpgrade, Integer> upgradeMap = new HashMap<>();
    private WootMob wootMob;
    private EnumEnchantKey enchantKey = EnumEnchantKey.NO_ENCHANT;
    private EnumMobFactoryTier tier = EnumMobFactoryTier.TIER_ONE;

    public FarmSetup(WootMob wootMob) {

        this.wootMob = wootMob;
    }

    public @Nonnull WootMob getWootMob() {

        return wootMob;
    }

    public @Nonnull
    WootMobName getWootMobName() {

        return wootMob.getWootMobName();
    }

    public int getNumMobs() {

        int numMobs;
        int massLevel = getUpgradeLevel(EnumFarmUpgrade.MASS);
        switch (massLevel) {
            case 1:
                numMobs = Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.MASS_1_PARAM);
                break;
            case 2:
                numMobs =  Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.MASS_2_PARAM);
                break;
            case 3:
                numMobs = Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.MASS_3_PARAM);
                break;
            default:
                numMobs = Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.NUM_MOBS);
                break;
        }

        return numMobs;
    }


    public int getUpgradeLevel(EnumFarmUpgrade upgrade) {

        if (upgradeMap.containsKey(upgrade))
            return upgradeMap.get(upgrade);

        return 0;
    }

    public void setUpgradeLevel(EnumFarmUpgrade upgrade, int level) {

        upgradeMap.put(upgrade, level);
    }

    @Override
    public void setFarmTier(EnumMobFactoryTier tier) {

        this.tier = tier;
    }

    @Override
    public void setEnchantKey(EnumEnchantKey key) {

        this.enchantKey = key;
    }

    public boolean hasUpgrade(EnumFarmUpgrade upgrade) {

        return getUpgradeLevel(upgrade) != 0;
    }

    @Nonnull
    @Override
    public EnumEnchantKey getEnchantKey() {
        return enchantKey;
    }

    @Nonnull
    @Override
    public EnumMobFactoryTier getFarmTier() {
        return tier;
    }



}
