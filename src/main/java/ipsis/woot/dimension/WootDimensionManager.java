package ipsis.woot.dimension;

import ipsis.woot.dimension.world.WootWorldProvider;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;

public class WootDimensionManager {

    private static int dimensionId = 666;
    private static DimensionType dimensionType;
    private boolean poked = false;

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

    public void touchSpawnChunk(World world) {

        if (!poked) {

            WorldServer worldServer = world.getMinecraftServer().getWorld(dimensionId);
            ChunkProviderServer chunkProviderServer = worldServer.getChunkProvider();

            if (!chunkProviderServer.chunkExists(0, 0)) {
                try {
                    chunkProviderServer.provideChunk(0, 0);
                    chunkProviderServer.chunkGenerator.populate(0, 0);
                } catch (Exception e) {
                    LogHelper.error("WootDimensionManager failed to create dimension");
                }
            }
        }

        poked = true;
    }
}
