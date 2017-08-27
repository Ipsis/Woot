package ipsis.woot.tileentity;

import net.minecraft.util.math.BlockPos;

public class ControllerLayoutBlockInfo implements  ILayoutBlockInfo {

    BlockPos blockPos;

    public ControllerLayoutBlockInfo(BlockPos pos) {

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
