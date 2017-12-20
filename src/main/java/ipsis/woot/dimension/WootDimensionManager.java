package ipsis.woot.dimension;

import ipsis.woot.dimension.world.WootWorldProvider;
import ipsis.woot.loot.schools.TartarusManager;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;

public class WootDimensionManager {

    private static int dimensionId = 666;
    private static DimensionType dimensionType;
    private boolean touched = false;

    public void init() {

        dimensionType = DimensionType.register(Reference.MOD_ID , "_lootlearn", dimensionId, WootWorldProvider.class, true);
        DimensionManager.registerDimension(dimensionId, dimensionType);
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public @Nullable WorldServer getWorldServer(World world) {

        return world.getMinecraftServer().getWorld(dimensionId);
    }

    public void touchSpawnChunk(World world) {

        if (!touched) {

            LogHelper.info("WootDimensionManager: generating chunks");

            WorldServer worldServer = world.getMinecraftServer().getWorld(dimensionId);
            ChunkProviderServer chunkProviderServer = worldServer.getChunkProvider();

            if (!chunkProviderServer.chunkExists(TartarusManager.CHUNK_X, TartarusManager.CHUNK_Z)) {
                try {
                    chunkProviderServer.provideChunk(TartarusManager.CHUNK_X, TartarusManager.CHUNK_Z);
                    chunkProviderServer.chunkGenerator.populate(TartarusManager.CHUNK_X, TartarusManager.CHUNK_Z);
                } catch (Exception e) {
                    LogHelper.error("WootDimensionManager failed to create dimension");
                }
            }

            touched = true;
        }
    }
}
