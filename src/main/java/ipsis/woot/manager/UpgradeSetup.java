package ipsis.woot.manager;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Settings;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSetup {

    EnumEnchantKey enchantKey;

    EnumSpawnerUpgrade rateUpgrade;
    EnumSpawnerUpgrade lootingUpgrade;
    EnumSpawnerUpgrade xpUpgrade;
    EnumSpawnerUpgrade massUpgrade;
    EnumSpawnerUpgrade decapitateUpgrade;
    EnumSpawnerUpgrade efficiencyUpgrade;

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
        efficiencyUpgrade = null;
        upgradeList.clear();
    }

    private int calcUpgradeCost(int tier, int tICost, int tIICost, int tIIICost) {

        if (tier == 1)
            return tICost;
        else if (tier == 2)
            return tIICost;
        else
            return tIIICost;
    }

    public void processUpgrades(List<SpawnerUpgrade> upgradeList) {

        clear();
        this.enchantKey = UpgradeManager.getLootingEnchant(upgradeList);
        rfPerTickCost = 0;

        SpawnerUpgrade u;

        u = UpgradeManager.getUpgrade(upgradeList, UpgradeManager.EnumUpgradeType.MASS);
        if (u != null) {
            massUpgrade = u.getUpgradeType();
            this.upgradeList.add(massUpgrade);

            rfPerTickCost += calcUpgradeCost(u.getUpgradeTier(), Settings.massIRfTick,
                    Settings.massIIRfTick, Settings.massIIIRfTick);
        }

        u = UpgradeManager.getUpgrade(upgradeList, UpgradeManager.EnumUpgradeType.RATE);
        if (u != null) {
            rateUpgrade = u.getUpgradeType();
            this.upgradeList.add(rateUpgrade);

            rfPerTickCost += calcUpgradeCost(u.getUpgradeTier(), Settings.rateIRfTick,
                    Settings.rateIIRfTick, Settings.rateIIIRfTick);
        }

        u = UpgradeManager.getUpgrade(upgradeList, UpgradeManager.EnumUpgradeType.DECAPITATE);
        if (u != null) {
            decapitateUpgrade = u.getUpgradeType();
            this.upgradeList.add(decapitateUpgrade);

            rfPerTickCost += calcUpgradeCost(u.getUpgradeTier(), Settings.decapitateIRfTick,
                    Settings.decapitateIIRfTick, Settings.decapitateIIIRfTick);
        }

        u = UpgradeManager.getUpgrade(upgradeList, UpgradeManager.EnumUpgradeType.LOOTING);
        if (u != null) {
            lootingUpgrade = u.getUpgradeType();
            this.upgradeList.add(lootingUpgrade);

            rfPerTickCost += calcUpgradeCost(u.getUpgradeTier(), Settings.lootingIRfTick,
                    Settings.lootingIIRfTick, Settings.lootingIIIRfTick);
        }

        u = UpgradeManager.getUpgrade(upgradeList, UpgradeManager.EnumUpgradeType.XP);
        if (u != null) {
            xpUpgrade = u.getUpgradeType();
            this.upgradeList.add(xpUpgrade);

            rfPerTickCost += calcUpgradeCost(u.getUpgradeTier(), Settings.xpIRfTick,
                    Settings.xpIIRfTick, Settings.xpIIIRfTick);
        }

        u = UpgradeManager.getUpgrade(upgradeList, UpgradeManager.EnumUpgradeType.EFFICIENCY);
        if (u != null) {
            efficiencyUpgrade = u.getUpgradeType();
            this.upgradeList.add(efficiencyUpgrade);

            /* No cost for efficiency */
        }
    }

    public boolean hasMassUpgrade() { return massUpgrade != null; }
    public boolean hasRateUpgrade() { return rateUpgrade != null; }
    public boolean hasDecapitateUpgrade() { return decapitateUpgrade != null; }
    public boolean hasLootingUpgrade() { return lootingUpgrade != null; }
    public boolean hasXpUpgrade() { return xpUpgrade != null; }
    public boolean hasEfficiencyUpgrade() { return efficiencyUpgrade != null; }
    public List<EnumSpawnerUpgrade> getUpgradeList() { return this.upgradeList; }
    public int getRfPerTickCost() { return rfPerTickCost; }
    public EnumSpawnerUpgrade getMassUpgrade() { return massUpgrade; }
    public EnumSpawnerUpgrade getRateUpgrade() { return rateUpgrade; }
    public EnumSpawnerUpgrade getDecapitateUpgrade() { return decapitateUpgrade; }
    public EnumSpawnerUpgrade getLootingUpgrade() { return lootingUpgrade; }
    public EnumSpawnerUpgrade getXpUpgrade() { return xpUpgrade; }
    public EnumSpawnerUpgrade getEfficiencyUpgrade() { return efficiencyUpgrade; }
    public EnumEnchantKey getEnchantKey() { return enchantKey; }
}
