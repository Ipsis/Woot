package ipsis.woot.dimension;

import ipsis.Woot;
import ipsis.woot.command.ITextStatus;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.dimension.world.WootWorldProvider;
import ipsis.woot.handler.ConfigHandler;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.DebugSetup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WootDimensionManager implements ITextStatus {

    private static int dimensionId = -1;
    private static DimensionType dimensionType;
    private boolean touched = false;
    public static final int CHUNK_X = 0;
    public static final int CHUNK_Z = 0;

    public void init() {

        dimensionId = Woot.wootConfiguration.getInteger(EnumConfigKey.TARTARUS_ID);
        if (dimensionId == -1) {
            dimensionId = DimensionManager.getNextFreeDimId();
            ConfigHandler.saveDimensionId(dimensionId);
        }
        dimensionType = DimensionType.register("tartarus" , "_lootlearn", dimensionId, WootWorldProvider.class, false);
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

    private boolean hasChunkGenerated(WorldServer worldServer) {

        return worldServer != null && worldServer.isChunkGeneratedAt(CHUNK_X, CHUNK_Z);
    }

    private boolean isChunkLoaded(WorldServer worldServer) {

        return worldServer != null && worldServer.isBlockLoaded(new BlockPos(CHUNK_X * 16, 0, CHUNK_Z * 16));
    }

    public void touchSpawnChunk(World world) {

        if (!touched) {

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "touchSpawnChunk", "Generating chunks");

            WorldServer worldServer = world.getMinecraftServer().getWorld(dimensionId);
            ChunkProviderServer chunkProviderServer = worldServer.getChunkProvider();

            if (!chunkProviderServer.chunkExists(CHUNK_X, CHUNK_Z)) {
                try {
                    chunkProviderServer.provideChunk(CHUNK_X, CHUNK_Z);
                    chunkProviderServer.chunkGenerator.populate(CHUNK_X, CHUNK_Z);
                } catch (Exception e) {
                    LogHelper.error("WootDimensionManager failed to create dimension");
                }
            }

            touched = true;
        }
    }

    @Override
    public List<String> getStatus() {

        return new ArrayList<>();
    }

    @Override
    public List<String> getStatus(WorldServer worldServer) {

        List<String> status = new ArrayList<>();
        status.add("Dimension Id: " + getDimensionId());
        status.add("Chunk Generated: " + hasChunkGenerated(worldServer));
        status.add("Chunk loaded: " + isChunkLoaded(worldServer));
        return status;
    }
}
