package ipsis.woot.loot.schools;

import net.minecraft.util.math.BlockPos;

public class SpawnBox {

    private BlockPos basePos;
    private BlockPos spawnPos;
    private boolean used = false;

    public SpawnBox(BlockPos basePos, BlockPos spawnPos) {
        this.basePos = new BlockPos(basePos);
        this.spawnPos = new BlockPos(spawnPos);
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
    public BlockPos getSpawnPos() { return this.spawnPos; }

    @Override
    public String toString() {

        return "Base: " + basePos + "Spawn: " + spawnPos + " Used:" + used;
    }
}
