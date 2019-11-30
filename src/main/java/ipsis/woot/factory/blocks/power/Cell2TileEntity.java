package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.configuration.Config;
import ipsis.woot.common.configuration.WootConfig;
import ipsis.woot.mod.ModBlocks;

public class Cell2TileEntity extends CellTileEntityBase {

    public Cell2TileEntity() {
        super(ModBlocks.CELL_2_BLOCK_TILE);
    }

    @Override
    int getCapacity() {
        return WootConfig.get().getIntConfig(WootConfig.ConfigKey.CELL_2_CAPACITY);
    }

    @Override
    int getMaxTransfer() {
        return WootConfig.get().getIntConfig(WootConfig.ConfigKey.CELL_2_MAX_TRANSFER);
    }
}
