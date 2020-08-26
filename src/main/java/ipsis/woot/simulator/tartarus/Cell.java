package ipsis.woot.simulator.tartarus;

import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Cell {

    private BlockPos origin; // Bottom left-hand block position of cell
    private BlockPos spawnPos;
    private AxisAlignedBB axisAlignedBB;
    private FakeMobKey fakeMobKey;

    public Cell(BlockPos origin) {
        this.origin = origin;
        spawnPos = origin.add(4, 4, 4);
        fakeMobKey = null;
        axisAlignedBB = null;
    }

    public boolean isOccupied() { return fakeMobKey != null; }
    public void free() { fakeMobKey = null; }
    public @Nullable FakeMobKey getOccupant() { return fakeMobKey; }

    public boolean setMob(@Nonnull FakeMobKey fakeMobKey) {
        if (isOccupied())
            return false;

        this.fakeMobKey = fakeMobKey;
        if (axisAlignedBB == null)
            axisAlignedBB = new AxisAlignedBB(spawnPos).grow(3); // 6x6x6 cell
        return true;
    }

    public void clean(@Nonnull World world) {
        /**
         * Remove everything from the cell.
         * This should catch any entity that spawns entities on death
         */
        for (LivingEntity livingEntity : world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB, x -> x.isAlive())) {
            livingEntity.remove();
        }
    }

    public @Nonnull List<ItemStack> sweep(@Nonnull World world) {
        List<ItemStack> drops = new ArrayList<>();
        if (isOccupied()) {
            for (ItemEntity itemEntity : world.getEntitiesWithinAABB(ItemEntity.class, axisAlignedBB, x -> x.isAlive())) {
                drops.add(itemEntity.getItem().copy());
                itemEntity.lifespan = 0;
            }
        }
        return drops;
    }

    public void run(@Nonnull World world) {
        if (isOccupied() && world instanceof ServerWorld)
            SpawnController.get().spawnKill(fakeMobKey, (ServerWorld)world, spawnPos);
    }

}
