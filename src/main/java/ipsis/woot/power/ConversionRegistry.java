package ipsis.woot.power;

import net.minecraftforge.fluids.FluidStack;

public class ConversionRegistry {

    public void init() {

        // TODO setup fluid->ratio mappings
        // TODO setup RF->ratio mapping
    }

    public int convertFromRF(int rf) {
        return rf;
    }

    public int convertFromFluid(FluidStack fluidStack) {

        if (fluidStack == null)
            return 0;

        return fluidStack.amount;
    }
}
