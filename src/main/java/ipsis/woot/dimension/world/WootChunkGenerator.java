package ipsis.woot.dimension.world;

import com.google.common.collect.ImmutableList;
import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;

public class WootChunkGenerator implements IChunkGenerator {

    private final World world;
    private Biome[] biomesForGeneration;

    public WootChunkGenerator(World world) {

        this.world = world;
    }


    @Override
    public Chunk generateChunk(int x, int z) {

        LogHelper.info("generateChunk @ " + x + ", " + z);

        ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.setBlocksInChunk(x, z, chunkPrimer);

        Chunk chunk = new Chunk(this.world, chunkPrimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);

        chunk.generateSkylightMap();
        return chunk;
    }

    private void setBlocksInChunk(int x, int z, ChunkPrimer chunkPrimer) {

        // 16 x 16 x 256 blocks
        int chunkX = x * 16;
        int chunkZ = z * 16;

        // Everything is air - it is a void dimension
        for (int xx = 0; xx < 16; xx++) {
            for (int zz = 0; zz < 16; zz++) {
                for (int y = 0; y < 256; y++) {
                    chunkPrimer.setBlockState(chunkX + xx, y, chunkZ + zz, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    @Override
    public void populate(int x, int z) {

        LogHelper.info("populate @ " + x + ", " + z);
        if (x == 0 && z == 0)
            Woot.dimensionSchoolManager.build(world, x, z);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {

        LogHelper.info("generateStructures ~ " + x + ", " + z);
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return ImmutableList.of();
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
