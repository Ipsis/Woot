package ipsis.woot.factory.structure;

import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.util.math.BlockPos;

public class FactoryConfig {

    private FakeMobKey fakeMobKey = new FakeMobKey();
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private int looting = 0;

    public FactoryConfig setLooting(int looting) { this.looting = looting; return this; }
    public FactoryConfig setFakeMobKey(FakeMobKey fakeMobKey) { this.fakeMobKey = fakeMobKey; return this; }
    public FactoryConfig setFactoryTier(FactoryTier factoryTier) { this.factoryTier = factoryTier; return this; }
    public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }
    public int getLooting() { return this.looting; }
    public FactoryTier getFactoryTier() { return this.factoryTier; }

    private BlockPos powerCellPos;
    private BlockPos exportPos;
    private BlockPos importPos;
    public void setPowerCellPos(BlockPos pos) { this.powerCellPos = new BlockPos(pos); }
    public void setExportPos(BlockPos pos) { this.exportPos = new BlockPos(pos); }
    public void setImportPos(BlockPos pos) { this.importPos = new BlockPos(pos); }
    public BlockPos getPowerCellPos() { return this.powerCellPos; }
    public BlockPos getExportPos() { return this.exportPos; }
    public BlockPos getImportPos() { return this.importPos; }


}
