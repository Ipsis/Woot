package ipsis.woot.factory.structure;

import ipsis.woot.upgrades.FactoryUpgradeConfig;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.util.math.BlockPos;

public class FactoryConfig {

    private FakeMobKey fakeMobKey = new FakeMobKey();
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private FactoryUpgradeConfig factoryUpgradeConfig = new FactoryUpgradeConfig();
    private int looting = 0;

    public FactoryConfig setLooting(int looting) { this.looting = looting; return this; }
    public FactoryConfig setFakeMobKey(FakeMobKey fakeMobKey) { this.fakeMobKey = fakeMobKey; return this; }
    public FactoryConfig setFactoryTier(FactoryTier factoryTier) { this.factoryTier = factoryTier; return this; }
    public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }
    public int getLooting() { return this.looting; }
    public FactoryTier getFactoryTier() { return this.factoryTier; }
    public FactoryUpgradeConfig getFactoryUpgradeConfig() { return this.factoryUpgradeConfig; }

    private BlockPos cellPos;
    private BlockPos exportPos;
    private BlockPos importPos;
    public void setCellPos(BlockPos pos) { this.cellPos = new BlockPos(pos); }
    public void setExportPos(BlockPos pos) { this.exportPos = new BlockPos(pos); }
    public void setImportPos(BlockPos pos) { this.importPos = new BlockPos(pos); }
    public BlockPos getCellPos() { return this.cellPos; }
    public BlockPos getExportPos() { return this.exportPos; }
    public BlockPos getImportPos() { return this.importPos; }


}
