package ipsis.woot;

import ipsis.woot.configuration.vanilla.GeneralConfig;
import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.dimensions.tartarus.WorldProviderTartarus;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModWorlds {

    public static DimensionType TARTARUS;

    public static void preInit() {

        TARTARUS = DimensionType.register("Tartarus", "_tr", GeneralConfig.TARTARUS_ID, WorldProviderTartarus.class, false);
        DimensionManager.registerDimension(GeneralConfig.TARTARUS_ID, TARTARUS);
    }
}
