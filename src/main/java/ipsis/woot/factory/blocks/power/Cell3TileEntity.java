package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.configuration.WootConfig;
import ipsis.woot.mod.ModBlocks;

public class Cell3TileEntity extends CellTileEntityBase {

    public Cell3TileEntity() {
        super(ModBlocks.CELL_3_BLOCK_TILE);
    }

    @Override
    int getCapacity() {
        return WootConfig.get().getIntConfig(WootConfig.ConfigKey.CELL_3_CAPACITY);
    }

    @Override
    int getMaxTransfer() {
        return WootConfig.get().getIntConfig(WootConfig.ConfigKey.CELL_3_MAX_TRANSFER);
    }
}
