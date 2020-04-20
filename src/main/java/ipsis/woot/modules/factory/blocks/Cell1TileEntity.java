package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FactorySetup;

public class Cell1TileEntity extends CellTileEntityBase {

    public Cell1TileEntity() {
        super(FactorySetup.CELL_1_BLOCK_TILE.get());
    }

    @Override
    public int getCapacity() {
        return FactoryConfiguration.CELL_1_CAPACITY.get();
    }

    @Override
    public int getMaxTransfer() {
        return FactoryConfiguration.CELL_1_MAX_TRANSFER.get();
    }
}
