package ipsis.woot.simulator.tartarus;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;

import javax.annotation.Nullable;

//public class TartarusDimension extends Dimension {
//    public TartarusDimension(World world, DimensionType dimensionType) {
//        super(world, dimensionType, 0.0f);
//    }
//
//    @Override
//    public ChunkGenerator<?> createChunkGenerator() {
//        return new TartarusChunkGenerator(world, new SingleBiomeProvider(
//                new SingleBiomeProviderSettings(this.world.getWorldInfo()).setBiome(Biomes.THE_VOID)));
//    }
//
//    @Nullable
//    @Override
//    public BlockPos findSpawn(ChunkPos p_206920_1_, boolean checkValid) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public BlockPos findSpawn(int p_206921_1_, int p_206921_2_, boolean checkValid) {
//        return null;
//    }
//
//    @Override
//    public int getActualHeight() {
//        return 256;
//    }
//
//    @Override
//    public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
//        return SleepResult.DENY;
//    }
//
//    @Override
//    public float calculateCelestialAngle(long worldTime, float partialTicks) {
//        double d0 = MathHelper.frac((double) worldTime / 24000.0D - 0.25D);
//        double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
//        return (float) (d0 * 2.0D + d1) / 3.0F;
//    }
//
//    @Override
//    public boolean isSurfaceWorld() {
//        return true;
//    }
//
//    @Override
//    public boolean hasSkyLight() {
//        return true;
//    }
//
//    @Override
//    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
//        return new Vec3d(0, 0, 0);
//    }
//
//    @Override
//    public boolean canRespawnHere() {
//        return false;
//    }
//
//    @Override
//    public boolean doesXZShowFog(int x, int z) {
//        return false;
//    }
//
//
//}
