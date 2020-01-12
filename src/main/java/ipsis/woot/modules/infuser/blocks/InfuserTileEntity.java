package ipsis.woot.modules.infuser.blocks;

import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.network.FluidStackPacket;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.util.EnchantingHelper;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import ipsis.woot.util.WootMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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

public class InfuserTileEntity extends WootMachineTileEntity implements WootDebug, INamedContainerProvider {

    public InfuserTileEntity() {
        super(InfuserSetup.INFUSER_BLOCK_TILE.get());
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
    //endregion

    //-------------------------------------------------------------------------
    //region NBT
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
    //endregion

    //-------------------------------------------------------------------------
    //region IWootDebug
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> InfuserTileEntity");
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

        itemHandler.ifPresent(i -> {
            i.extractItem(INPUT_SLOT, inputSize, false);
            if (currRecipe.hasAugment())
                i.extractItem(AUGMENT_SLOT, augmentSize, false);

            ItemStack itemStack = currRecipe.getOutput();
            if (itemStack.getItem() == Items.ENCHANTED_BOOK) {
                // stack size determines the enchant level, so save it off and reset to single item generated
                int level = itemStack.getCount();
                itemStack = new ItemStack(Items.BOOK, 1);
                itemStack = EnchantingHelper.addRandomBookEnchant(itemStack, level);
            }

            i.insertItem(OUTPUT_SLOT, itemStack, false);
        });

        fluidTank.ifPresent(f -> f.drain(currRecipe.getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE));
        markDirty();
    }

    @Override
    protected boolean canStart() {
        if (itemHandler.map(h -> h.getStackInSlot(INPUT_SLOT).isEmpty()).orElse(true))
            return false;

        if (fluidTank.map(f -> f.isEmpty()).orElse(true))
            return false;

        getRecipe();
        if (currRecipe == null)
            return false;

        // Can only start for enchanted books if the output slot is empty
        if (currRecipe.getOutput().getItem() == Items.ENCHANTED_BOOK && !itemHandler.map(h -> h.getStackInSlot(OUTPUT_SLOT).isEmpty()).orElse(true))
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

        clearRecipe();
    }

    public FluidStackPacket getFluidStackPacket() {
        return new FluidStackPacket(fluidTank.map(f -> f.getFluid()).orElse(FluidStack.EMPTY));
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

}
