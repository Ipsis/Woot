package ipsis.woot.mod;

import ipsis.woot.simulation.dimension.TartarusChunkGenerator;
import ipsis.woot.simulation.dimension.TartarusDimension;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

public class ModDimensions {

    public static DimensionType tartarusDimensionType;
    public static ChunkGeneratorType<GenerationSettings, TartarusChunkGenerator> tartarusChunkGeneratorType =
            new ChunkGeneratorType<>(TartarusChunkGenerator::new, false, GenerationSettings::new);

    public static ModDimension tartarusModDimension = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return TartarusDimension::new;
        }
    };

}
