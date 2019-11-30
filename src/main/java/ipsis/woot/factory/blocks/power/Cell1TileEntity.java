package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.configuration.WootConfig;
import ipsis.woot.mod.ModBlocks;

public class Cell1TileEntity extends CellTileEntityBase {

    public Cell1TileEntity() {
        super(ModBlocks.CELL_1_BLOCK_TILE);
    }

    @Override
    int getCapacity() {
        return WootConfig.get().getIntConfig(WootConfig.ConfigKey.CELL_1_CAPACITY);
    }

    @Override
    int getMaxTransfer() {
        return WootConfig.get().getIntConfig(WootConfig.ConfigKey.CELL_1_MAX_TRANSFER);
    }
}
