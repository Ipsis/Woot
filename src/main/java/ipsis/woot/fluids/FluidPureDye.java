package ipsis.woot.fluids;

import ipsis.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidPureDye extends Fluid {

    public FluidPureDye() {
        super("puredye",
                new ResourceLocation(Woot.MODID, "blocks/puredye_still"),
                new ResourceLocation(Woot.MODID, "blocks/puredye_flow"));
    }
}
