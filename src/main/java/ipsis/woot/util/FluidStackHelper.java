package ipsis.woot.util;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackHelper {

    public static FluidStack getFluidStackFromName(String name, int amount) {

        return FluidRegistry.getFluidStack(name, amount);
    }
}
