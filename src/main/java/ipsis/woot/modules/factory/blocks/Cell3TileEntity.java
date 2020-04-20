package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FactorySetup;

public class Cell3TileEntity extends CellTileEntityBase {

    public Cell3TileEntity() {
        super(FactorySetup.CELL_3_BLOCK_TILE.get());
    }

    @Override
    public int getCapacity() {
        return FactoryConfiguration.CELL_3_CAPACITY.get();
    }

    @Override
    public int getMaxTransfer() {
        return FactoryConfiguration.CELL_3_MAX_TRANSFER.get();
    }
}
