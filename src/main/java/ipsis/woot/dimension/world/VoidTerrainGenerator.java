package ipsis.woot.dimension.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

public class VoidTerrainGenerator {

    public VoidTerrainGenerator() {

    }

    private void setBlockState(ChunkPrimer chunkPrimer, int index, IBlockState state) {
    }

    private void generate(int chunkX, int chunkZ, ChunkPrimer chunkPrimer) {
        for (int i = 0; i < 65535; i++) {
            setBlockState(chunkPrimer, i, Blocks.AIR.getDefaultState());
        }
    }
}
