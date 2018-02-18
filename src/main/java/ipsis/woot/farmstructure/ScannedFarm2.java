package ipsis.woot.farmstructure;

import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ScannedFarm2 {

    Set<FarmScanner2.BadFarmBlockInfo> badBlocks = new HashSet<>();

    public ScannedFarmBase base = new ScannedFarmBase();
    public ScannedFarmController controller = new ScannedFarmController();
    public ScannedFarmRemote remote = new ScannedFarmRemote();
    public ScannedFarmUpgrade upgrades = new ScannedFarmUpgrade();

    public boolean isValidStructure() {
        return base.tier != null && controller.isValid() && remote.isValid();
    }

    public Set<FarmScanner2.BadFarmBlockInfo> getBadBlocks() { return badBlocks; }

    public boolean isValidCofiguration(World world) {
        return isValidStructure() && controller.isTierValid(world, base.tier);
    }

    /**
     * Returns true if the farms are identical but ignoring the proxy
     */
    public static boolean areFarmsEqual(@Nullable ScannedFarm2 a, @Nullable ScannedFarm2 b) {

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
