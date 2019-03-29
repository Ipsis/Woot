package ipsis.woot.factory.layout;

import ipsis.woot.factory.FactoryTier;
import net.minecraft.util.math.BlockPos;

import java.util.*;

/**
 * A factory pattern located around a physical origin
 */
public class AbsolutePattern {

    private FactoryTier factoryTier;

    private List<PatternBlock> blocks = new ArrayList<>();
    public List<PatternBlock> getBlocks() { return Collections.unmodifiableList(blocks); }

    public AbsolutePattern(FactoryTier factoryTier) {
        this.factoryTier = factoryTier;
    }

    public void addAbsoluteBlock(FactoryBlock factoryBlock, BlockPos pos) {
        blocks.add(new PatternBlock(factoryBlock, pos));
    }

    public FactoryTier getFactoryTier() { return this.factoryTier; }
}
