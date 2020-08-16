package ipsis.woot.simulator.tartarus;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import ipsis.woot.Woot;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

public class TartarusChunkGenerator extends ChunkGenerator {

    public static final TartarusChunkGenerator INSTANCE = new TartarusChunkGenerator();
    public static final Codec<TartarusChunkGenerator> codecTartarusChunk = MapCodec.of(Encoder.empty(), Decoder.unit(() -> {
                return INSTANCE;
            })).stable().codec();

    private TartarusChunkGenerator() {
        super(new SingleBiomeProvider(Biomes.PLAINS), new DimensionStructuresSettings(false));
    }

    private static final int WORK_CHUNK_X = 0;
    private static final int WORK_CHUNK_Z = 0;

    private List<BlockPos> cell0Blocks;
    private List<BlockPos> cell1Blocks;
    private List<BlockPos> cell2Blocks;
    private List<BlockPos> cell3Blocks;

    private void calcCellStructures() {

        if (cell1Blocks != null)
            return;

        List<BlockPos> fullBlocks = new ArrayList<>(8 * 8 * 8);
        for (int x = 0; x < 8; x++) {
            for (int z = 0; z < 8; z++) {
                for (int y = 0; y < 8; y++) {
                    fullBlocks.add(new BlockPos(x, y, z));
                }
            }
        }

        List<BlockPos> innerBlocks = new ArrayList<>(8 * 8 * 8);
        for (int x = 1; x < 7; x++) {
            for (int z = 1; z < 7; z++) {
                for (int y = 1; y < 7; y++) {
                    innerBlocks.add(new BlockPos(x, y, z));
                }
            }
        }

        cell0Blocks = new ArrayList<>();
        for (BlockPos p : fullBlocks) {
            if (!innerBlocks.contains(p))
                cell0Blocks.add(new BlockPos(p));
        }

        cell1Blocks = new ArrayList<>(cell0Blocks.size());
        for (BlockPos p : cell0Blocks)
            cell1Blocks.add(new BlockPos(p.getX() + 8, p.getY(), p.getZ()));
        cell2Blocks = new ArrayList<>(cell0Blocks.size());
        for (BlockPos p : cell0Blocks)
            cell2Blocks.add(new BlockPos(p.getX() + 8, p.getY(), p.getZ() + 8));
        cell3Blocks = new ArrayList<>(cell0Blocks.size());
        for (BlockPos p : cell0Blocks)
            cell3Blocks.add(new BlockPos(p.getX(), p.getY(), p.getZ() + 8));

        fullBlocks = null;
        innerBlocks = null;
    }

    private void buildCell(IChunk iChunk, List<BlockPos> posList, int y, BlockState blockState) {
        for (BlockPos pos : posList)
            iChunk.setBlockState(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ()), blockState, false);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ChunkGenerator withSeed(long p_230349_1_) {
        return this;
    }

    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, IChunk iChunk) {

        /**
         * This is all based off chunk coordinates - therefore 0->16
         */
        Woot.setup.getLogger().info("generateSurface: {}", iChunk);

        BlockState blockState = Blocks.AIR.getDefaultState();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x1 = 0; x1 < 16; x1++) {
            for (int z1 = 0; z1 < 16; z1++) {
                for (int y1 = 0; y1 < 256; y1++) {
                    iChunk.setBlockState(pos.setPos(x1, y1, z1), blockState, false);
                }
            }
        }

        if (iChunk.getPos().x == WORK_CHUNK_X && iChunk.getPos().z == WORK_CHUNK_Z) {
            Woot.setup.getLogger().info("generateSurface: work chunk creating cells");
            BlockState wallState = Blocks.GLASS.getDefaultState();
            calcCellStructures();

            for (int y = 0; y < 256; y += 8) {
                buildCell(iChunk, cell0Blocks, y, wallState);
                buildCell(iChunk, cell1Blocks, y, wallState);
                buildCell(iChunk, cell2Blocks, y, wallState);
                buildCell(iChunk, cell3Blocks, y, wallState);
            }
        }
    }

    @Override
    public void carve(long p_230350_1_, BiomeManager p_230350_3_, IChunk p_230350_4_, GenerationStage.Carving p_230350_5_) {
    }


    @Override
    public void generateFeatures(WorldGenRegion p_230351_1_, StructureManager p_230351_2_) {
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return codecTartarusChunk;
    }

    @Override
    public void populateNoise(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_) {
    }

    @Override
    public int func_222529_a(int i, int i1, Heightmap.Type type) {
        return 0;
    }

    @Override
    public IBlockReader getColumnSample(int p_230348_1_, int p_230348_2_) {
        return new Blockreader((new BlockState[0]));
    }
}
