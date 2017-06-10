package ipsis.woot.tileentity.ng.farmstructure;


import javax.annotation.Nullable;

public class ScannedFarm {

    ScannedFarmBase base;
    ScannedFarmProxy proxy;
    ScannedFarmController controller;
    ScannedFarmUpgrade upgrades;

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


        return false;
    }

    /**
     * Returns true if the farms are identical including the proxy
     */
    public static boolean areFarmsEqualProxy(@Nullable ScannedFarm a, @Nullable ScannedFarm b) {

        if (areFarmsEqual(a, b)) {
            return ScannedFarmProxy.isEqual(a.proxy, b.proxy);
        }

        return false;
    }
}
