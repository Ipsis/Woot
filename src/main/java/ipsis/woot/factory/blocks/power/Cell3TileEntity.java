package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.Config;
import ipsis.woot.mod.ModBlocks;

public class Cell3TileEntity extends CellTileEntityBase {

    public Cell3TileEntity() {
        super(ModBlocks.CELL_3_BLOCK_TILE);
    }

    @Override
    int getCapacity() {
        return Config.COMMON.CELL_3_CAPACITY.get();
    }

    @Override
    int getMaxTransfer() {
        return Config.COMMON.CELL_3_MAX_TRANSFER.get();
    }
}
