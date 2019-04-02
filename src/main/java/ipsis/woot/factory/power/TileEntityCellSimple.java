package ipsis.woot.factory.power;

import ipsis.woot.config.FactoryConfig;
import ipsis.woot.factory.power.TileEntityCell;
import ipsis.woot.mod.ModTileEntities;

public class TileEntityCellSimple extends TileEntityCell {

    public static final String BASENAME = "cell_1";
    public TileEntityCellSimple() {
        super(ModTileEntities.cellSimpleTileEntityType);
        capacity = FactoryConfig.CELL_1_CAPACITY.get();
    }
}