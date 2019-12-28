package ipsis.woot.misc.squeezer;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.mod.ModFluids;
import ipsis.woot.util.WootDebug;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
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

public class SqueezerTileEntity extends TileEntity implements ITickableTileEntity, WootDebug, INamedContainerProvider {

    public SqueezerTileEntity() { super(ModBlocks.SQUEEZER_BLOCK_TILE); }

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
                SqueezerRegistry.Recipe recipe = SqueezerRegistry.get().getRecipe(itemStack);
                if (recipe != null) {
                    Woot.LOGGER.info("Tick {} {}", itemStack.getTranslationKey(), recipe.getDyeMakeup());

                    red += recipe.getDyeMakeup().getRed();
                    yellow += recipe.getDyeMakeup().getYellow();
                    blue += recipe.getDyeMakeup().getBlue();
                    white += recipe.getDyeMakeup().getWhite();

                    while (canCreateOutput() && canStoreOutput()) {
                        FluidTank tank = fluidTank.orElseThrow(NullPointerException::new);
                        tank.fill(new FluidStack(ModFluids.CONATUS_FLUID.get(), 1000), IFluidHandler.FluidAction.EXECUTE);
                        red -= 20;
                        yellow -= 20;
                        blue -= 20;
                        white -= 20;
                        Woot.LOGGER.info("Tick created output");
                    }
                }
                h.extractItem(0, 1, false);
                markDirty();
            }
        });
    }

    private boolean canCreateOutput() {
        return red >= 20 && yellow >= 20 && blue >= 20 && white >= 20;
    }

    private boolean canStoreOutput() {

        AtomicBoolean v = new AtomicBoolean(false);
        fluidTank.ifPresent(h -> {
            if (h.fill(new FluidStack(ModFluids.CONATUS_FLUID.get(), 1000), IFluidHandler.FluidAction.SIMULATE) == 1000)
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
            h.setFluid(new FluidStack(ModFluids.CONATUS_FLUID.get(), v));
        });
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> SqueezerTileEntity");
        debug.add("      r:" + red + " y:" + yellow + " b:" + blue + " w:" + white);
        return debug;
    }

    /**
     * Tank
     */
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(10000, h -> h.isFluidEqual(new FluidStack(ModFluids.CONATUS_FLUID.get(), 1)));
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
                return SqueezerRegistry.get().getRecipe(stack) != null;
            }
        };
    }

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compoundNBT) {
        CompoundNBT invTag = compoundNBT.getCompound("inv");
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));

        CompoundNBT tankTag = compoundNBT.getCompound("tank");
        fluidTank.ifPresent(h -> ((FluidTank)h).readFromNBT(tankTag));

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
            CompoundNBT tankTag = ((FluidTank)h).writeToNBT(new CompoundNBT());
            compoundNBT.put("tank", tankTag);
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
        return new SqueezerContainer(i, world, pos, playerInventory, playerEntity);
    }
}
