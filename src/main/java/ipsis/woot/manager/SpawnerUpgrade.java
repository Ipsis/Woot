package ipsis.woot.manager;

public class SpawnerUpgrade {

    EnumSpawnerUpgrade upgradeType;
    int rfCostPerTick;
    int v; /* type specific value */

    @Override
    public String toString() {

        return upgradeType + " pwr:" + rfCostPerTick + " v:" + v;
    }

    public SpawnerUpgrade(EnumSpawnerUpgrade upgradeType) {

        this.upgradeType = upgradeType;
        this.rfCostPerTick = 1;
        this.v = 0;
    }

    public SpawnerUpgrade setRfCostPerTick(int rfCostPerTick) {

        this.rfCostPerTick = rfCostPerTick;
        return this;
    }

    public int getRfCostPerTick() {

        return this.rfCostPerTick;
    }

    public SpawnerUpgrade setSpawnRate(int spawnRate) {

        this.v = spawnRate;
        return this;
    }

    public int getSpawnRate() {

        return this.v;
    }

    public SpawnerUpgrade setMass(int mass) {

        this.v = mass;
        return  this;
    }

    public int getMass() {

        return this.v;
    }

    public SpawnerUpgrade setDecapitateChance(int decapitateChance) {

        this.v = decapitateChance;
        return this;
    }

    public int getDecapitateChance() {

        return this.v;
    }

    public SpawnerUpgrade setXpBoost(int boost) {

        this.v = boost;
        return this;
    }

    public int getXpBoost() {

        return this.v;
    }

    public EnumSpawnerUpgrade getUpgradeType() {

        return this.upgradeType;
    }

    public EnumEnchantKey getEnchantKey() {

        if (upgradeType == EnumSpawnerUpgrade.LOOTING_I)
            return EnumEnchantKey.LOOTING_I;
        else if (upgradeType == EnumSpawnerUpgrade.LOOTING_II)
            return EnumEnchantKey.LOOTING_II;
        else if (upgradeType == EnumSpawnerUpgrade.LOOTING_III)
            return EnumEnchantKey.LOOTING_III;

        return EnumEnchantKey.NO_ENCHANT;
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

    public boolean isMass() {

        return upgradeType == EnumSpawnerUpgrade.MASS_I ||
                upgradeType == EnumSpawnerUpgrade.MASS_II ||
                upgradeType == EnumSpawnerUpgrade.MASS_III;
    }

    public boolean isDecapitate() {

        return upgradeType == EnumSpawnerUpgrade.DECAPITATE_I ||
                upgradeType == EnumSpawnerUpgrade.DECAPITATE_II ||
                upgradeType == EnumSpawnerUpgrade.DECAPITATE_III;
    }

    public int getUpgradeTier() {

        int tier;
        switch (upgradeType) {
            case LOOTING_I:
            case RATE_I:
            case XP_I:
            case MASS_I:
            case DECAPITATE_I:
                tier = 1;
                break;
            case LOOTING_II:
            case RATE_II:
            case XP_II:
            case MASS_II:
            case DECAPITATE_II:
                tier = 2;
                break;
            case LOOTING_III:
            case RATE_III:
            case XP_III:
            case MASS_III:
            case DECAPITATE_III:
                tier = 3;
                break;
            default:
                tier = 0;
                break;
        }

        return tier;
    }
}
