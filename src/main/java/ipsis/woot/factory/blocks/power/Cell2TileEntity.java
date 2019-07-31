package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.Config;
import ipsis.woot.mod.ModBlocks;

public class Cell2TileEntity extends CellTileEntityBase {

    public Cell2TileEntity() {
        super(ModBlocks.CELL_2_BLOCK_TILE);
    }

    @Override
    int getCapacity() {
        return Config.COMMON.CELL_2_CAPACITY.get();
    }

    @Override
    int getMaxTransfer() {
        return Config.COMMON.CELL_2_MAX_TRANSFER.get();
    }
}
