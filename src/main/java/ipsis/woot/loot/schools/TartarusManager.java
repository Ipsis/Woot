package ipsis.woot.loot.schools;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TartarusManager {

    public static final int CHUNK_X = 0;
    public static final int CHUNK_Z = 0;
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

    }

    /**
     * Single chunk
     * Each 16x16x16 split into 8x8x8
     */
    private void init() {

        BlockPos origin = new BlockPos(CHUNK_X * 16, 0, CHUNK_Z * 16);

        int spawnId = 0;
        for (int y = 0; y < 256; y += 8) {

            spawnBoxMap.put(spawnId++, new SpawnBox(
                    new BlockPos(origin.getX() + 0, y, origin.getZ() + 0),
                    new BlockPos(origin.getX() + 0 + 4, y + 4, origin.getZ() + 0 + 4)));
            spawnBoxMap.put(spawnId++,
                    new SpawnBox(new BlockPos(origin.getX() + 8, y, origin.getZ() + 0),
                    new BlockPos(origin.getX() + 8 + 4, y + 4, origin.getZ() + 0 + 4)));
            spawnBoxMap.put(spawnId++, new SpawnBox(
                    new BlockPos(origin.getX() + 0, y, origin.getZ() + 0),
                    new BlockPos(origin.getX() + 0 + 4, y + 4, origin.getZ() + 0 + 4)));
            spawnBoxMap.put(spawnId++, new SpawnBox(
                    new BlockPos(origin.getX() + 8, y, origin.getZ() + 8),
                    new BlockPos(origin.getX() + 8 + 4, y + 4, origin.getZ() + 8 + 4)));
        }
    }

    public TartarusManager() {

        init();
        for (int id : spawnBoxMap.keySet())
            LogHelper.info("TartarusManager: Id=" + id + " " + spawnBoxMap.get(id));
    }

    public void build(World world) {

        for (SpawnBox spawnBox : spawnBoxMap.values())
            buildSpawnBox(world, spawnBox);
    }

    public int allocateSpawnBoxId() {

        for (int id : spawnBoxMap.keySet()) {
            if (!spawnBoxMap.get(id).isUsed()) {
                spawnBoxMap.get(id).setUsed();
                return id;
            }
        }

        return INVALID_SPAWN_ID;
    }

    public int freeSpawnBoxId(int id) {

        if (spawnBoxMap.containsKey(id))
            spawnBoxMap.get(id).clearUsed();
        else
            LogHelper.error("freeSpawnBoxId: id " + id + " not allocated");

        return INVALID_SPAWN_ID;
    }

    public void spawnInBox(World world, int id, WootMobName wootMobName, EnumEnchantKey key) {

        Woot.wootDimensionManager.touchSpawnChunk(world);

        if (id == INVALID_SPAWN_ID)
            return;

        if (!spawnBoxMap.containsKey(id)) {
            LogHelper.error("spawnInBox: id " + id + " not allocated");
            return;
        }

        SpawnBox spawnBox = spawnBoxMap.get(id);
        Woot.entitySpawner.spawn(wootMobName, key, world, spawnBox.getSpawnPos());
    }

    private AxisAlignedBB axisAlignedBB;
    public List<EntityItem> getLootInBox(World world, int id) {

        Woot.wootDimensionManager.touchSpawnChunk(world);

       List<EntityItem> itemList = new ArrayList<>();

        if (!spawnBoxMap.containsKey(id)) {
            LogHelper.error("getLootInBox: id " + id + " not allocated");
            return itemList;
        }

        SpawnBox spawnBox = spawnBoxMap.get(id);

        if (axisAlignedBB == null) {
            int range = 6;
            axisAlignedBB = new AxisAlignedBB(spawnBox.getSpawnPos()).grow(range, 0, range);
        }

        itemList.addAll(world.getEntitiesWithinAABB(EntityItem.class, axisAlignedBB, EntitySelectors.IS_ALIVE));
        return itemList;
    }
}
