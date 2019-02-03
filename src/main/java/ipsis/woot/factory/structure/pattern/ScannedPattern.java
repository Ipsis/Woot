package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * The result of scanning an area for a specific tier
 *
 * The scan can be successful or unsuccessful.
 * When it is successful then isComplete will be true.
 * When it is unsuccessful then there will be entries in the bad blocks list and you must check with the hasX methods
 * before accessing anything other than the methods getBlocks or getBadBlocks.
 */
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
        else if (FactoryBlock.isPowerFactoryBlock(factoryBlock))
            cellPos = new BlockPos(pos);
        else if (factoryBlock == FactoryBlock.IMPORT)
            importPos = new BlockPos(pos);
        else if (factoryBlock == FactoryBlock.EXPORT)
            exportPos = new BlockPos(pos);
    }

    private BlockPos controllerPos;
    private FakeMobKey controllerMob;
    private BlockPos cellPos;
    private BlockPos exportPos;
    private BlockPos importPos;

    public void setControllerMob(FakeMobKey fakeMobKey) { controllerMob = fakeMobKey; }

    public boolean hasController() { return controllerPos != null && controllerMob != null && controllerMob.isValid(); }
    public boolean hasCell() { return cellPos != null; }
    public boolean hasExport() { return exportPos != null; }
    public boolean hasImport() { return importPos != null; }

    public BlockPos getControllerPos() { return controllerPos; }
    public BlockPos getCellPos() { return cellPos; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }
    public FakeMobKey getControllerMob() { return controllerMob; }

    public boolean isComplete() {
        return !hasBadBlocks() && hasController() && hasCell() && hasImport() && hasExport();
    }


    /**
     * Bad Blocks
     */
    private List<BadLayoutBlockInfo> badBlocks = new ArrayList<>();
    public List<BadLayoutBlockInfo> getBadBlocks() { return this.badBlocks; }
    private boolean hasBadBlocks() { return !this.badBlocks.isEmpty(); }
    public void addBadBlock(BlockPos pos, BadBlockReason reason, FactoryBlock correctBlock, Block incorrectBlock) {
        badBlocks.add(new BadLayoutBlockInfo(new BlockPos(pos), reason, correctBlock, incorrectBlock));
    }

    public enum BadBlockReason {
        MISSING_BLOCK,
        INCORRECT_BLOCK,
        INCORRECT_TYPE,
        INVALID_MOB,
        MISSING_MOB;
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
