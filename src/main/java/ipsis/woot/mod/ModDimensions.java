package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.simulation.dimension.TartarusChunkGenerator;
import ipsis.woot.simulation.dimension.TartarusDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.BiFunction;

public class ModDimensions {

    public static final ResourceLocation TARTARUS_DIMENSION_ID = new ResourceLocation(Woot.MODID, "tartarus");

    @ObjectHolder(Woot.MODID + ":tartarus")
    public static ModDimension TARTARUS;

    public static DimensionType TARTARUS_DIMENSION_TYPE;

}
