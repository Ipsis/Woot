package ipsis.woot.factory.blocks;

import ipsis.woot.factory.*;
import ipsis.woot.factory.layout.Layout;
import ipsis.woot.factory.multiblock.MultiBlockMaster;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.FakeMob;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * The factory is formed manually by the user via the intern -> interrupt
 * When an attached block is removed or unloaded it should inform the heart -> interrupt
 */
public class HeartTileEntity extends TileEntity implements ITickableTileEntity, MultiBlockMaster {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker LAYOUT = MarkerManager.getMarker("WOOT_LAYOUT");

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
        tickTracker.setStructureTickCount(20);
    }

    @Override
    public void remove() {
        super.remove();
        if (layout != null)
            layout.fullDisconnect();
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        if (layout != null)
            layout.fullDisconnect();
    }

    @Override
    public void tick() {
       if (world.isRemote)
           return;

       if (layout == null) {
           layout = new Layout();
           layout.setLocation(world,pos, world.getBlockState(pos).get(BlockStateProperties.HORIZONTAL_FACING));
           layout.setDirty();
       }

       // Check for tick acceleration
       if (!tickTracker.tick(world))
           return;

       layout.tick(tickTracker, this);
       if (layout.isFormed()) {
           if (layout.hasChanged()) {
               setup = new Setup(new FakeMob("minecraft:cow"), Tier.TIER_1);
               recipe = new Recipe(1000, 10);
               layout.clearChanged();
           }

           if (world.isBlockPowered(pos))
               return;

           tickRecipe();
           if (consumedUnits >= recipe.getNumUnits()) {
               // get and process the ingredients
               consumedUnits = 0;
           }
       }
    }

    /**
     * MultiBlockMaster
     */
    @Override
    public void interrupt() {
        LOGGER.info(LAYOUT, "interrupt layout:" + layout);
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
