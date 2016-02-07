package ipsis.woot.manager;

import java.util.List;

public class UpgradeValidator {

    public static boolean isUpgradeValid(List<Upgrade> upgradeList) {

        int[] count = new int[Upgrade.Group.values().length];

        for (Upgrade u : upgradeList)
            count[u.getGroup().ordinal()]++;

        for (int i = 0; i < count.length; i++)
            if (count[i] > 1)
                return false;

        return true;
    }
}
