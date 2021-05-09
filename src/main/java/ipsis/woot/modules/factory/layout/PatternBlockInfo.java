package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.ComponentType;
import net.minecraft.util.math.BlockPos;

public class PatternBlockInfo {

    public BlockPos offset; // offset from the origin block ie. the heart
    public ComponentType componentType;

    private PatternBlockInfo() {}
    public PatternBlockInfo(BlockPos offset, ComponentType componentType) {
        this.offset = offset;
        this.componentType = componentType;
    }
}
