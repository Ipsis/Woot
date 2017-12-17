package ipsis.woot.loot.schools;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class DimensionSchoolManager {

    private static final int SIZE = 16;


    private class SpawnBox {

        // origin is the lower left x,z coordinate
        private BlockPos origin;
        private World world;
        private boolean allocated = false;
        public boolean isAllocated() { return allocated; }
        public void setAllocated() { allocated = true; }
        public void clearAllocated() { allocated = false; }

        public SpawnBox(BlockPos origin, World world) {
            this.origin = origin;
            this.world = world;
        }

        public BlockPos getSpawnPos() {

            int offset = SIZE / 2;
            return new BlockPos(origin.getX() + offset, origin.getY() + offset, origin.getZ() + offset);
        }

        public void buildSpawnBox() {

            Block b = Blocks.COBBLESTONE;

            for (int yOffset = 0; yOffset < SIZE; yOffset++) {
                for (int xOffset = 0; xOffset < SIZE; xOffset++) {
                    for (int zOffset = 0; zOffset < SIZE; zOffset++) {
                        BlockPos blockPos = new BlockPos(origin.getX() + xOffset, origin.getY() + yOffset, origin.getZ() + zOffset);
                        if (yOffset == 0 || yOffset == SIZE - 1) {
                            world.setBlockState(blockPos, b.getDefaultState(), 3);
                        } else if (xOffset == 0 || xOffset == SIZE - 1) {
                            world.setBlockState(blockPos, b.getDefaultState(), 3);
                        } else if (zOffset == 0 || zOffset == SIZE - 1) {
                            world.setBlockState(blockPos, b.getDefaultState(), 3);

                        }
                    }
                }
            }

        }
    }


    private HashMap<Integer, SpawnBox> spawnBoxMap;
    public DimensionSchoolManager() {
        spawnBoxMap = new HashMap<>();
    }

    public void build(World world, int chunkX, int chunkZ) {

        int x = chunkX * 16;
        int z = chunkZ * 16;
        int id = 0;
        for (int y = 0; y < 256; y += 16) {

            SpawnBox box = new SpawnBox(new BlockPos(x, y, z), world);
            spawnBoxMap.put(id, box);
            id++;
        }
    }
}
