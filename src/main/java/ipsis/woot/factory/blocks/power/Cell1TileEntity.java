package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.configuration.Config;
import ipsis.woot.mod.ModBlocks;

public class Cell1TileEntity extends CellTileEntityBase {

    public Cell1TileEntity() {
        super(ModBlocks.CELL_1_BLOCK_TILE);
    }

    @Override
    int getCapacity() {
        return Config.COMMON.CELL_1_CAPACITY.get();
    }

    @Override
    int getMaxTransfer() {
        return Config.COMMON.CELL_1_MAX_TRANSFER.get();
    }
}
