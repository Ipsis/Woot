package ipsis.woot.modules.infuser.blocks;

import ipsis.woot.Woot;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.network.FluidStackPacket;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static ipsis.woot.crafting.InfuserRecipe.INFUSER_TYPE;

public class InfuserTileEntity extends TileEntity implements ITickableTileEntity, WootDebug, INamedContainerProvider {

    public InfuserTileEntity() {
        super(InfuserSetup.INFUSER_BLOCK_TILE.get());
    }

    @Override
    public void tick() {

        if (world.isRemote)
            return;

        machineTick();
    }

    public void setTankFluid(FluidStack fluidStack) {
        fluidTank.ifPresent(h -> h.setFluid(fluidStack));
    }

    public FluidStack getTankFluid() {
        return fluidTank.map(h -> h.getFluid()).orElse(FluidStack.EMPTY);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();
        else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return fluidTank.cast();
        else if (cap == CapabilityEnergy.ENERGY)
            return energyStorage.cast();
        return super.getCapability(cap, side);
    }

    public int getEnergy() {
        return energyStorage.map(h -> h.getEnergyStored()).orElse(0);
    }
    public void setEnergy(int v) { energyStorage.ifPresent(h -> h.setEnergy(v)); }

    /**
     * Tank
     */
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(InfuserConfiguration.INFUSER_TANK_CAPACITY.get(), h -> InfuserRecipe.isValidFluid(h));
    }

    /**
     * Energy
     */
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(InfuserConfiguration.INFUSER_MAX_ENERGY.get(), InfuserConfiguration.INFUSER_MAX_ENERGY_RX.get());
    }


    /**
     * Inventory
     */
    public static final int INPUT_SLOT = 0;
    public static final int AUGMENT_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private IItemHandler createItemHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == INPUT_SLOT)
                    return InfuserRecipe.isValidInput(stack);
                else if (slot == AUGMENT_SLOT)
                    return InfuserRecipe.isValidAugment(stack);

                return true;
            }
        };
    }

    public boolean handleFluidContainerUse(ItemStack heldItem, PlayerEntity playerEntity, Hand hand) {
        AtomicBoolean ret = new AtomicBoolean(false);
        fluidTank.ifPresent(h -> {
            FluidActionResult fillResult = FluidUtil.tryEmptyContainerAndStow(heldItem, h, null, Integer.MAX_VALUE, playerEntity, true);
            if (fillResult.isSuccess()) {
                playerEntity.setHeldItem(hand, fillResult.getResult());
                ret.set(true);
            }
        });
        return ret.get();
    }

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compoundNBT) {

        CompoundNBT invTag = compoundNBT.getCompound("inv");
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));

        CompoundNBT tankTag = compoundNBT.getCompound("tank");
        fluidTank.ifPresent(h -> h.readFromNBT(tankTag));

        CompoundNBT energyTag = compoundNBT.getCompound("energy");
        energyStorage.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(energyTag));

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        itemHandler.ifPresent(h -> {
            CompoundNBT invTag = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            compoundNBT.put("inv", invTag);
        });

        fluidTank.ifPresent(h -> {
            CompoundNBT tankTag = h.writeToNBT(new CompoundNBT());
            compoundNBT.put("tank", tankTag);
        });

        energyStorage.ifPresent(h -> {
            CompoundNBT energyTag = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
            compoundNBT.put("energy", energyTag);
        });

        return super.write(compoundNBT);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> InfuserTileEntity");
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
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new InfuserContainer(i, world, pos, playerInventory, playerEntity);
    }

    public FluidStackPacket getFluidStackPacket() {
        return new FluidStackPacket(fluidTank.map(f -> f.getFluid()).orElse(FluidStack.EMPTY));
    }

    /**
     * Process
     *
     * This is HEAVILY based off the CoFH's TileMachineBase
     */
    private boolean isActive = false;
    private int processMax = 0; // total energy needed
    private int processRem = 0; // energy still to use
    private InfuserRecipe currRecipe = null;
    public void machineTick() {

        if (isActive) {
            Woot.LOGGER.info("machineTick: running");
            processTick(); // use energy and update processRem
            if (canFinish()) {
                // all energy used and all inputs are still valid
                processFinish(); // use inputs and generate outputs
                if (isDisabled() || !canStart()) {
                    //  disabled via redstone or dont have a valid set of input items and enough energy
                    processOff();
                    isActive = false;
                    Woot.LOGGER.info("machineTick: turn off");
                } else {
                    processStart(); // set processMax and processRem
                }
            } else if (energyStorage.map(e -> e.getEnergyStored() <= 0).orElse(false)) {
                // No energy left so stop processing
                processOff();
            }
        } else if (!isDisabled()) {
            if (world.getGameTime() % 10 == 0 && canStart()) {
                // have a valid set of input items and enough energy
                processStart(); // set processMax and processRem
                processTick(); // use energy and update processRem
                isActive = true;
                Woot.LOGGER.info("machineTick: turn on");
            }
        }
    }

    /**
     *
     * If energy still needs to be consumed then do so
     * Returns the amount of energy used
     */
    private int processTick() {
        if (processRem <= 0)
            return 0;

        int energy = energyStorage.map(e -> e.extractEnergy(200, false)).orElse(0);
        processRem -= energy;
        return energy;
    }

    /**
     * Can finish if all the energy has been consumed and the inputs still give a valid recipe
     */
    private boolean canFinish() {
        return processRem <= 0 && hasValidInput();
    }

    /**
     * Consume all the inputs and generate the output
     */
    private void processFinish() {
        if (currRecipe == null)
            getRecipe();
        if (currRecipe == null) {
            processOff();
            return;
        }

        final int inputSize = currRecipe.getIngredient().getMatchingStacks()[0].getCount();
        final int augmentSize = currRecipe.hasAugment() ? currRecipe.getAugment().getMatchingStacks()[0].getCount() : 1;

        itemHandler.ifPresent(i -> {
            i.extractItem(INPUT_SLOT, inputSize, false);
            if (currRecipe.hasAugment())
                i.extractItem(AUGMENT_SLOT, augmentSize, false);
            i.insertItem(OUTPUT_SLOT, currRecipe.getOutput(), false);
        });

        fluidTank.ifPresent(f -> f.drain(currRecipe.getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE));
        markDirty();
    }

    /**
     * Check that we have inputs and they represent a valid recipe
     */
    private boolean canStart() {
        if (itemHandler.map(h -> h.getStackInSlot(INPUT_SLOT).isEmpty()).orElse(true)) {
            return false;
        }

        if (fluidTank.map(f -> f.isEmpty()).orElse(true)) {
            return false;
        }

        getRecipe();
        if (currRecipe == null) {
            return false;
        }

        return true;
    }

    private void processOff() {
        processRem = 0;
        currRecipe = null;
    }

    /**
     * Setup the required energy for the recipe
     */
    private void processStart() {
        processMax = currRecipe.getEnergy();
        processRem = currRecipe.getEnergy();
    }

    /**
     * Redstone disabling of the machine
     */
    private boolean isDisabled() {
        return false;
    }

    private void getRecipe() {

        if (fluidTank.map(h -> h.isEmpty()).orElse(false)) {
            currRecipe = null;
            return;
        }

        List<InfuserRecipe> recipes = world.getRecipeManager().getRecipes(
                INFUSER_TYPE,
                new Inventory(
                        itemHandler.map(i -> i.getStackInSlot(INPUT_SLOT)).orElse(ItemStack.EMPTY),
                        itemHandler.map(i -> i.getStackInSlot(AUGMENT_SLOT)).orElse(ItemStack.EMPTY)),
                world);

        if (!recipes.isEmpty()) {
            // Already checked for empty so this should always be !empty
            FluidStack fluidStack = fluidTank.map(h ->h.getFluid()).orElse(FluidStack.EMPTY);
            for (InfuserRecipe r : recipes) {
                if (r.getFluidInput().isFluidEqual(fluidStack)) {
                    currRecipe = r;
                    return;
                }
            }
        }

        currRecipe = null;
    }

    private boolean hasValidInput() {
        if (currRecipe == null)
            getRecipe();
        if (currRecipe == null)
            return false;

        return true;
    }

    private int clientFluidAmount = 0;
    public int getClientFluidAmount() { return clientFluidAmount; }
    public void setClientFluidAmount(int v) { clientFluidAmount = v; }




}
