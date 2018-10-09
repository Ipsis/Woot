package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
     * ALWAYS returns the scanned information
     */
    boolean valid = true;
    public ScannedPattern compareToWorld(@Nonnull World world) {

        ScannedPattern scanned = new ScannedPattern(factoryTier);

        for (AbsoluteBlock absoluteBlock : blocks) {
            // Don't load an unloaded chunk
            if (!world.isBlockLoaded(absoluteBlock.pos)) {
                valid = false;
                scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.MISSING_BLOCK, absoluteBlock.getFactoryBlock(), Blocks.AIR);
                continue;
            }

            Block block = world.getBlockState(absoluteBlock.pos).getBlock();
            FactoryBlock factoryBlock = FactoryBlock.getFactoryBlock(block);

            if (factoryBlock == null) {
                valid = false;
                scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.INCORRECT_BLOCK, absoluteBlock.getFactoryBlock(), block);
                continue;
            }

            if (factoryBlock != absoluteBlock.getFactoryBlock()) {
                valid = false;
                scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.INCORRECT_TYPE, absoluteBlock.getFactoryBlock(), block);
                continue;
            }

            scanned.addGoodBlock(absoluteBlock.pos, absoluteBlock.getFactoryBlock());
            if (factoryBlock == FactoryBlock.CONTROLLER) {
                // TODO set the mob
            }
        }

        return scanned;
    }

    /**
     * Compare this pattern with the world state
     * Returns the minute a bad block is found and doesn't report incorrect block positions
     * ie. All the blocks have to be present
     */
    public @Nullable ScannedPattern compareToWorldQuick(@Nonnull World world) {

        boolean valid = true;
        for (AbsoluteBlock absoluteBlock : blocks) {
            // Don't load an unloaded chunk
            if (!world.isBlockLoaded(absoluteBlock.pos)) {
                valid = false;
                break;
            }

            Block block = world.getBlockState(absoluteBlock.pos).getBlock();
            FactoryBlock factoryBlock = FactoryBlock.getFactoryBlock(block);

            if (factoryBlock == null || factoryBlock != absoluteBlock.getFactoryBlock()) {
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
