package ipsis.woot.upgrades;

import java.util.HashMap;

public class FactoryUpgradeConfig {

    private HashMap<FactoryUpgrade, Integer> upgrades = new HashMap<>();

    public void addUpgrade(FactoryUpgrade upgrade, int level) {
        if (level > 0 && level <= 3)
            upgrades.put(upgrade, level);
    }

    public boolean hasUpgrade(FactoryUpgrade upgrade) {
        return upgrades.containsKey(upgrade);
    }

    public int getUpgradeLevel(FactoryUpgrade upgrade) {
        if (upgrades.containsKey(upgrade))
            return upgrades.get(upgrade);
        return 0;
    }
}
