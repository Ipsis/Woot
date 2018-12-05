package ipsis.woot.generators;

import ipsis.woot.factory.recipes.IWootUnitProvider;

public class TileEntityGeneratorTick extends TileEntityGenerator implements IWootUnitProvider {

    public TileEntityGeneratorTick() {
        super();
    }

    @Override
    public int consume(int units) {
        return units;
    }
}
