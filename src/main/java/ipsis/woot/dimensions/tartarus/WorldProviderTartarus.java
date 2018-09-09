package ipsis.woot.dimensions.tartarus;

import ipsis.Woot;
import ipsis.woot.ModWorlds;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;

public class WorldProviderTartarus extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return ModWorlds.TARTARUS;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorTartarus(world);
    }

    @Override
    public boolean canDropChunk(int x, int z) {
        return !TartarusManager.isWorkChunk(x, z);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Nullable
    @Override
    public String getSaveFolder() {
        return "DIM" + Woot.MODID.toUpperCase() + "-TARTARUS";
    }
}
