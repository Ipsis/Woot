package ipsis.woot.manager;

public class SpawnerUpgrade {

    EnumSpawnerUpgrade upgradeType;

    public SpawnerUpgrade(EnumSpawnerUpgrade upgradeType) {

        this.upgradeType = upgradeType;
    }

    public EnumSpawnerUpgrade getUpgradeType() {

        return this.upgradeType;
    }
}
