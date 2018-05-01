package ipsis.woot.plugins.evilcraft;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

public class EvilCraft {

    public static final String EC_MODID = "evilcraft";
    private static final String BLOOD_NAME = "evilcraftblood";
    public static FluidStack blood = null;

    @Optional.Method(modid = EC_MODID)
    public static void init() {

        setupBlood();
    }

    private static void setupBlood() {

        if (FluidRegistry.isFluidRegistered(BLOOD_NAME)) {
            Fluid fluid = FluidRegistry.getFluid(BLOOD_NAME);
            blood = new FluidStack(fluid, 0);
        }
    }
}
