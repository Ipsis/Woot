package ipsis.woot.mod;

import ipsis.woot.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

public class ModDimensions {

    public static final ResourceLocation TARTARUS_DIMENSION_ID = new ResourceLocation(Woot.MODID, "tartarus");

    @ObjectHolder(Woot.MODID + ":tartarus")
    public static ModDimension TARTARUS;

    public static DimensionType TARTARUS_DIMENSION_TYPE;

}
