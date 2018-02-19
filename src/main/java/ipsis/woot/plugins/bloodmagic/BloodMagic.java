package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.core.registry.RitualRegistry;
import net.minecraftforge.fml.common.Optional;

public class BloodMagic {

    public static final String BM_MODID = "bloodmagic";

    @Optional.Method(modid = BM_MODID)
    public static void init() {

        registerRituals();

    }

    private static void registerRituals() {

        RitualRegistry.registerRitual(new RitualA());
    }
}
