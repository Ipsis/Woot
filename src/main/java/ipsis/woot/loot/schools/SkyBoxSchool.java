package ipsis.woot.loot.schools;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.*;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.ITickTracker;
import ipsis.woot.loot.ILootLearner;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.List;

public class SkyBoxSchool implements ILootSchool {

    private boolean hasSkyBox = false;
    private AxisAlignedBB axisAlignedBB;

    /**
     * Creates a 3x3x3 box in the sky to capture the drops while learning
     */
    private void createSkybox(World world, BlockPos factoryPos) {

//       Block b = Blocks.COBBLESTONE;
       Block b = Blocks.BARRIER;

       int worldHeight = world.getHeight();
       int yMin = worldHeight - 5;
       int yMax = worldHeight - 1;

       for (int y = yMin; y <= yMax; y++) {
           for (int x = -2; x <= 2; x++) {
               for (int z = -2; z <= 2; z++) {

                   boolean change = false;
                   if (y == yMin || y == yMax || x == -2 || x == 2 || z == -2 || z == 2)
                       change = true;

                   if (change) {
                       BlockPos p = new BlockPos(factoryPos.getX() + x, y, factoryPos.getZ() + z);
                       if (world.getBlockState(p).getBlock() != b)
                           world.setBlockState(p, b.getDefaultState(), 3);
                   }
               }
           }
       }

       hasSkyBox = true;
    }

    private void destroySkyBox(World world, BlockPos factoryPos) {

        int worldHeight = world.getHeight();
        int yMin = worldHeight - 5;
        int yMax = worldHeight - 1;

        for (int y = yMax; y >= yMin; y--) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos p = new BlockPos(factoryPos.getX() + x, y, factoryPos.getZ() + z);
                    if (world.getBlockState(p).getBlock() != Blocks.AIR)
                        world.setBlockToAir(p);
                }
            }
        }

        hasSkyBox = false;
    }

    private void checkSkybox(World world, BlockPos origin, IFarmSetup farmSetup) {

        WootMobName wootMobName = farmSetup.getWootMobName();
        EnumEnchantKey key = farmSetup.getEnchantKey();

        if (!Woot.lootRepository.isFull(wootMobName, key)) {

            if (axisAlignedBB == null) {
                BlockPos spawnPos = new BlockPos(origin.getX(), world.getHeight() - 3, origin.getZ());
                int range = 2;
                axisAlignedBB = new AxisAlignedBB(spawnPos).grow(range, 0, range);
            }

            List<EntityItem> items = world.getEntitiesWithinAABB(
                    EntityItem.class, axisAlignedBB, EntitySelectors.IS_ALIVE);

            if (!items.isEmpty()) {
                Woot.lootRepository.learn(wootMobName, key, items, false);
                for (EntityItem i : items)
                    i.setDead();
            }
        }
    }

    /**
     * ILootLearner
     */

    public void tick(World world, BlockPos origin, IFarmSetup farmSetup) {

        Woot.wootDimensionManager.touchSpawnChunk(world);

        if (hasSkyBox)
            checkSkybox(world, origin, farmSetup);

        WootMobName wootMobName = farmSetup.getWootMobName();
        EnumEnchantKey key = farmSetup.getEnchantKey();
        if (!Woot.lootRepository.isFull(wootMobName, key)) {

            if (!hasSkyBox)
                createSkybox(world, origin);

            // Spawn in middle of the sky box
            BlockPos spawnPos = new BlockPos(origin.getX(), world.getHeight() - 3, origin.getZ());
            Woot.entitySpawner.spawn(wootMobName, key, world, spawnPos);

        } else if (hasSkyBox) {

            // Don't need it any more
            destroySkyBox(world, origin);
        }

    }
}
