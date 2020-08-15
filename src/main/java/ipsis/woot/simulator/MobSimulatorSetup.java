package ipsis.woot.simulator;

import ipsis.woot.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.registries.ObjectHolder;

public class MobSimulatorSetup {

    public static final ResourceLocation TARTARUS_DIMENSION_ID = new ResourceLocation(Woot.MODID, "tartarus");

    // TODO dimensions
    /*
    @ObjectHolder(Woot.MODID + ":tartarus")
    public static ModDimension TARTARUS; */
    public static DimensionType TARTARUS_DIMENSION_TYPE;
}
