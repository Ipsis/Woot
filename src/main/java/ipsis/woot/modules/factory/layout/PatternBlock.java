package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.FactoryComponent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PatternBlock {

    private BlockPos pos;
    private FactoryComponent component;
    public PatternBlock(FactoryComponent component, BlockPos pos) {
        this.component = component;
        this.pos = pos;
    }

    public BlockPos getBlockPos() { return this.pos; }
    public FactoryComponent getFactoryComponent() { return this.component; }

    @Override
    public String toString() { return String.format("%s (%s)", pos, component); }
}
