package ipsis.woot.dimensions.tartarus;

import ipsis.Woot;
import ipsis.woot.util.Debug;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class ChunkGeneratorTartarus implements IChunkGenerator {

    private final World world;
    private Biome[] biomes;

    public ChunkGeneratorTartarus(World world) {
        this.world = world;
    }

    @Override
    public Chunk generateChunk(int x, int z) {

        if (Woot.debugging.isEnabled(Debug.Group.TARTARUS))
            Woot.debugging.trace(Debug.Group.TARTARUS, "ChunkGeneratorTartarus:generateChunk %d:%d", x, z);

        ChunkPrimer primer = new ChunkPrimer();
        this.biomes = this.world.getBiomeProvider().getBiomes(this.biomes, x * 16, z * 16, 16, 16);

        /**
         * Nothing but air
         */
        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                for (int dy = 0; dy < 256; dy++) {
                    IBlockState iBlockState = Blocks.AIR.getDefaultState();
                    primer.setBlockState(dx, dy, dz, iBlockState);
                }
            }
        }

        /**
         * TODO - Remove access transformer for primer.data
        for (int i = 0; i < 65536; i++)
            primer.data[i] = (char) Block.BLOCK_STATE_IDS.get(Blocks.AIR.getDefaultState());
            */

        Chunk chunk = new Chunk(this.world, primer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; i++)
            abyte[i] = (byte)Biome.getIdForBiome(this.biomes[i]);

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {

        if (Woot.debugging.isEnabled(Debug.Group.TARTARUS))
            Woot.debugging.trace(Debug.Group.TARTARUS, "ChunkGeneratorTartarus:populate %d:%d", x, z);

        TartarusManager.build(this.world, x, z);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) { }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
