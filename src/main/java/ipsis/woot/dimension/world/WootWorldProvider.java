package ipsis.woot.dimension.world;

import ipsis.Woot;
import ipsis.woot.reference.Reference;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

import static ipsis.woot.dimension.WootDimensionManager.CHUNK_X;
import static ipsis.woot.dimension.WootDimensionManager.CHUNK_Z;

public class WootWorldProvider extends WorldProvider {


    @Override
    public DimensionType getDimensionType() {
        return Woot.wootDimensionManager.getDimensionType();
    }

    @Override
    public String getSaveFolder() {

        return Reference.MOD_ID + "_tartarus";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {

        return new WootChunkGenerator(world);
    }

    @Override
    public boolean canDropChunk(int x, int z) {

        return x != CHUNK_X && z != CHUNK_Z;
    }
}
