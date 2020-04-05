package ipsis.woot.modules.infuser.blocks;

import ipsis.woot.Woot;
import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.network.FluidStackPacket;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.util.EnchantingHelper;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import ipsis.woot.util.WootMachineTileEntity;
import ipsis.woot.util.helper.WorldHelper;
import ipsis.woot.util.oss.OutputOnlyItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static ipsis.woot.crafting.InfuserRecipe.INFUSER_TYPE;

public class InfuserTileEntity extends WootMachineTileEntity implements WootDebug, INamedContainerProvider {

    public InfuserTileEntity() {
        super(InfuserSetup.INFUSER_BLOCK_TILE.get());
        inputSlots = new ItemStackHandler(2) {
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
                return false;
            }
        };
        outputSlot = new ItemStackHandler();
        outputWrappedSlot= new OutputOnlyItemStackHandler(outputSlot);
    }

    //-------------------------------------------------------------------------
    //region Tanks
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(InfuserConfiguration.INFUSER_TANK_CAPACITY.get(), h -> InfuserRecipe.isValidFluid(h));
    }

    public void setTankFluid(FluidStack fluidStack) {
        fluidTank.ifPresent(h -> h.setFluid(fluidStack));
    }

    public FluidStack getTankFluid() {
        return fluidTank.map(h -> h.getFluid()).orElse(FluidStack.EMPTY);
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Energy
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(InfuserConfiguration.INFUSER_MAX_ENERGY.get(), InfuserConfiguration.INFUSER_MAX_ENERGY_RX.get());
    }

    public int getEnergy() {
        return energyStorage.map(h -> h.getEnergyStored()).orElse(0);
    }
    public void setEnergy(int v) { energyStorage.ifPresent(h -> h.setEnergy(v)); }
    //endregion

    //-------------------------------------------------------------------------
    //region Inventory
    public static final int INPUT_SLOT = 0;
    public static final int AUGMENT_SLOT = 1;
    public static final int OUTPUT_SLOT = 0; // two different inventories, one for input, one for output
    private ItemStackHandler inputSlots;
    private ItemStackHandler outputSlot;
    private ItemStackHandler outputWrappedSlot;
    private final LazyOptional<IItemHandler> inputSlotHandler = LazyOptional.of(() -> inputSlots);
    private final LazyOptional<IItemHandler> outputWrappedSlotHandler = LazyOptional.of(() -> outputWrappedSlot);
    private final LazyOptional<IItemHandler> allSlotHandler = LazyOptional.of(() -> new CombinedInvWrapper(inputSlots, outputSlot));
    //endregion

    //-------------------------------------------------------------------------
    //region NBT
    @Override
    public void read(CompoundNBT compoundNBT) {

        CompoundNBT invInputTag = compoundNBT.getCompound("invInput");
        inputSlots.deserializeNBT(invInputTag);

        CompoundNBT invOutputTag = compoundNBT.getCompound("invOutput");
        outputSlot.deserializeNBT(invOutputTag);

        CompoundNBT tankTag = compoundNBT.getCompound("tank");
        fluidTank.ifPresent(h -> h.readFromNBT(tankTag));

        CompoundNBT energyTag = compoundNBT.getCompound("energy");
        energyStorage.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(energyTag));

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {

        CompoundNBT invInputTag = inputSlots.serializeNBT();
        compoundNBT.put("invInput", invInputTag);

        CompoundNBT invOutputTag = outputSlot.serializeNBT();
        compoundNBT.put("invOutput", invOutputTag);

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
    //endregion

    //-------------------------------------------------------------------------
    //region IWootDebug
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> InfuserTileEntity");
        debug.add("      Tank " + getTankFluid().getTranslationKey() + " " + getTankFluid().getAmount());
        debug.add("      Energy " + getEnergy());
        return debug;
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Container
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.woot.infuser.name");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new InfuserContainer(i, world, pos, playerInventory, playerEntity);
    }
    //endregion


    //-------------------------------------------------------------------------
    //region Client sync
    private int clientFluidAmount = 0;
    public int getClientFluidAmount() { return clientFluidAmount; }
    public void setClientFluidAmount(int v) { clientFluidAmount = v; }

    private int progress;
    public int getProgress() {
        return calculateProgress();
    }
    public int getClientProgress() { return progress; }
    public void setProgress(int v) { progress = v; }
    //endregion

    //-------------------------------------------------------------------------
    //region Machine Process
    private InfuserRecipe currRecipe = null;

    @Override
    protected boolean hasEnergy() {
        return energyStorage.map(e -> e.getEnergyStored() > 0).orElse(false);
    }

    @Override
    protected int useEnergy() {
        return energyStorage.map(e -> e.extractEnergy(InfuserConfiguration.INFUSER_ENERGY_PER_TICK.get(), false)).orElse(0);
    }

    @Override
    protected void clearRecipe() {
        currRecipe = null;
    }

    @Override
    protected int getRecipeEnergy() {
        return currRecipe != null ? currRecipe.getEnergy() : 0;
    }

    @Override
    protected void processFinish() {
        if (currRecipe == null)
            getRecipe();
        if (currRecipe == null) {
            processOff();
            return;
        }

        final int inputSize = currRecipe.getIngredient().getMatchingStacks()[0].getCount();
        final int augmentSize = currRecipe.hasAugment() ? currRecipe.getAugment().getMatchingStacks()[0].getCount() : 1;

        inputSlots.extractItem(INPUT_SLOT, inputSize, false);
        if (currRecipe.hasAugment())
            inputSlots.extractItem(AUGMENT_SLOT, augmentSize, false);

        ItemStack itemStack = currRecipe.getOutput();
        if (itemStack.getItem() == Items.ENCHANTED_BOOK) {
            // stack size determines the enchant level, so save it off and reset to single item generated
            int level = itemStack.getCount();
            itemStack = new ItemStack(Items.BOOK, 1);
            itemStack = EnchantingHelper.addRandomBookEnchant(itemStack, level);
        }

        outputSlot.insertItem(OUTPUT_SLOT, itemStack, false);
        fluidTank.ifPresent(f -> f.drain(currRecipe.getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE));
        markDirty();
    }

    @Override
    protected boolean canStart() {
        if (inputSlots.getStackInSlot(INPUT_SLOT).isEmpty())
            return false;

        if (fluidTank.map(f -> f.isEmpty()).orElse(true))
            return false;

        getRecipe();
        if (currRecipe == null)
            return false;

        // Can only start for enchanted books if the output slot is empty
        if (currRecipe.getOutput().getItem() == Items.ENCHANTED_BOOK && !outputSlot.getStackInSlot(OUTPUT_SLOT).isEmpty())
            return false;

        return true;
    }

    @Override
    protected boolean hasValidInput() {
        if (currRecipe == null)
            getRecipe();

        return currRecipe == null ? false : true;
    }

    @Override
    protected boolean isDisabled() {
        return false;
    }
    //endregion

    private void getRecipe() {
        if (fluidTank.map(h -> h.isEmpty()).orElse(false)) {
            clearRecipe();
            return;
        }

        List<InfuserRecipe> recipes = world.getRecipeManager().getRecipes(
                INFUSER_TYPE,
                new Inventory(
                        inputSlots.getStackInSlot(INPUT_SLOT),
                        inputSlots.getStackInSlot(AUGMENT_SLOT)),
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

        clearRecipe();
    }

    public FluidStackPacket getFluidStackPacket() {
        return new FluidStackPacket(fluidTank.map(f -> f.getFluid()).orElse(FluidStack.EMPTY));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null)
                // eg. player is in the gui
                return allSlotHandler.cast();
            else if (side == Direction.UP || side == Direction.EAST)
                return inputSlotHandler.cast();
            else if (side == Direction.DOWN || side == Direction.WEST)
                return outputWrappedSlotHandler.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTank.cast();
        } else if (cap == CapabilityEnergy.ENERGY) {
            return energyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    public void dropContents(World world, BlockPos pos) {
        for (int i = 0; i < 2; i++) {
            ItemStack itemStack = inputSlots.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                inputSlots.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        ItemStack itemStack = outputSlot.getStackInSlot(OUTPUT_SLOT);
        if (!itemStack.isEmpty()) {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            outputSlot.setStackInSlot(OUTPUT_SLOT, ItemStack.EMPTY);
        }

         markDirty();
        if (world != null)
            WorldHelper.updateClient(world, pos);
    }

}
