package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.calculators.CalculatorVersion1;
import ipsis.woot.modules.factory.generators.LootGeneration;
import ipsis.woot.modules.factory.layout.Layout;
import ipsis.woot.modules.factory.multiblock.MultiBlockMaster;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.WootDebug;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The factory is formed manually by the user via the intern -> interrupt
 * When an attached block is removed or unloaded it should inform the heart -> interrupt
 */
public class HeartTileEntity extends TileEntity implements ITickableTileEntity, MultiBlockMaster, WootDebug, INamedContainerProvider {

    static final Logger LOGGER = LogManager.getLogger();

    /**
     * Layout will not exist until after the first update call
     * Setup will only exist if the layout is formed
     */
    Layout layout;
    Setup setup;
    Recipe recipe;
    TickTracker tickTracker = new TickTracker();
    boolean loadedFromNBT = false;

    public HeartTileEntity() {
        super(FactorySetup.HEART_BLOCK_TILE.get());
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

    public boolean isFormed() { return layout != null && layout.isFormed(); }

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
               setup = Setup.createFromLayout(world, layout);

               setup.getMobs().forEach(m -> MobSimulator.getInstance().learn(m));
               recipe = CalculatorVersion1.calculate(setup, world);

               // Restore the progress on load
               if (loadedFromNBT) {
                   loadedFromNBT = false;
               } else {
                   consumedUnits = 0;
                   markDirty();
               }

               layout.clearChanged();
           }

           if (world.isBlockPowered(pos))
               return;

           tickRecipe();
           if (consumedUnits >= recipe.getNumTicks()) {
               // get and process the ingredients
               consumedUnits = 0;
               markDirty();

               TileEntity te = world.getTileEntity(setup.getCellPos());
               if (te instanceof CellTileEntityBase) {
                   LazyOptional<IFluidHandler> hdlr = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
                   if (hdlr.isPresent()){
                       IFluidHandler iFluidHandler = hdlr.orElseThrow(NullPointerException::new);
                       FluidStack fluidStack = iFluidHandler.drain(recipe.getNumUnits(), IFluidHandler.FluidAction.SIMULATE);
                       if (fluidStack.getAmount() == recipe.getNumUnits()) {
                           LOGGER.debug("Generate loot");
                           iFluidHandler.drain(recipe.getNumUnits(), IFluidHandler.FluidAction.EXECUTE);
                           LootGeneration.get().generate(this, setup);
                       }
                   }
               }
           }
       }
    }

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("progress")) {
            loadedFromNBT = true;
            consumedUnits = compound.getInt("progress");
            LOGGER.debug("read: loading progress " + consumedUnits);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (isFormed() && recipe != null) {
            compound.putInt("progress", consumedUnits);
            LOGGER.debug("write: saving progress " + consumedUnits);
        }
        return compound;
    }

    /**
     * MultiBlockMaster
     */
    @Override
    public void interrupt() {
        LOGGER.debug("interrupt layout:" + layout);
        if (layout != null)
            layout.setDirty();
    }

    /**
     * Recipe handling
     */
    int consumedUnits = 0;
    void tickRecipe() {
        // Purely the passage of time
        consumedUnits++;
        markDirty();
    }

    /**
     * Tick Tracker
     */
    public class TickTracker {
        long lastGameTime = -1;
        int structureTicksTimeout = 0;
        int currStructureTicks = 0;

        public boolean tick(World world) {

            boolean realTick = false;
            long currGameTime = world.getGameTime();
            if (FactoryConfiguration.TICK_ACCEL.get() == true || lastGameTime != currGameTime) {
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
        debug.add("      consumed: " + consumedUnits);
        return debug;
    }

    /**
     * INamedContainerProvider
     */
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new HeartContainer(windowId, world, pos, playerInventory, playerEntity);
    }

    /**
     * Client sync data
     */
    public int getClientProgress() { return consumedUnits; }
    public void setClientProgress(int clientProgress) { this.consumedUnits = clientProgress; }

    /**
     * Used by the container tracker for the server value
     */
    public int getFluidAmount() {
        if (setup != null) {
            TileEntity te = world.getTileEntity(setup.getCellPos());
            if (te instanceof CellTileEntityBase) {
                return ((CellTileEntityBase) te).tank.getFluidAmount();
            }
        }
        return 0;
    }
    public int getFluidCapacity() {
        if (setup != null) {
            TileEntity te = world.getTileEntity(setup.getCellPos());
            if (te instanceof CellTileEntityBase) {
                return ((CellTileEntityBase) te).tank.getCapacity();
            }
        }
        return 0;
    }

    /**
     * Used by the container tracker for the client value
     */
    private int clientFluidAmount = -1;
    private int clientFluidCapacity = -1;
    public int getClientFluidAmount() { return clientFluidAmount; }
    public void setClientFluidAmount(int v) {
        /**
         * Cannot set the client tank as it is in the cell and we don't have the location
         * We use a local tracker for the client value only
         */
        clientFluidAmount = v;
    }
    public int getClientFluidCapacity() { return clientFluidCapacity; }
    public void setClientFluidCapacity(int v) {
        /**
         * Cannot set the client tank as it is in the cell and we don't have the location
         * We use a local tracker for the client value only
         */
        clientFluidCapacity = v;
    }

    public FactoryUIInfo createFactoryUIInfo() {
        return FactoryUIInfo.create(setup, recipe);
    }

    @OnlyIn(Dist.CLIENT)
    public FactoryUIInfo factoryUIInfo;

    @OnlyIn(Dist.CLIENT)
    public void setFromUIInfo(FactoryUIInfo factoryUIInfo) {
        this.factoryUIInfo = factoryUIInfo;
    }
}
