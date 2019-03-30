package ipsis.woot.factory;

import ipsis.woot.config.FactoryConfig;
import ipsis.woot.mod.ModTileEntities;

public class TileEntityCellUltimate extends TileEntityCell {

    public static final String BASENAME = "cell_3";
    public TileEntityCellUltimate() {
        super(ModTileEntities.cellUltimateTileEntityType);
        capacity = FactoryConfig.CELL_3_CAPACITY.get();
    }
}
