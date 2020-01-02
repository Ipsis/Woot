package ipsis.woot.modules.infuser.blocks;

import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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
import java.util.concurrent.atomic.AtomicInteger;

public class InfuserTileEntity extends TileEntity implements ITickableTileEntity, WootDebug, INamedContainerProvider {

    public InfuserTileEntity() {
        super(InfuserSetup.INFUSER_BLOCK_TILE.get());
    }

    @Override
    public void tick() {

        if (world.isRemote)
            return;

        if (world.getGameTime() % 20 != 0)
            return;

        itemHandler.ifPresent(h -> {
            ItemStack itemStack = h.getStackInSlot(0);
            if (!itemStack.isEmpty()) {
                fluidTank.ifPresent(f -> {
                    InfuserRecipe recipe = InfuserRecipe.findRecipe(itemStack, f.getFluid(), ItemStack.EMPTY);
                    if (recipe != null && h.insertItem(OUTPUT_SLOT, recipe.output.copy(), true).isEmpty()) {
                        h.extractItem(INPUT_SLOT, 1, false);
                        h.extractItem(AUGMENT_SLOT, 1, false);
                        f.drain(recipe.fluid.copy(), IFluidHandler.FluidAction.EXECUTE);
                        h.insertItem(OUTPUT_SLOT, recipe.output.copy(), false);
                        markDirty();
                    }
                });
            }
        });
    }

    public int getTankAmount() {
        AtomicInteger v = new AtomicInteger(0);
        fluidTank.ifPresent(h -> {
            v.set(h.getFluidAmount());
        });
        return v.get();
    }

    public void setTankAmount(int v) {
        fluidTank.ifPresent(h -> {
            if (h.getFluidAmount() == 0)
                h.setFluid(FluidStack.EMPTY);
            else
                h.setFluid(new FluidStack(h.getFluid(), v));
        });
    }

    public int getTankFluid() {
        AtomicInteger v = new AtomicInteger(-1);
        fluidTank.ifPresent(h -> {
            // reg id?
        });
        return v.get();
    }

    public void setTankFluid(int v) {
        fluidTank.ifPresent(h -> {
        });
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
        return new FluidTank(InfuserConfiguration.INFUSER_TANK_CAPACITY.get(), h -> InfuserRecipe.getValidFluids().contains(h.getFluid()));
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
}
