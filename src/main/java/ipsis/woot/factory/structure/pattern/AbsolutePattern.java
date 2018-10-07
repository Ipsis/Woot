package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
     * ie. All the blocks have to be present
     */
    public @Nullable ScannedPattern compareToWorldQuick(@Nonnull World world) {

        boolean valid = true;
        for (AbsoluteBlock absoluteBlock : blocks)
        {
            // Don't load an unloaded chunk
            if (!world.isBlockLoaded(absoluteBlock.pos)) {
                valid = false;
                break;
            }

            Block block = world.getBlockState(absoluteBlock.pos).getBlock();
            if (FactoryBlock.isSameBlock(absoluteBlock.getFactoryBlock(), block)) {
                valid = false;
                break;
            }
        }

        return valid ? new ScannedPattern(factoryTier) : null;
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
