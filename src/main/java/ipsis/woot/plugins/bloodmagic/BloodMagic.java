package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.ritual.RitualRegistry;
import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.oss.LogHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

public class BloodMagic {

    public static final String BM_MODID = "bloodmagic";
    private static final String LIFE_ESSENCE_NAME = "lifeessence";
    public static final int SACRIFICE_AMOUNT = 25;
    public static FluidStack lifeEssence = null;

    @Optional.Method(modid = BM_MODID)
    public static void init() {

        registerRituals();
        setupLifeEssence();
    }

    private static void registerRituals() {

        if (Woot.wootConfiguration.getBoolean(EnumConfigKey.ALLOW_BM_LE_TANK)) {
            LogHelper.info("Adding Ritual of the Sanguine Urn");
            RitualRegistry.registerRitual(new RitualLifeEssenceTank());
        }
        if (Woot.wootConfiguration.getBoolean(EnumConfigKey.ALLOW_BM_LE_ALTAR)) {
            LogHelper.info("Adding Ritual of the Mechanical Altar");
            RitualRegistry.registerRitual(new RitualLifeEssenceAltar());
        }

        RitualRegistry.registerRitual(new RitualClonedSoul());
    }

    private static void setupLifeEssence() {

        if (FluidRegistry.isFluidRegistered(LIFE_ESSENCE_NAME)) {
            Fluid fluid = FluidRegistry.getFluid(LIFE_ESSENCE_NAME);
            lifeEssence = new FluidStack(fluid, 0);
        }
    }
}
