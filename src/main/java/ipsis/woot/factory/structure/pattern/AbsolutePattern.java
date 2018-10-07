package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbsolutePattern {

    private FactoryTier factoryTier;
    private List<AbsoluteBlock> blocks = new ArrayList<>();

    private AbsolutePattern() {}
    public AbsolutePattern(FactoryTier factoryTier) { this.factoryTier = factoryTier; }

    public void addAbsoluteBlock(BlockPos pos, FactoryBlock factoryBlock) {
        blocks.add(new AbsoluteBlock(factoryBlock, pos));
    }

    public List<AbsoluteBlock> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public void offsetInY(int offset) {
        for (AbsoluteBlock block : blocks) {
            if (offset >= 1)
                block.pos = block.pos.up(Math.abs(offset));
            else if (offset <= -1)
                block.pos = block.pos.down(Math.abs(offset));
        }
    }

    /**
     * Compare this pattern with the world state
     * Reports all incorrect blocks
     */
    public ScannedPattern compareToWorld() {

        return new ScannedPattern(factoryTier);
    }

    /**
     * Compare this pattern with the world state
     * Returns the minute a bad block is found and doesn't report incorrect block positions
     */
    public ScannedPattern compareToWorldQuick() {

        return new ScannedPattern(factoryTier);
    }

    public class AbsoluteBlock {

        protected BlockPos pos;
        protected FactoryBlock factoryBlock;

        public AbsoluteBlock(FactoryBlock factoryBlock, BlockPos pos) {
            this.factoryBlock = factoryBlock;
            this.pos = pos;
        }

        public BlockPos getPos() { return this.pos; }
        public FactoryBlock getFactoryBlock() { return this.factoryBlock; }

        @Override
        public String toString() {
            return String.format("%s (%s)", pos, factoryBlock.getName());
        }
    }
}
