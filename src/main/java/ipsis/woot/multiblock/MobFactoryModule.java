package ipsis.woot.multiblock;

import net.minecraft.util.math.BlockPos;

public class MobFactoryModule {

    BlockPos offset;
    EnumMobFactoryModule moduleType;

    public MobFactoryModule(BlockPos offset, EnumMobFactoryModule moduleType) {

        this.offset = offset;
        this.moduleType = moduleType;
    }

    /**
     * This always returns a new BlockPos
     */
    public BlockPos getOffsetBlock(BlockPos currPos) {

        return currPos.add(offset.getX(), offset.getY(), offset.getZ());
    }

    public EnumMobFactoryModule getModuleType() { return this.moduleType; }
    public BlockPos getOffset() { return this.offset; }

    @Override
    public String toString() {
        return offset + " : " + moduleType;
    }
}
