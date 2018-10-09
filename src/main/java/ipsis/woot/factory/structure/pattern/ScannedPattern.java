package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ScannedPattern {

    // The tier we failed or succeeded to validate
    private FactoryTier factoryTier;
    public ScannedPattern(FactoryTier factoryTier) { this.factoryTier = factoryTier; }
    public FactoryTier getFactoryTier() { return this.factoryTier; }

    private List<BlockPos> blocks = new ArrayList<>();
    public List<BlockPos> getBlocks() { return this.blocks; }
    public void addGoodBlock(BlockPos pos, FactoryBlock factoryBlock) {
        blocks.add(new BlockPos(pos));

        if (factoryBlock == FactoryBlock.CONTROLLER)
            controllerPos = new BlockPos(pos);
        else if (factoryBlock == FactoryBlock.POWER)
            powerPos = new BlockPos(pos);
        else if (factoryBlock == FactoryBlock.IMPORT)
            importPos = new BlockPos(pos);
        else if (factoryBlock == FactoryBlock.EXPORT)
            exportPos = new BlockPos(pos);
    }

    private BlockPos controllerPos;
    private FakeMobKey controllerMob;
    private BlockPos powerPos;
    private BlockPos exportPos;
    private BlockPos importPos;

    public void setControllerMob(FakeMobKey fakeMobKey) { controllerMob = fakeMobKey; }

    public boolean hasController() { return controllerPos != null && controllerMob != null && controllerMob.isValid(); }
    public boolean hasPower() { return powerPos != null; }
    public boolean hasExport() { return exportPos != null; }
    public boolean hasImport() { return importPos != null; }

    public BlockPos getControllerPos() { return controllerPos; }
    public BlockPos getPowerPos() { return powerPos; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }
    public FakeMobKey getControllerMob() { return controllerMob; }


    /**
     * Bad Blocks
     */
    private List<BadLayoutBlockInfo> badBlocks = new ArrayList<>();
    public List<BadLayoutBlockInfo> getBadBlocks() { return this.badBlocks; }
    public boolean hasBadBlocks() { return !this.badBlocks.isEmpty(); }
    public void addBadBlock(BlockPos pos, BadBlockReason reason, FactoryBlock correctBlock, Block incorrectBlock) {
        badBlocks.add(new BadLayoutBlockInfo(new BlockPos(pos), reason, correctBlock, incorrectBlock));
    }

    public enum BadBlockReason {
        MISSING_BLOCK,
        INCORRECT_BLOCK,
        INCORRECT_TYPE;
    }

    public class BadLayoutBlockInfo {

        public BlockPos pos;
        public BadBlockReason reason;
        public FactoryBlock correctBlock;
        public Block incorrectBlock;

        public BadLayoutBlockInfo(BlockPos pos, BadBlockReason reason, FactoryBlock correctBlock, Block incorrectBlock) {
            this.pos = new BlockPos(pos);
            this.reason = reason;
            this.correctBlock = correctBlock;
            this.incorrectBlock = incorrectBlock;
        }
    }
}
