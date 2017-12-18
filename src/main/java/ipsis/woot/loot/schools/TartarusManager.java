package ipsis.woot.loot.schools;

import ipsis.woot.oss.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class TartarusManager {

    public static final int CHUNK_X = 0;
    public static final int CHUNK_Z = 0;

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

            spawnBoxMap.put(spawnId++, new SpawnBox(new BlockPos(origin.getX() + 0, y, origin.getZ() + 0)));
            spawnBoxMap.put(spawnId++, new SpawnBox(new BlockPos(origin.getX() + 8, y, origin.getZ() + 0)));
            spawnBoxMap.put(spawnId++, new SpawnBox(new BlockPos(origin.getX() + 0, y, origin.getZ() + 8)));
            spawnBoxMap.put(spawnId++, new SpawnBox(new BlockPos(origin.getX() + 8, y, origin.getZ() + 8)));
        }
    }

    public TartarusManager() {

        init();
        for (int id : spawnBoxMap.keySet())
            LogHelper.info("TartarusManager: Id=" +id + " " + spawnBoxMap.get(id));
    }

    public void build(World world) {

        for (SpawnBox spawnBox : spawnBoxMap.values())
            buildSpawnBox(world, spawnBox);

    }
}
