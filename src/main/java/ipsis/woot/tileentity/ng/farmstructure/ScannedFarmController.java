package ipsis.woot.tileentity.ng.farmstructure;

import ipsis.woot.tileentity.ng.WootMob;
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

        return pos != null && wootMob!= null;
    }

    public BlockPos getBlocks() {

        return pos;
    }

    public void setBlocks(BlockPos pos) {

        this.pos = pos;
    }
}
