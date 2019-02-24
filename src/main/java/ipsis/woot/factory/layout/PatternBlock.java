package ipsis.woot.factory.layout;

import net.minecraft.util.math.BlockPos;

public class PatternBlock {
    private BlockPos pos;
    private FactoryBlock factoryBlock;
    public PatternBlock(FactoryBlock factoryBlock, BlockPos pos) {
        this.factoryBlock = factoryBlock;
        this.pos = pos;
    }

    public BlockPos getBlockPos() { return this.pos; }
    public FactoryBlock getFactoryBlock() { return this.factoryBlock; }

    @Override
    public String toString() { return String.format("%s (%s)", pos, factoryBlock); }
}
