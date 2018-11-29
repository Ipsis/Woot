package ipsis.woot.blocks.generators;

import ipsis.woot.factory.recipes.IWootUnitProvider;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGeneratorRF extends TileEntity implements IWootUnitProvider {

    @Override
    public int consume(int units) {
        return units;
    }
}
