package ipsis.woot;

import ipsis.woot.fluids.FluidPureDye;
import ipsis.woot.fluids.FluidPureEnchant;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

    public static final Fluid pureDye = new FluidPureDye();
    public static final Fluid pureEnchant = new FluidPureEnchant();

    public static void init() {
        FluidRegistry.registerFluid(pureDye);
        FluidRegistry.addBucketForFluid(pureDye);
        FluidRegistry.registerFluid(pureEnchant);
        FluidRegistry.addBucketForFluid(pureEnchant);
    }
}
