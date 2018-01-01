package ipsis.woot.dimension.world;

import com.google.common.collect.ImmutableList;
import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
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

import static ipsis.woot.dimension.WootDimensionManager.CHUNK_X;
import static ipsis.woot.dimension.WootDimensionManager.CHUNK_Z;

public class WootChunkGenerator implements IChunkGenerator {

    private final World world;
    private Biome[] biomesForGeneration;

    public WootChunkGenerator(World world) {

        this.world = world;
    }

    @Override
    public Chunk generateChunk(int x, int z) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "generateChunk", "Generating chunks " + x + ", " + z);

        ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);

        // Everything is air as it is void
        for (int i = 0; i < 65536; i++)
            chunkPrimer.data[i] = (char)Block.BLOCK_STATE_IDS.get(Blocks.AIR.getDefaultState());

        Chunk chunk = new Chunk(this.world, chunkPrimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {

        if (x == CHUNK_X && z == CHUNK_Z) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "populate", "Populating chunk " + x + ", " + z);
            Woot.tartarusManager.build(world);
        }
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {

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
