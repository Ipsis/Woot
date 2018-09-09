package ipsis.woot;

import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.dimensions.tartarus.WorldProviderTartarus;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModWorlds {

    public static int tartarus_id = 418;
    public static DimensionType TARTARUS;

    public static void preInit() {

        TARTARUS = DimensionType.register("Tartarus", "_tr", tartarus_id, WorldProviderTartarus.class, false);
        DimensionManager.registerDimension(tartarus_id, TARTARUS);
    }
}
