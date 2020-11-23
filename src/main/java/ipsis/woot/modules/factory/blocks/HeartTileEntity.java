package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.mod.ModNBT;
import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.network.HeartStaticDataReply2;
import ipsis.woot.modules.factory.recipe.Calculator;
import ipsis.woot.modules.factory.client.ClientFactorySetup;
import ipsis.woot.modules.factory.generators.LootGeneration;
import ipsis.woot.modules.factory.layout.Layout;
import ipsis.woot.modules.factory.multiblock.MultiBlockMaster;
import ipsis.woot.modules.factory.network.HeartStaticDataReply;
import ipsis.woot.modules.factory.recipe.HeartRecipe;
import ipsis.woot.modules.factory.recipe.HeartSummary;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.StorageHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    FormedSetup formedSetup;
    HeartRecipe recipe;
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
        if (world == null)
            return;

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
               formedSetup = FormedSetup.createFromValidLayout(world, layout);
               LOGGER.debug("formedSetup: {}", formedSetup);

               formedSetup.getAllMobs().forEach(m -> MobSimulator.getInstance().learn(m));
               recipe = Calculator.createRecipe(formedSetup);

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

               if (recipe.canFindIngredients(formedSetup)) {
                   LazyOptional<IFluidHandler> hdlr = formedSetup.getCellFluidHandler();
                   hdlr.ifPresent(h -> {
                       FluidStack fluidStack = h.drain(recipe.getNumUnits(), IFluidHandler.FluidAction.EXECUTE);
                       if (fluidStack.getAmount() == recipe.getNumUnits()) {
                           LOGGER.debug("Generate loot");
                           consumeIngredients(recipe, formedSetup);
                           h.drain(recipe.getNumUnits(), IFluidHandler.FluidAction.EXECUTE);
                           LootGeneration.get().generate(this, formedSetup);
                       }

                   });
               }
           }
       }
    }

    private void consumeIngredients(HeartRecipe recipe, FormedSetup formedSetup) {
        if (!recipe.hasFluidIngredients() && !recipe.hasItemIngredients())
            return;

        /**
         * This is only called when we are sure that correct amount of ingredients
         * are already present.
         */

        List<LazyOptional<IItemHandler>> itemImportHandlers = formedSetup.getImportItemHandlers();
        List<LazyOptional<IFluidHandler>> fluidImportHandlers = formedSetup.getImportFluidHandlers();

        for (HeartRecipe.Ingredient i : recipe.getIngredients()) {
            if (i instanceof HeartRecipe.ItemIngredient) {
                HeartRecipe.ItemIngredient itemIngredient = (HeartRecipe.ItemIngredient)i;
                consumeItemIngredient(itemIngredient.getItemStack().copy(), itemIngredient.getAmount(), itemImportHandlers);
            } else if (i instanceof HeartRecipe.FluidIngredient) {
                HeartRecipe.FluidIngredient fluidIngredient = (HeartRecipe.FluidIngredient)i;
                consumeFluidIngredient(fluidIngredient.getFluidStack().copy(), fluidIngredient.getAmount(), fluidImportHandlers);
            }
        }
    }

    private void consumeItemIngredient(ItemStack itemStack, int amount, List<LazyOptional<IItemHandler>> itemImportHandlers) {

        Woot.setup.getLogger().debug("consumeItemIngredient: to consume {}*{}", amount, itemStack);

        AtomicInteger left = new AtomicInteger(amount);
        for (LazyOptional<IItemHandler> hdlr : itemImportHandlers) {
            if (left.get() == 0)
                break;

            hdlr.ifPresent(h -> {
                    for (int slot = 0; slot < h.getSlots(); slot++) {
                        ItemStack slotStack = h.getStackInSlot(slot);
                        if (!slotStack.isEmpty() && ItemStack.areItemsEqual(itemStack, slotStack)) {

                            int consumed;
                            if (left.get() > slotStack.getCount())
                                consumed = slotStack.getCount();
                            else
                                consumed = left.get();

                            Woot.setup.getLogger().debug("consumeItemIngredients: consumed {}", consumed);
                            slotStack.shrink(consumed);
                            left.set(left.get() - consumed);
                        }
                    }
            });
        }
    }

    private void consumeFluidIngredient(FluidStack fluidStack, int amount, List<LazyOptional<IFluidHandler>> fluidImportHandlers) {

        Woot.setup.getLogger().debug("consumeFluidIngredient: to consume {}*{}", amount, fluidStack);

        AtomicInteger left = new AtomicInteger(amount);
        for (LazyOptional<IFluidHandler> hdlr : formedSetup.getImportFluidHandlers()) {
            if (left.get() == 0)
                break;

            hdlr.ifPresent(h -> {
                FluidStack toDrain = new FluidStack(fluidStack, left.get());
                FluidStack drainedStack = h.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
                Woot.setup.getLogger().debug("consumeFluidIngredient: consumed {}", drainedStack.getAmount());
                left.set(left.get() - drainedStack.getAmount());
            });
        }
    }

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains(ModNBT.Heart.PROGRESS_TAG)) {
            loadedFromNBT = true;
            consumedUnits = compound.getInt(ModNBT.Heart.PROGRESS_TAG);
            LOGGER.debug("read: loading progress " + consumedUnits);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (isFormed() && recipe != null) {
            compound.putInt(ModNBT.Heart.PROGRESS_TAG, consumedUnits);
            LOGGER.debug("write: saving progress " + consumedUnits);
        }
        return compound;
    }

    /**
     * MultiBlockMaster
     */
    @Override
    public void interrupt() {
        //LOGGER.debug("interrupt layout:" + layout);
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
            if (FactoryConfiguration.TICK_ACCEL.get() || lastGameTime != currGameTime) {
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
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> HeartTileEntity");
        debug.add("      layout: " + layout);
        debug.add("      setup: " + formedSetup);
        debug.add("      recipe: " + recipe);
        debug.add("      consumed: " + consumedUnits);
        return debug;
    }

    /**
     * INamedContainerProvider
     */
    @Override
    public ITextComponent getDisplayName() {
        if (isFormed())
            return StringHelper.translate(formedSetup.getTier().getTranslationKey());

        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new HeartContainer(windowId, world, pos, playerInventory, playerEntity);
    }

    public int getFluidCapacity() {
        return formedSetup != null ? formedSetup.getCellCapacity() : 0;
    }
    public FluidStack getTankFluid() {
        return formedSetup != null ?
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), formedSetup.getCellFluidAmount()) :
                FluidStack.EMPTY;
    }

    public int getProgress() {
        if (formedSetup == null)
            return 0;

        return (int)((100.0F / recipe.getNumTicks() * consumedUnits));
    }

    public List<FakeMob> getFormedMobs() {
        List<FakeMob> mobs = new ArrayList<>();
        if (isFormed())
            mobs.addAll(formedSetup.getAllMobs());
        return mobs;
    }

    public Map<PerkType, Integer> getPerks() {
        Map<PerkType, Integer> perks = new HashMap<>();
        if (isFormed())
            perks.putAll(formedSetup.getAllPerks());
        return perks;
    }

    public Tier getTier() {
        return isFormed() ? formedSetup.getTier() : Tier.UNKNOWN;
    }

    public Exotic getExotic() {
        return isFormed() ? formedSetup.getExotic() : Exotic.NONE;
    }

    @OnlyIn(Dist.CLIENT)
    public ClientFactorySetup clientFactorySetup;

    @OnlyIn(Dist.CLIENT)
    public void setClientFactorySetup(ClientFactorySetup clientFactorySetup) {
        this.clientFactorySetup = clientFactorySetup;
    }

    @OnlyIn(Dist.CLIENT)
    public HeartSummary clientHeartSummary;

    @OnlyIn(Dist.CLIENT)
    public void setClientHeartSummary(HeartSummary heartSummary) {
        this.clientHeartSummary = heartSummary;
    }

    public HeartStaticDataReply2 createStaticDataReply2() {
        return new HeartStaticDataReply2(new HeartSummary(formedSetup, recipe));
    }
}
