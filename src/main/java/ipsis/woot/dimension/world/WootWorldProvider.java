package ipsis.woot.dimension.world;

import ipsis.woot.init.ModDimensions;
import ipsis.woot.reference.Reference;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public class WootWorldProvider extends WorldProvider {


    @Override
    public DimensionType getDimensionType() {
        return ModDimensions.wootDimensionType;
    }

    @Override
    public String getSaveFolder() {

        return "WOOT_TEST_DIMENSION";
    }
}
