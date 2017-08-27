package ipsis.woot.tileentity;

import net.minecraft.util.math.BlockPos;

public interface ILayoutBlockInfo {

    BlockPos getPos();
    void offsetY(int offset);
}
