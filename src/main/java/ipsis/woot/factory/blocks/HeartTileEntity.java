package ipsis.woot.factory.blocks;

import ipsis.woot.factory.*;
import ipsis.woot.factory.multiblock.MultiBlockMaster;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.simulation.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HeartTileEntity extends TileEntity implements ITickableTileEntity, MultiBlockMaster {

    /**
     * Layout will not exist until after the first update call
     * Setup will only exist if the layout is formed
     */
    private Layout layout;
    private Setup setup;
    private Recipe recipe;
    private TickTracker tickTracker = new TickTracker();

    public HeartTileEntity() {
        super(ModBlocks.HEART_BLOCK_TILE);
    }

    @Override
    public void tick() {
       if (world.isRemote)
           return;

       if (layout == null) {
           layout = new Layout();
           recipe = new Recipe(1000, 2000);
           FakeMobKey fakeMobKey = new FakeMobKey(new FakeMob("minecraft:zombie"), 1);
           setup = new Setup(fakeMobKey.getMob(), Tier.TIER_1);
           MobSimulator.get().learn(fakeMobKey);
       }

       // Check for tick acceleration
       if (!tickTracker.tick(world))
           return;

       layout.tick(tickTracker, this);
       if (layout.isFormed()) {
           if (layout.hasChanged()) {
               layout.clearChanged();
           }
       }

       if (world.isBlockPowered(pos))
           return;


       tickRecipe();
       if (consumedUnits >= recipe.getNumUnits()) {
           // get and process the ingredients
           consumedUnits = 0;
       }
    }

    /**
     * MultiBlockMaster
     */
    @Override
    public void interrupt() {
        if (layout != null)
            layout.setDirty();
    }

    /**
     * Recipe handling
     */
    private int consumedUnits = 0;
    private void tickRecipe() {
        consumedUnits += recipe.getUnitsPerTick();
    }

    /**
     * Tick Tracker
     */
    public class TickTracker {
        private long lastGameTime = -1;
        private int structureTicksTimeout = 0;
        private int currStructureTicks = 0;

        public boolean tick(World world) {

            // @todo config for tick acceleration

            boolean realTick = false;
            long currGameTime = world.getGameTime();
            if (lastGameTime != currGameTime) {
                // actual time has passed - no acceleration
                lastGameTime = currGameTime;
                realTick = true;

                if (structureTicksTimeout > 0)
                    currStructureTicks++;
            }

            return realTick;
        }

        public boolean hasStructureTickExpired() {
            return structureTicksTimeout > 0 && currStructureTicks >= structureTicksTimeout;
        }

        public void setStructureTickCount(int ticks) {
            structureTicksTimeout = ticks;
        }

        public void resetStructureTickCount() {
            currStructureTicks = 0;
        }
    }
}
