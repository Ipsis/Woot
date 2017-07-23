package ipsis.woot.farmstructure;

import ipsis.woot.util.EnumFarmUpgrade;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScannedFarmUpgrade {

    private List<Upgrade> upgradeList = new ArrayList<>();

    public static class Upgrade {

        Set<BlockPos> blocks = new HashSet<>();
        EnumFarmUpgrade upgrade;
        int upgradeTier;

    }

    public Set<BlockPos> getBlocks() {

        Set<BlockPos> blocks = new HashSet<>();
        for (Upgrade u : upgradeList)
            blocks.addAll(u.blocks);

        return blocks;
    }

    public void addUpgrade(Upgrade upgrade) {

        this.upgradeList.add(upgrade);
    }

    public List<Upgrade> getUpgrades() {

        return upgradeList;
    }

    public static boolean isEqual(ScannedFarmUpgrade a, ScannedFarmUpgrade b) {

        if (a == null || b == null)
            return false;

        if (a.upgradeList.size() != b.upgradeList.size())
            return false;

        int matches = 0;
        for (Upgrade upgrade : a.getUpgrades()) {
            for (Upgrade upgrade1 : b.getUpgrades()) {
                if (upgrade.upgrade == upgrade1.upgrade && upgrade.upgradeTier == upgrade1.upgradeTier)
                    matches++;
            }
        }

        if (matches == a.upgradeList.size())
            return true;

        return false;
    }
}
