package ipsis.woot.tileentity;

import net.minecraft.util.math.BlockPos;

public class HeartLayoutBlockInfo implements  ILayoutBlockInfo {

    BlockPos blockPos;

    public HeartLayoutBlockInfo(BlockPos pos) {
        this.blockPos = pos;
    }

    @Override
    public BlockPos getPos() {
        return blockPos;
    }

    @Override
    public void offsetY(int offset) {

        this.blockPos = blockPos.up(offset);
    }
}
