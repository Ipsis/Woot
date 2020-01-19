package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.SqueezerSetup;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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

import static ipsis.woot.crafting.DyeSqueezerRecipe.DYE_SQUEEZER_TYPE;

public class DyeSqueezerTileEntity extends WootMachineTileEntity implements WootDebug, INamedContainerProvider {

    private int red = 0;
    private int yellow = 0;
    private int blue = 0;
    private int white = 0;

    public DyeSqueezerTileEntity() { super(SqueezerSetup.SQUEEZER_BLOCK_TILE.get()); }

    //-------------------------------------------------------------------------
    //region Tanks
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get(), h -> h.isFluidEqual(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), 1)));
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Energy
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get(), SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY_RX.get());
    }

    public int getEnergy() { return energyStorage.map(h -> h.getEnergyStored()).orElse(0); }
    public void setEnergy(int v) { energyStorage.ifPresent(h -> h.setEnergy(v)); }
    //endregion

    //-------------------------------------------------------------------------
    //region Inventory
    public static int INPUT_SLOT = 0;
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private IItemHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return DyeSqueezerRecipe.isValidInput(stack);
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
    //endregion

    //-------------------------------------------------------------------------
    //region WootDebug
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> SqueezerTileEntity");
        debug.add("      r:" + red + " y:" + yellow + " b:" + blue + " w:" + white);
        fluidTank.ifPresent(h -> debug.add("     p:" + h.getFluidAmount()));
        return debug;
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Container
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.woot.squeezer.name");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new DyeSqueezerContainer(i, world, pos, playerInventory, playerEntity);
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Client sync
    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }
    public void setRed(int v) { this.red = v; }
    public void setYellow(int v) { this.yellow = v; }
    public void setBlue(int v) { this.blue = v; }
    public void setWhite(int v) { this.white = v; }
    public int getPure() { return fluidTank.map(h -> h.getFluidAmount()).orElse(0); }
    public void setPure(int v) { fluidTank.ifPresent(h -> h.setFluid(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), v))); }
    //endregion

    //-------------------------------------------------------------------------
    //region Machine Process
    private DyeSqueezerRecipe currRecipe = null;

    @Override
    protected boolean hasEnergy() {
        return energyStorage.map(e -> e.getEnergyStored() > 0).orElse(false);
    }

    @Override
    protected int useEnergy() {
        return energyStorage.map(e -> e.extractEnergy(SqueezerConfiguration.DYE_SQUEEZER_ENERGY_PER_TICK.get(), false)).orElse(0);
    }

    @Override
    protected int getRecipeEnergy() {
        return currRecipe != null ? currRecipe.getEnergy() : 0;
    }

    @Override
    protected void clearRecipe() {
        currRecipe = null;
    }

    @Override
    protected void processFinish() {
        if (currRecipe == null)
            getRecipe();
        if (currRecipe == null) {
            processOff();
            return;
        }

        red += currRecipe.getRed();
        yellow += currRecipe.getYellow();
        blue += currRecipe.getBlue();
        white += currRecipe.getWhite();
        itemHandler.ifPresent(h -> h.extractItem(INPUT_SLOT, 1, false));
        fluidTank.ifPresent(f -> {
            while (canCreateOutput() && canStoreOutput()) {
                f.fill(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM * 4), IFluidHandler.FluidAction.EXECUTE);
                red -= DyeMakeup.LCM;
                yellow -= DyeMakeup.LCM;
                blue -= DyeMakeup.LCM;
                white -= DyeMakeup.LCM;
            }
        });
        markDirty();
    }

    @Override
    protected boolean canStart() {
        if (itemHandler.map(h -> h.getStackInSlot(INPUT_SLOT).isEmpty()).orElse(true))
            return false;

        getRecipe();
        if (currRecipe == null)
            return false;

        if (!canStoreInternal(currRecipe))
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
        currRecipe = world.getRecipeManager().getRecipe(DYE_SQUEEZER_TYPE,
                new Inventory(itemHandler.map(i -> i.getStackInSlot(INPUT_SLOT)).orElse(ItemStack.EMPTY)),
                world).orElse(null);
    }

    private boolean canStoreInternal(DyeSqueezerRecipe recipe) {
        if (recipe.getRed() + red > SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get())
            return false;
        if (recipe.getYellow() + yellow > SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get())
            return false;
        if (recipe.getBlue() + blue > SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get())
            return false;
        if (recipe.getWhite() + white > SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get())
            return false;

        return true;
    }

    private boolean canCreateOutput() { return red >= DyeMakeup.LCM && yellow >= DyeMakeup.LCM && blue >= DyeMakeup.LCM && white >= DyeMakeup.LCM; }
    private boolean canStoreOutput() { return fluidTank.map(h -> h.fill(new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM * 4), IFluidHandler.FluidAction.SIMULATE ) == DyeMakeup.LCM * 4).orElse(false); }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null || side == Direction.UP || side == Direction.WEST)
                return itemHandler.cast();
            else
                super.getCapability(cap, side);
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTank.cast();
        } else if (cap == CapabilityEnergy.ENERGY) {
            return energyStorage.cast();
        }
        return super.getCapability(cap, side);
    }


}
