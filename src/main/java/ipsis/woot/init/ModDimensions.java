package ipsis.woot.init;

import ipsis.woot.dimension.world.WootWorldProvider;
import ipsis.woot.reference.Reference;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {

    public static int wootDimensionId = 666;
    public static DimensionType wootDimensionType;

    public static void init() {

    }

    private static void registerDimensionTypes() {
        wootDimensionType = DimensionType.register(Reference.MOD_ID , "_lootlearn", wootDimensionId, WootWorldProvider.class, true);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(wootDimensionId, wootDimensionType);
    }
}
