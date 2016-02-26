package ipsis.woot.manager;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSetup {

    EnumEnchantKey enchantKey;

    EnumSpawnerUpgrade rateUpgrade;
    EnumSpawnerUpgrade lootingUpgrade;
    EnumSpawnerUpgrade xpUpgrade;
    EnumSpawnerUpgrade massUpgrade;
    EnumSpawnerUpgrade decapitateUpgrade;

    List<EnumSpawnerUpgrade> upgradeList;

    int rfPerTickCost;

    public UpgradeSetup() {

        upgradeList = new ArrayList<EnumSpawnerUpgrade>();
        clear();
    }

    public void clear() {

        enchantKey = EnumEnchantKey.NO_ENCHANT;
        rateUpgrade = null;
        lootingUpgrade = null;
        xpUpgrade = null;
        massUpgrade = null;
        decapitateUpgrade = null;
        upgradeList.clear();
    }

    public void processUpgrades(List<SpawnerUpgrade> upgradeList) {

        clear();
        this.enchantKey = UpgradeManager.getLootingEnchant(upgradeList);

        SpawnerUpgrade u;

        u = UpgradeManager.getMassUpgrade(upgradeList);
        if (u != null) {
            massUpgrade = u.getUpgradeType();
            this.upgradeList.add(massUpgrade);
        }

        u = UpgradeManager.getRateUpgrade(upgradeList);
        if (u != null) {
            rateUpgrade = u.getUpgradeType();
            this.upgradeList.add(rateUpgrade);
        }

        u = UpgradeManager.getDecapitateUpgrade(upgradeList);
        if (u != null) {
            decapitateUpgrade = u.getUpgradeType();
            this.upgradeList.add(decapitateUpgrade);
        }

        u = UpgradeManager.getLootingUpgrade(upgradeList);
        if (u != null) {
            lootingUpgrade = u.getUpgradeType();
            this.upgradeList.add(lootingUpgrade);
        }

        u = UpgradeManager.getXpUpgrade(upgradeList);
        if (u != null) {
            xpUpgrade = u.getUpgradeType();
            this.upgradeList.add(xpUpgrade);
        }

        rfPerTickCost = 0;
        // Look at ALL the upgrades passed in
        for (SpawnerUpgrade spawnerUpgrade : upgradeList) {
            rfPerTickCost += spawnerUpgrade.getRfCostPerTick();
        }
    }

    public boolean hasMassUpgrade() { return massUpgrade != null; }
    public boolean hasRateUpgrade() { return rateUpgrade != null; }
    public boolean hasDecapitateUpgrade() { return decapitateUpgrade != null; }
    public boolean hasLootingUpgrade() { return lootingUpgrade != null; }
    public boolean hasXpUpgrade() { return xpUpgrade != null; }
    public List<EnumSpawnerUpgrade> getUpgradeList() { return this.upgradeList; }
    public int getRfPerTickCost() { return rfPerTickCost; }
    public EnumSpawnerUpgrade getMassUpgrade() { return massUpgrade; }
    public EnumSpawnerUpgrade getRateUpgrade() { return rateUpgrade; }
    public EnumSpawnerUpgrade getDecapitateUpgrade() { return decapitateUpgrade; }
    public EnumSpawnerUpgrade getLootingUpgrade() { return lootingUpgrade; }
    public EnumSpawnerUpgrade getXpUpgrade() { return xpUpgrade; }
    public EnumEnchantKey getEnchantKey() { return enchantKey; }
}
