package ipsis.woot.util;

import ipsis.woot.ModFluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class WootFluidTank extends FluidTank {

    public WootFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return fluid != null && fluid.getFluid() == ModFluids.effort;
    }
}
