package ipsis.woot.loot.schools;

import ipsis.Woot;
import ipsis.woot.command.ITextStatus;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ipsis.woot.dimension.WootDimensionManager.CHUNK_X;
import static ipsis.woot.dimension.WootDimensionManager.CHUNK_Z;

public class TartarusManager implements ITextStatus {

    public static final int INVALID_SPAWN_ID = -1;

    private HashMap<Integer, SpawnBox> spawnBoxMap = new HashMap<>();

    private void buildSpawnBox(World world, SpawnBox spawnBox) {

        Block b = Blocks.GLASS;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                for (int z = 0; z < 8; z++) {

                    if (y == 0 || y == 7) {
                        // top and bottom
                        BlockPos pos = new BlockPos(spawnBox.getBasePos().getX() + x, spawnBox.getBasePos().getY() + y, spawnBox.getBasePos().getZ() + z);
                        world.setBlockState(pos, b.getDefaultState());
                    } else if (x == 0 || x == 7 || z == 0 || z == 7) {
                        // sides
                        BlockPos pos = new BlockPos(spawnBox.getBasePos().getX() + x, spawnBox.getBasePos().getY() + y, spawnBox.getBasePos().getZ() + z);
                        world.setBlockState(pos, b.getDefaultState());
                    }
                }
            }
        }

//        world.setBlockState(spawnBox.getBasePos(), Blocks.GOLD_BLOCK.getDefaultState());
//        world.setBlockState(spawnBox.getSpawnPos(), Blocks.DIAMOND_BLOCK.getDefaultState());

    }

    /**
     * Single chunk
     * Each 16x16x16 split into 8x8x8
     */
    private void init() {

        BlockPos origin = new BlockPos(CHUNK_X * 16, 0, CHUNK_Z * 16);


        int[] xOffsets = { 0, 8, 0, 8 };
        int[] zOffsets = { 0, 0, 8, 8 };

        int spawnId = 0;
        for (int y = 0; y < 256; y += 8) {

            for (int i = 0; i < 4; i++) {
                BlockPos basePos = new BlockPos(origin.getX() + xOffsets[i], y, origin.getZ() + zOffsets[i]);
                BlockPos spawnPos = new BlockPos(basePos.getX() + 4, basePos.getY() + 4, basePos.getZ() + 4);
                spawnBoxMap.put(spawnId++, new SpawnBox(basePos, spawnPos));
            }
        }
    }

    public TartarusManager() {

        init();
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "TartatrusManager", spawnBoxMap.keySet());
    }

    public void build(World world) {

        for (SpawnBox spawnBox : spawnBoxMap.values())
            buildSpawnBox(world, spawnBox);
    }

    public int allocateSpawnBoxId() {

        for (int id : spawnBoxMap.keySet()) {
            if (!spawnBoxMap.get(id).isUsed()) {
                spawnBoxMap.get(id).setUsed();
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "allocateSpawnBoxId", "Id:" + id);
                return id;
            }
        }

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "allocateSpawnBoxId", "Id:INVALID");

        return INVALID_SPAWN_ID;
    }

    public int freeSpawnBoxId(int id) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "freeSpawnBoxId", "Id:" + id);

        if (spawnBoxMap.containsKey(id))
            spawnBoxMap.get(id).clearUsed();
        else
            LogHelper.error("freeSpawnBoxId: id " + id + " not allocated");

        return INVALID_SPAWN_ID;
    }

    public void spawnInBox(World world, int id, WootMobName wootMobName, EnumEnchantKey key) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "spawnInBox", "Id:" + id + " " + wootMobName + " " + key);

        Woot.wootDimensionManager.touchSpawnChunk(world);

        if (id == INVALID_SPAWN_ID)
            return;

        if (!spawnBoxMap.containsKey(id)) {
            LogHelper.error("spawnInBox: id " + id + " not allocated");
            return;
        }

        WorldServer spawnWorldServer = Woot.wootDimensionManager.getWorldServer(world);
        if (spawnWorldServer != null) {
            SpawnBox spawnBox = spawnBoxMap.get(id);
            Woot.entitySpawner.spawn(wootMobName, key, spawnWorldServer, spawnBox.getSpawnPos());
        }
    }

    public List<EntityItem> getLootInBox(World world, int id) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "getLootInBox", "Id:" + id);

        Woot.wootDimensionManager.touchSpawnChunk(world);

        List<EntityItem> itemList = new ArrayList<>();

        if (!spawnBoxMap.containsKey(id)) {
            LogHelper.error("getLootInBox: id " + id + " not allocated");
            return itemList;
        }

        SpawnBox spawnBox = spawnBoxMap.get(id);

        WorldServer spawnWorldServer = Woot.wootDimensionManager.getWorldServer(world);
        if (spawnWorldServer != null)
            itemList.addAll(spawnWorldServer.getEntitiesWithinAABB(EntityItem.class, spawnBox.getAxisAlignedBB(), EntitySelectors.IS_ALIVE));
        return itemList;
    }

    @Override
    public List<String> getStatus(WorldServer worldServer) {

        List<String> status = new ArrayList<>();
        boolean canUnload = true;
        for (Integer spawnId : spawnBoxMap.keySet()) {
            if (spawnBoxMap.get(spawnId).isUsed()) {
                status.add(String.format("%d: %s", spawnId, spawnBoxMap.get(spawnId)));
                canUnload = false;
            }
        }
        status.add("Can unload tartarus: " + canUnload);
        return status;
    }

    @Override
    public List<String> getStatus() {

        return new ArrayList<>();
    }
}
