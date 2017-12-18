package ipsis.woot.loot.schools;

import net.minecraft.util.math.BlockPos;

public class SpawnBox {

    private BlockPos basePos;
    private BlockPos spawnPos;
    private boolean used = false;

    public SpawnBox(BlockPos basePos) {
        this.basePos = basePos;
    }

    public void setUsed() {
        this.used = true;
    }

    public void clearUsed() {
        this.used = false;
    }

    public boolean isUsed() {
        return this.used;
    }

    public BlockPos getBasePos() {
        return this.basePos;
    }

    @Override
    public String toString() {

        return "Base:" + basePos + " Used:" + used;
    }
}
