package ipsis.woot.dimensions.tartarus;

import ipsis.Woot;
import ipsis.woot.ModWorlds;
import ipsis.woot.spawning.SpawnManager;
import ipsis.woot.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TartarusManager {

    private static final int WORK_CHUNK_X = 0;
    private static final int WORK_CHUNK_Z = 0;
    private static boolean cellsBuilt = false;

    public static final int INVALID_SPAWN_ID = -1;
    public static final int INVALID_CELL_ID = -1;
    private static HashMap<Integer, Cell> cells = new HashMap<>();

    public static boolean isWorkChunk(int chunkX, int chunkZ) {

        return chunkX == WORK_CHUNK_X && chunkZ == WORK_CHUNK_Z;
    }

    static {

        BlockPos origin = new BlockPos(WORK_CHUNK_X * 16, 0, WORK_CHUNK_Z * 16);

        int[] xOffsets = { 0, 8, 0, 8};
        int[] zOffsets = { 0, 0, 8, 8};

        int id = 0;
        for (int y = 0; y < 256; y += 8) {
            for (int i = 0; i < 4; i ++ ) {
                BlockPos basePos = new BlockPos(origin.getX() + xOffsets[i], y, origin.getZ() + zOffsets[i]);
                BlockPos spawnPos = basePos.add(4, 4, 4);
                cells.put(id, new Cell(basePos, spawnPos));
                id++;
            }
        }
    }

    public static void setWorld(World world) {

        touchWorkChunk(world);

        for (Cell cell : cells.values())
            cell.setWorld(world);
    }

    public static void build(World world, int chunkX, int chunkZ) {

        if (!isWorkChunk(chunkX, chunkZ))
            return;

        Woot.debugging.trace(Debug.Group.TARTARUS, "build x:" + chunkX + " z:" + chunkZ);

        for (Cell cell : cells.values())
            build(world, cell);

        cellsBuilt = true;
    }

    private static void build(World world, Cell cell) {

        Block b = Blocks.GLASS;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                for (int z = 0; z < 8; z++) {

                    if (y == 0 || y == 7) {
                        // top and bottom
                        BlockPos pos = new BlockPos(cell.getBasePos().getX() + x,
                                cell.getBasePos().getY() + y,
                                cell.getBasePos().getZ() + z);
                        world.setBlockState(pos, b.getDefaultState());
                    } else if (x == 0 || x == 7 || z == 0 || z == 7) {
                        // sides
                        BlockPos pos = new BlockPos(cell.getBasePos().getX() + x,
                                cell.getBasePos().getY() + y,
                                cell.getBasePos().getZ() + z);
                        world.setBlockState(pos, b.getDefaultState());
                    }
                }
            }
        }
    }

    private static boolean touchWorkChunk(World world) {

        Woot.debugging.trace(Debug.Group.TARTARUS, "touchWorkChunk: " + world);
        if (world == null)
            return false;

        WorldServer worldServer = world.getMinecraftServer().getWorld(ModWorlds.tartarus_id);
        if (worldServer == null)
            return false;

        ChunkProviderServer chunkProviderServer = worldServer.getChunkProvider();

        boolean ret = true;
        if (!chunkProviderServer.chunkExists(WORK_CHUNK_X, WORK_CHUNK_Z)) {
            try {
                chunkProviderServer.provideChunk(WORK_CHUNK_X, WORK_CHUNK_Z);
                chunkProviderServer.chunkGenerator.populate(WORK_CHUNK_X, WORK_CHUNK_Z);
            } catch (Exception e) {
                ret = false;
                LogHelper.error("touchWorkChunk failed " + e);
            }
        }

        return ret;
    }

    public static void getStatus(@Nonnull List<String> status) {

        status.add("Tartarus");
        status.add("Spawn box built: " + cellsBuilt);

        for (Integer id : cells.keySet()) {
            Cell cell = cells.get(id);
            if (cell.isUsed())
                status.add("Cell used: " + id + "/" + cell.fakeMobKey + "/l" + cell.looting);
        }
    }

    /**
     *
     */
    public static int assignCell(FakeMobKey fakeMobKey, int looting) {

        int id = INVALID_SPAWN_ID;
        looting = MiscUtils.clampLooting(looting);

        for (Integer i : cells.keySet()) {
            Cell c = cells.get(i);
            if (!c.isUsed()) {
                c.setUsed(fakeMobKey, looting);
                id = i;
                break;
            }
        }

        return id;
    }

    public static int freeCell(int id) {

        Cell cell = cells.get(id);
        if (cell != null) {
            cell.clearUsed();
        }

        return INVALID_SPAWN_ID;
    }

    public static @Nonnull List<ItemStack> getLoot(int id) {

        List<ItemStack> drops = new ArrayList<>();
        Cell cell = cells.get(id);
        if (cell != null && cell.isUsed() && cell.world != null) {
            List<EntityItem> entityItems = cell.world.getEntitiesWithinAABB(EntityItem.class, cell.axisAlignedBB, EntitySelectors.IS_ALIVE);
            for (EntityItem entityItem : entityItems) {
                drops.add(entityItem.getItem().copy());
                entityItem.setDead();
            }
        }
        return drops;
    }

    public static void spawnKill(int id) {

        Cell cell = cells.get(id);
        if (cell != null && cell.isUsed()) {
            SpawnManager.spawnKill(cell.fakeMobKey, cell.looting, cell.world, cell.getSpawnPos());
        }

    }

    private static class Cell {

        private BlockPos basePos;
        private BlockPos spawnPos;
        private boolean used  = false;
        private AxisAlignedBB axisAlignedBB;
        private FakeMobKey fakeMobKey;
        private int looting;
        private World world;

        private Cell() {}

        public Cell(BlockPos blockPos, BlockPos spawnPos) {
            this.basePos = blockPos;
            this.spawnPos = spawnPos;
        }

        public void setWorld(World world) {
            this.world = world;
        }

        public void setUsed(@Nonnull FakeMobKey fakeMobKey, int looting) {

            this.used = true;
            this.fakeMobKey = fakeMobKey;
            this.looting = looting;

            if (axisAlignedBB == null) {
                // Spawn box is internal 6x6x6
                axisAlignedBB = new AxisAlignedBB(spawnPos).grow(3);
            }
        }

        public void clearUsed() {

            this.used = false;
            this.axisAlignedBB = null;
            this.fakeMobKey = null;
            this.looting = 0;
        }

        public boolean isUsed() { return used; }
        public BlockPos getBasePos() { return this.basePos; }
        public BlockPos getSpawnPos() { return this.spawnPos; }
    }
}
