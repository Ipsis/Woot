package ipsis.woot.farmstructure;

import ipsis.Woot;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.EntityList;
import net.minecraft.util.math.BlockPos;

public class ScannedFarmController {

    private BlockPos pos;
    WootMob wootMob;

    public static boolean isEqual(ScannedFarmController a, ScannedFarmController b) {

        if (a == null || b == null)
            return false;

        if (!a.pos.equals(b.pos))
            return false;

        return a.wootMob.getWootMobName().equals(b.wootMob.getWootMobName());
    }

    public boolean isValid() {

        if (pos == null || wootMob == null || !Woot.policyRepository.canCapture(wootMob.getWootMobName()))
            return false;

        return (EntityList.isRegistered(wootMob.getWootMobName().getResourceLocation()));
    }

    public BlockPos getBlocks() {

        return pos;
    }

    public void setBlocks(BlockPos pos) {

        this.pos = pos;
    }
}
