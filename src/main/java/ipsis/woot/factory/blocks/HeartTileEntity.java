package ipsis.woot.factory.blocks;

import ipsis.woot.factory.*;
import ipsis.woot.factory.generators.LootGeneration;
import ipsis.woot.factory.layout.Layout;
import ipsis.woot.factory.multiblock.MultiBlockMaster;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.List;

/**
 * The factory is formed manually by the user via the intern -> interrupt
 * When an attached block is removed or unloaded it should inform the heart -> interrupt
 */
public class HeartTileEntity extends TileEntity implements ITickableTileEntity, MultiBlockMaster, WootDebug {

    static final Logger LOGGER = LogManager.getLogger();
    static final Marker LAYOUT = MarkerManager.getMarker("WOOT_LAYOUT");

    /**
     * Layout will not exist until after the first update call
     * Setup will only exist if the layout is formed
     */
    Layout layout;
    Setup setup;
    Recipe recipe;
    TickTracker tickTracker = new TickTracker();

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
               setup = Setup.creatFromLayout(world, layout);
               recipe = RecipeHelper.createRecipe(setup, world);
               layout.clearChanged();
           }

           if (world.isBlockPowered(pos))
               return;

           tickRecipe();
           if (consumedUnits >= recipe.getNumUnits()) {
               // get and process the ingredients
               consumedUnits = 0;

               LootGeneration.get().generate(this, setup);
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
    int consumedUnits = 0;
    void tickRecipe() {
        consumedUnits += recipe.getUnitsPerTick();
    }

    /**
     * Tick Tracker
     */
    public class TickTracker {
        long lastGameTime = -1;
        int structureTicksTimeout = 0;
        int currStructureTicks = 0;

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

    /**
     * Currently running recipe
     */
    public static class Recipe {

        int numTicks;
        int numUnits;

        public int getNumTicks() {
            return numTicks;
        }

        public int getNumUnits() {
            return numUnits;
        }

        public Recipe() {
            numTicks = 1;
            numUnits = 1;
        }

        public Recipe(int numTicks, int numUnits) {
            this.numTicks = MathHelper.clamp(numTicks, 1, Integer.MAX_VALUE);
            this.numUnits = MathHelper.clamp(numUnits, 1, Integer.MAX_VALUE);
        }

        public int getUnitsPerTick() {
            return MathHelper.clamp(numUnits / numTicks, 1, Integer.MAX_VALUE);
        }

        @Override
        public String toString() {
            return "u:" + numUnits + "/t:" + numTicks;
        }
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> HeartTileEntity");
        debug.add("      layout: " + layout);
        debug.add("      setup: " + setup);
        debug.add("      recipe: " + recipe);
        return debug;
    }
}
