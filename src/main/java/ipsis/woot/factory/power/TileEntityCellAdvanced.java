package ipsis.woot.factory.power;

import ipsis.woot.config.FactoryConfig;
import ipsis.woot.factory.power.TileEntityCell;
import ipsis.woot.mod.ModTileEntities;

public class TileEntityCellAdvanced extends TileEntityCell {

    public static final String BASENAME = "cell_2";
    public TileEntityCellAdvanced() {
        super(ModTileEntities.cellAdvancedTileEntityType);
        capacity = FactoryConfig.CELL_2_CAPACITY.get();
    }
}
