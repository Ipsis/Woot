package ipsis.woot.machines.squeezer;

import ipsis.woot.Woot;
import ipsis.woot.config.MachineConfig;
import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.IGuiTile;
import ipsis.woot.util.IRestorableTileEntity;
import ipsis.woot.util.WootEnergyStorage;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntitySqueezer extends TileEntity implements IWootDebug, IGuiTile, IInteractionObject, ITickable, IRestorableTileEntity {

    public static final int INPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS;

    public TileEntitySqueezer() {
        super(ModTileEntities.squeezerTileEntity);
    }

    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return !isRemoved() && entityPlayer.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    /**
     * Input item handler
     */
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return SqueezerRegistry.INSTANCE.getRecipe(stack) != null;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileEntitySqueezer.this.markDirty();
        }
    };

    /**
     * IGuiTile
     */
    @Override
    public GuiContainer createGui(EntityPlayer entityPlayer) {
        return new GuiSqueezer(this, new ContainerSqueezer(entityPlayer.inventory, this));
    }

    /**
     * IInteractionObject
     */
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerSqueezer(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "woot:squeezer";
    }

    /**
     * IINameable
     */
    @Override
    public ITextComponent getName() {
        return new TextComponentTranslation("gui.woot.squeezer");
    }

    @Override
    public boolean hasCustomName() { return false; }

    @Nullable
    @Override
    public ITextComponent getCustomName() { return null; }

    /**
     * Internal tank
     */
    private FluidTank tank = new FluidTank(MachineConfig.SQUEEZER_TANK_CAPACITY.get());


    /**
     * Capabilities
     */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return LazyOptional.of(() -> (T) inputHandler);
        else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return LazyOptional.of(() -> (T) tank);
        else if (cap == CapabilityEnergy.ENERGY)
            return LazyOptional.of(() -> (T) energyStorage);

        return super.getCapability(cap, side);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext context) {
        debug.add("====> TileEntitySqueezer");
        debug.add("Energy:" + energyStorage.getEnergyStored() + " / " + energyStorage.getMaxEnergyStored());
        debug.add(String.format("Red:%d Yellow:%d Blue:%d White:%d Pure:%d",
            red, yellow, blue, white, tank.getFluidAmount()));
        return debug;
    }

    /**
     * Energy
     */
    private WootEnergyStorage energyStorage = new WootEnergyStorage(MachineConfig.SQUEEZER_ENERGY_CAPACITY.get(), MachineConfig.SQUEEZER_ENERGY_RX.get());

    /**
     * NBT
     */
    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }


    /**
     * IRestorableTileEntity
     */
    @Override
    public void writeRestorableToNBT(NBTTagCompound nbtTagCompound) {
        energyStorage.writeToNBT(nbtTagCompound);
        tank.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInt("red", red);
        nbtTagCompound.setInt("yellow", yellow);
        nbtTagCompound.setInt("blue", blue);
        nbtTagCompound.setInt("white", white);
        nbtTagCompound.setInt("progress", progress);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound nbtTagCompound) {
        energyStorage.readFromNBT(nbtTagCompound);
        tank.readFromNBT(nbtTagCompound);
        red = nbtTagCompound.getInt("red");
        yellow = nbtTagCompound.getInt("yellow");
        blue = nbtTagCompound.getInt("blue");
        white = nbtTagCompound.getInt("white");
        progress = nbtTagCompound.getInt("progress");
    }

    /**
     * ITickable
     */
    @Override
    public void tick() {
        if (WorldHelper.isClientWorld(world))
            return;

        if (energyStorage.getEnergyStored() < MachineConfig.SQUEEZER_RF_PER_TICK.get())
            return;

        if (progress > 0) {
            energyStorage.extractEnergyInternal(MachineConfig.SQUEEZER_RF_PER_TICK.get(), false);
            progress -= MachineConfig.SQUEEZER_RF_PER_TICK.get();
            if (progress <= 0)
                processOutputs();
            markDirty();
        } else {
            tryStart();
        }
    }

    /**
     * Prccessing
     */
    private int progress = 0;
    private int red = 0;
    private int yellow = 0;
    private int blue = 0;
    private int white = 0;
    private void tryStart() {
        progress = 0;
        for (int i = 0; i < INPUT_SLOTS; i++) {
            SqueezerRegistry.RecipeSqueezer recipe = SqueezerRegistry.INSTANCE.getRecipe(inputHandler.getStackInSlot(i));
            if (recipe != null) {
                progress = MachineConfig.SQUEEZER_RF_PER_RECIPE.get();
                break;
            }
        }
        markDirty();
        return;
    }

    private void processOutputs() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            SqueezerRegistry.RecipeSqueezer recipe = SqueezerRegistry.INSTANCE.getRecipe(inputHandler.getStackInSlot(i));
            if (recipe != null) {
                red += recipe.getDyeMakeup().getRed();
                yellow += recipe.getDyeMakeup().getYellow();
                blue += recipe.getDyeMakeup().getBlue();
                white += recipe.getDyeMakeup().getWhite();
                red = MathHelper.clamp(red, 0, MachineConfig.SQUEEZER_COLOR_CAPACITY.get());
                yellow = MathHelper.clamp(yellow, 0, MachineConfig.SQUEEZER_COLOR_CAPACITY.get());
                blue = MathHelper.clamp(blue, 0, MachineConfig.SQUEEZER_COLOR_CAPACITY.get());
                white = MathHelper.clamp(white, 0, MachineConfig.SQUEEZER_COLOR_CAPACITY.get());
                inputHandler.extractItem(i, 1, false);

                /**
                 * Prccess the output tank
                 */
                if (red >= MachineConfig.SQUEEZER_RED_MIX.get() &&
                    yellow >= MachineConfig.SQUEEZER_YELLOW_MIX.get() &&
                    blue >= MachineConfig.SQUEEZER_BLUE_MIX.get() &&
                    white >= MachineConfig.SQUEEZER_WHITE_MIX.get()) {

                    // @todo is there space in the output tank
                    red -= MachineConfig.SQUEEZER_RED_MIX.get();
                    yellow -= MachineConfig.SQUEEZER_YELLOW_MIX.get();
                    blue -= MachineConfig.SQUEEZER_BLUE_MIX.get();
                    white -= MachineConfig.SQUEEZER_WHITE_MIX.get();

                    Woot.LOGGER.info("Add 1000mb of pre dye");
                }
                break;
            }
        }
    }

    /**
     * Client Data - for gui display
     */
    private int clientEnergy = -1;
    private int clientProgress = -1;
    private int clientRed = -1;
    private int clientYellow = -1;
    private int clientBlue = -1;
    private int clientWhite = -1;
    private int clientPure = -1;

    public void setClientInfo(int energy, int progress, int red, int yellow, int blue, int white, int pure) {
        this.clientEnergy = energy;
        this.clientProgress = progress;
        this.clientRed = red;
        this.clientYellow = yellow;
        this.clientBlue = blue;
        this.clientWhite = white;
        this.clientPure = pure;
    }

    public boolean doesClientNeedSync() {
        if (clientEnergy != energyStorage.getEnergyStored() ||
            clientProgress != progress ||
            clientRed != red ||
            clientYellow != yellow ||
            clientBlue != blue ||
            clientWhite != white ||
            clientPure != tank.getFluidAmount())
            return true;

        return false;
    }

    public void refreshClientValuesWithCurrent() {
        clientEnergy = energyStorage.getEnergyStored();
        clientProgress = 100 - (int)((100.0F / (float)MachineConfig.SQUEEZER_RF_PER_RECIPE.get()) * (float)progress);
        clientProgress = MathHelper.clamp(clientProgress, 0, 100);
        clientRed = red;
        clientYellow = yellow;
        clientBlue = blue;
        clientWhite = white;
        clientPure = tank.getFluidAmount();

        /**
         * progress counts downwards from the total RF to zero
         */
        if (progress == 0) {
            clientProgress = 0;
        } else {
            int t = (int)((100.0F / (float)MachineConfig.SQUEEZER_RF_PER_RECIPE.get()) * (float)progress);
            clientProgress = MathHelper.clamp(100 - t, 0, 100);
        }
    }

    public int getClientEnergy() { return clientEnergy; }
    public int getClientProgress() { return clientProgress; }
    public int getClientRed() { return clientRed; }
    public int getClientYellow() { return clientYellow; }
    public int getClientBlue() { return clientBlue; }
    public int getClientWhite() { return clientWhite; }
    public int getClientPure() { return clientPure; }
}
