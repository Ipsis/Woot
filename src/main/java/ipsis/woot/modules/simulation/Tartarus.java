package ipsis.woot.modules.simulation;

import ipsis.woot.Woot;
import ipsis.woot.modules.simulation.spawning.SpawnController;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Tartarus is the simulation world and consists of cells in one chunk extending from 0->255
 * Each cell is 8x8x8 with an internal airspace of 6x6x6
 * Standard world height is 256 block so that gives us 32 floors with 4 cells per floor so 128 cells
 */

public class Tartarus {

    static final int NUM_CELLS = 128;
    static final BlockPos origin = new BlockPos(0, 0, 0);

    public static Tartarus get() { return INSTANCE; }
    static Tartarus INSTANCE;
    static { INSTANCE = new Tartarus(); }

    Cell[] cells;
    private Tartarus() {
        cells = new Cell[NUM_CELLS];

        BlockPos[] offsets = {
                new BlockPos(0, 0, 0),
                new BlockPos(0, 0, 9),
                new BlockPos(9, 0, 9),
                new BlockPos(9, 0, 9),
        };
        for (int floor = 0; floor < (NUM_CELLS / 4); floor++) {
            for (int cell = 0; cell < 4; cell++) {
                cells[floor + cell] = new Cell(new BlockPos(origin.getX() + offsets[cell].getX(), (floor * 16) + offsets[cell].getY(), origin.getY() + offsets[cell].getY()));
            }
        }
    }

    public static final int INVALID_CELL = -1;
    public int allocateCell() {
        int allocatedCell = INVALID_CELL;
        for (int cell = 0; cell < NUM_CELLS && allocatedCell == INVALID_CELL; cell++) {
            if (!cells[cell].isOccupied()) {
                cells[cell].occupy();
                allocatedCell = cell;
            }
        }
        return allocatedCell;
    }

    public void vacateCell(int cellid) {
        if (cellid >= 0 && cellid < NUM_CELLS)
            cells[cellid].vacate();
    }

    public void simulateInCell(int cellid, @Nonnull FakeMobKey fakeMobKey, @Nonnull World world) {
        if (cellid >= 0 && cellid < NUM_CELLS && fakeMobKey.getMob().isValid()) {
            Woot.setup.getLogger().info("Spawning and killing {} {}", fakeMobKey, world.getDimension());
            SpawnController.get().spawnKill(fakeMobKey, world, cells[cellid].spawnPos);
        }
    }

    public @Nonnull List<ItemStack> sweepCell(int cellid, @Nonnull World world) {
        List<ItemStack> drops = new ArrayList<>();
        if (cellid >= 0 && cellid < NUM_CELLS) {
            List<ItemEntity> list = world.getEntitiesWithinAABB(ItemEntity.class, cells[cellid].bb, (x) -> x.isAlive() );
            if (!list.isEmpty()) {
                for (ItemEntity itemEntity : list) {
                    drops.add(itemEntity.getItem().copy());
                    itemEntity.lifespan = 0;
                }
            }
        }
        return drops;
    }

    class Cell {
        boolean occupied;
        BlockPos originPos; // Bottom left-hand block position of cell
        BlockPos spawnPos;
        AxisAlignedBB bb;

        private Cell() { }
        public Cell(BlockPos originPos) {
            occupied = false;
            this.originPos = originPos;
            // spawn in center of cell
            spawnPos = originPos.add(4, 4, 4);
        }

        public boolean isOccupied() { return this.occupied; }
        public void occupy() {
            occupied = true;
            if (bb == null)
                bb = new AxisAlignedBB(spawnPos).grow(3); // 6x6x6 cell
        }
        public void vacate() { occupied = false; }
    }
}
