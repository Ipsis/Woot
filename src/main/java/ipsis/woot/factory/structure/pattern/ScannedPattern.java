package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryTier;
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
    private List<BadLayoutBlockInfo> badBlocks = new ArrayList<>();

    public List<BadLayoutBlockInfo> getBadBlocks() { return this.badBlocks; }
    public List<BlockPos> getBlocks() { return this.blocks; }

    public boolean hasBadBlocks() { return !this.badBlocks.isEmpty(); }

    public enum BadBlockReason {
        MISSING_BLOCK,
        INCORRECT_BLOCK,
    }

    public class BadLayoutBlockInfo {

        public BlockPos pos;
        public BadBlockReason reason;
        public Block correctBlock;
        public Block incorrectBlock;

        public BadLayoutBlockInfo(BlockPos pos, BadBlockReason reason, Block correctBlock, Block incorrectBlock) {
            this.pos = new BlockPos(pos);
            this.reason = reason;
            this.correctBlock = correctBlock;
            this.incorrectBlock = incorrectBlock;
        }
    }

}
