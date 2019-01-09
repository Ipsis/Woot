package ipsis.woot.fluids;

import ipsis.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidPureEnchant extends Fluid {

    public FluidPureEnchant() {
        super("pureenchant",
                new ResourceLocation(Woot.MODID, "blocks/pureenchant_still"),
                new ResourceLocation(Woot.MODID, "blocks/pureenchant_flow"));
    }
}
