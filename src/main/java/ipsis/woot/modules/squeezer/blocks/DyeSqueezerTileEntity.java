package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.SqueezerSetup;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DyeSqueezerTileEntity extends TileEntity implements ITickableTileEntity, WootDebug, INamedContainerProvider {

    public DyeSqueezerTileEntity() { super(SqueezerSetup.SQUEEZER_BLOCK_TILE.get()); }

    private int red = 0;
    private int yellow = 0;
    private int blue = 0;
    private int white = 0;

    @Override
    public void tick() {

        if (world.isRemote)
            return;

        if (world.getGameTime() % 20 != 0)
            return;

        itemHandler.ifPresent(h -> {
            ItemStack itemStack = h.getStackInSlot(0);
            if (!itemStack.isEmpty()) {
                DyeSqueezerRecipe recipe = DyeSqueezerRecipe.findRecipe(itemStack);
                if (recipe != null && canStoreInternal(recipe)) {
                    red += recipe.output.getRed();
                    yellow += recipe.output.getYellow();
                    blue += recipe.output.getBlue();
                    white += recipe.output.getWhite();

                    while (canCreateOutput() && canStoreOutput()) {
                        fluidTank.ifPresent(t -> {
                            t.fill(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM * 4), IFluidHandler.FluidAction.EXECUTE);
                            red -= DyeMakeup.LCM;
                            yellow -= DyeMakeup.LCM;
                            blue -= DyeMakeup.LCM;
                            white -= DyeMakeup.LCM;
                        });
                    }
                    h.extractItem(0, 1, false);
                    markDirty();
                }
            }
        });
    }

    private boolean canStoreInternal(DyeSqueezerRecipe recipe) {
        if (recipe.output.getRed() + red > SqueezerConfiguration.INTERNAL_FLUID_MAX.get())
            return false;
        if (recipe.output.getYellow() + yellow > SqueezerConfiguration.INTERNAL_FLUID_MAX.get())
            return false;
        if (recipe.output.getBlue() + blue > SqueezerConfiguration.INTERNAL_FLUID_MAX.get())
            return false;
        if (recipe.output.getWhite() + white > SqueezerConfiguration.INTERNAL_FLUID_MAX.get())
            return false;

        return true;
    }

    private boolean canCreateOutput() {
        return red >= DyeMakeup.LCM && yellow >= DyeMakeup.LCM && blue >= DyeMakeup.LCM && white >= DyeMakeup.LCM;
    }

    private boolean canStoreOutput() {
        AtomicBoolean v = new AtomicBoolean(false);
        fluidTank.ifPresent(h -> {
            if (h.fill(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM * 4), IFluidHandler.FluidAction.SIMULATE ) == DyeMakeup.LCM * 4)
                v.set(true);
        });
        return v.get();
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

    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }
    public void setRed(int v) { this.red = v; }
    public void setYellow(int v) { this.yellow = v; }
    public void setBlue(int v) { this.blue = v; }
    public void setWhite(int v) { this.white = v; }
    public int getPure() {
        AtomicInteger v = new AtomicInteger(0);
        fluidTank.ifPresent(h -> {
            v.set(h.getFluidAmount());
        });
        return v.get();
    }

    public void setPure(int v) {
        fluidTank.ifPresent(h -> {
            h.setFluid(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), v));
        });
    }
    public int getEnergy() {
        return energyStorage.map(h -> h.getEnergyStored()).orElse(0);
    }
    public void setEnergy(int v) {
        energyStorage.ifPresent(h -> h.setEnergy(v));
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> SqueezerTileEntity");
        debug.add("      r:" + red + " y:" + yellow + " b:" + blue + " w:" + white);
        fluidTank.ifPresent(h -> {
            debug.add("     p:" + h.getFluidAmount());
        });
        return debug;
    }

    /**
     * Tank
     */
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(SqueezerConfiguration.TANK_CAPACITY.get(), h -> h.isFluidEqual(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), 1)));
    }

    /**
     * Inventory
     */
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private IItemHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return DyeSqueezerRecipe.findRecipe(stack) != null;
            }
        };
    }

    /**
     * Energy
     */
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(SqueezerConfiguration.MAX_ENERGY.get(), SqueezerConfiguration.MAX_ENERGY_RX.get());
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

        if (compoundNBT.contains("dye")) {
            CompoundNBT dyeTag = compoundNBT.getCompound("dye");
            red = dyeTag.getInt("red");
            yellow = dyeTag.getInt("yellow");
            blue = dyeTag.getInt("blue");
            white = dyeTag.getInt("white");
        }
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

        CompoundNBT dyeTag = new CompoundNBT();
        dyeTag.putInt("red", red);
        dyeTag.putInt("yellow", yellow);
        dyeTag.putInt("blue", blue);
        dyeTag.putInt("white", white);
        compoundNBT.put("dye", dyeTag);
        return super.write(compoundNBT);
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
        return new DyeSqueezerContainer(i, world, pos, playerInventory, playerEntity);
    }
}
