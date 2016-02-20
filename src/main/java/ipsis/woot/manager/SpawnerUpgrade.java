package ipsis.woot.manager;

public class SpawnerUpgrade {

    EnumSpawnerUpgrade upgradeType;

    public SpawnerUpgrade(EnumSpawnerUpgrade upgradeType) {

        this.upgradeType = upgradeType;
    }

    public EnumSpawnerUpgrade getUpgradeType() {

        return this.upgradeType;
    }

    public SpawnerManager.EnchantKey getEnchantKey() {

        if (upgradeType == EnumSpawnerUpgrade.LOOTING_I)
            return SpawnerManager.EnchantKey.LOOTING_I;
        else if (upgradeType == EnumSpawnerUpgrade.LOOTING_II)
            return SpawnerManager.EnchantKey.LOOTING_II;
        else if (upgradeType == EnumSpawnerUpgrade.LOOTING_III)
            return SpawnerManager.EnchantKey.LOOTING_III;

        return SpawnerManager.EnchantKey.NO_ENCHANT;
    }

    public boolean isLooting() {

        return upgradeType == EnumSpawnerUpgrade.LOOTING_I ||
                upgradeType == EnumSpawnerUpgrade.LOOTING_II ||
                upgradeType == EnumSpawnerUpgrade.LOOTING_III;
    }

    public boolean isRate() {

        return upgradeType == EnumSpawnerUpgrade.RATE_I ||
                upgradeType == EnumSpawnerUpgrade.RATE_II ||
                upgradeType == EnumSpawnerUpgrade.RATE_III;
    }

    public boolean isXp() {

        return upgradeType == EnumSpawnerUpgrade.XP_I ||
                upgradeType == EnumSpawnerUpgrade.XP_II ||
                upgradeType == EnumSpawnerUpgrade.XP_III;
    }

    public int getUpgradeTier() {

        int tier;
        switch (upgradeType) {
            case LOOTING_I:
            case RATE_I:
            case XP_I:
                tier = 1;
                break;
            case LOOTING_II:
            case RATE_II:
            case XP_II:
                tier = 2;
                break;
            case LOOTING_III:
            case RATE_III:
            case XP_III:
                tier = 3;
                break;
            default:
                tier = 0;
                break;
        }

        return tier;
    }
}
