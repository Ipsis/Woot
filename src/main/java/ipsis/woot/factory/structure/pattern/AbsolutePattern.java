package ipsis.woot.factory.structure.pattern;

import ipsis.Woot;
import ipsis.woot.blocks.TileEntityController;
import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
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

    boolean valid = true;

    /**
     * Compares the full pattern to the current world state and reports all incorrect blocks.
     * @return the scanned pattern which may not be complete
     */
    public @Nonnull ScannedPattern compareToWorld(@Nonnull World world) {

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
            } else if (factoryBlock != absoluteBlock.getFactoryBlock()) {
                valid = false;
                scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.INCORRECT_TYPE, absoluteBlock.getFactoryBlock(), block);
                continue;
            }

            if (factoryBlock == FactoryBlock.CONTROLLER) {
                valid = compareController(world, absoluteBlock, scanned, block);
            } else {
                scanned.addGoodBlock(absoluteBlock.pos, absoluteBlock.getFactoryBlock());
            }
        }

        return scanned;
    }

    private boolean compareController(World world, AbsoluteBlock absoluteBlock, ScannedPattern scanned, Block block) {

        boolean valid = true;
        TileEntity te = world.getTileEntity(absoluteBlock.pos);
        if (te instanceof TileEntityController) {
            TileEntityController controller = (TileEntityController)te;
            FakeMobKey fakeMobKey = controller.getFakeMobKey();
            if (!fakeMobKey.isValid()) {
                scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.MISSING_MOB, absoluteBlock.getFactoryBlock(), block);
                valid = false;
            } else if (!Woot.POLICY_MANAGER.canCaptureMob(fakeMobKey.getResourceLocation())) {
                scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.INVALID_MOB, absoluteBlock.getFactoryBlock(), block);
                valid = false;
            } else {
                scanned.addGoodBlock(absoluteBlock.pos, absoluteBlock.getFactoryBlock());
                scanned.setControllerMob(fakeMobKey);
            }
        } else {
            scanned.addBadBlock(absoluteBlock.pos, ScannedPattern.BadBlockReason.MISSING_BLOCK, absoluteBlock.getFactoryBlock(), Blocks.AIR);
            valid = false;
        }

        return valid;
    }

    /**
     * Compare this pattern with the world state
     * Returns the minute a bad block is found and doesn't report incorrect block positions
     * ie. All the blocks have to be present
     */

    /**
     * Compare this pattern with the world state
     * Will return on the first bad block is found and doesn't report incorrect block positions
     * @return the scanned pattern if everything is present or null on the first incorrect block.
     */
    public @Nullable ScannedPattern compareToWorldQuick(@Nonnull World world) {

        boolean valid = true;
        ScannedPattern scanned = new ScannedPattern(factoryTier);
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

            scanned.addGoodBlock(absoluteBlock.pos, absoluteBlock.getFactoryBlock());
            if (factoryBlock == FactoryBlock.CONTROLLER) {
                if (!(valid = compareController(world, absoluteBlock, scanned, block)))
                    break;
            }
        }

        return valid ? scanned : null;
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
