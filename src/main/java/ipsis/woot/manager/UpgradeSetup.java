package ipsis.woot.manager;

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
    EnumSpawnerUpgrade bmUpgrade;

    List<EnumSpawnerUpgrade> upgradeList;

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
        bmUpgrade = null;
        upgradeList.clear();
    }

    public boolean hasBmUpgrade() { return bmUpgrade != null; }
    public boolean hasMassUpgrade() { return massUpgrade != null; }
    public boolean hasRateUpgrade() { return rateUpgrade != null; }
    public boolean hasDecapitateUpgrade() { return decapitateUpgrade != null; }
    public boolean hasLootingUpgrade() { return lootingUpgrade != null; }
    public boolean hasXpUpgrade() { return xpUpgrade != null; }
    public boolean hasEfficiencyUpgrade() { return efficiencyUpgrade != null; }
    public List<EnumSpawnerUpgrade> getUpgradeList() { return this.upgradeList; }
    public EnumSpawnerUpgrade getMassUpgrade() { return massUpgrade; }
    public EnumSpawnerUpgrade getRateUpgrade() { return rateUpgrade; }
    public EnumSpawnerUpgrade getDecapitateUpgrade() { return decapitateUpgrade; }
    public EnumSpawnerUpgrade getLootingUpgrade() { return lootingUpgrade; }
    public EnumSpawnerUpgrade getXpUpgrade() { return xpUpgrade; }
    public EnumSpawnerUpgrade getEfficiencyUpgrade() { return efficiencyUpgrade; }
    public EnumSpawnerUpgrade getBmUpgrade() { return bmUpgrade; }
    public EnumEnchantKey getEnchantKey() { return enchantKey; }
}
