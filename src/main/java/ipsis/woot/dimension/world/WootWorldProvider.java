package ipsis.woot.dimension.world;

import ipsis.Woot;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WootWorldProvider extends WorldProvider {


    @Override
    public DimensionType getDimensionType() {
        return Woot.wootDimensionManager.getDimensionType();
    }

    @Override
    public String getSaveFolder() {

        return "tartarus";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {

        return new WootChunkGenerator(world);
    }
}
