package ipsis.woot.farmstructure;


import javax.annotation.Nullable;

public class ScannedFarm {

    ScannedFarmBase base;
    ScannedFarmController controller;
    ScannedFarmUpgrade upgrades;
    ScannedFarmRemote remote;

    /**
     * Returns true if the farms are identical but ignoring the proxy
     */
    public static boolean areFarmsEqual(@Nullable ScannedFarm a, @Nullable ScannedFarm b) {

        if (a == null || b == null)
            return false;

        if (!ScannedFarmBase.isEqual(a.base, b.base))
            return false;

        if (!ScannedFarmController.isEqual(a.controller, b.controller))
            return false;

        if (!ScannedFarmUpgrade.isEqual(a.upgrades, b.upgrades))
            return false;

        if (!ScannedFarmRemote.isEqual(a.remote, b.remote))
            return false;

        return true;
    }
}
