package ipsis.woot.power;

import ipsis.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidEffort extends Fluid {

    public FluidEffort() {
        super("effort",
                new ResourceLocation(Woot.MODID, "blocks/effort_still"),
                new ResourceLocation(Woot.MODID, "blocks/effort_flow"));
    }
}
