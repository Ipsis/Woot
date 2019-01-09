package ipsis.woot.machines.squeezer;

import ipsis.woot.util.IDebug;
import ipsis.woot.util.IGuiTile;
import ipsis.woot.util.IRestorableTileEntity;
import ipsis.woot.util.helpers.LogHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntitySqueezer extends TileEntity implements IDebug, ITickable, IGuiTile, IRestorableTileEntity {

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 0;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    private static final int CONFIG_PURE_RED = 250;
    private static final int CONFIG_PURE_YELLOW = 250;
    private static final int CONFIG_PURE_BLUE = 250;
    private static final int CONFIG_PURE_WHITE = 250;
    private static final int CONFIG_SQUEEZE_TICKS = 20;

    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return !isInvalid() && entityPlayer.getDistanceSq(getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    private int TANK_MB_SIZE = 5000;
    private int progress = 0;
    private int redTank = 0;
    private int yellowTank = 0;
    private int blueTank = 0;
    private int whiteTank = 0;
    private int pureTank = 0;

    private int clientProgress = -1;
    private int clientRedTank = -1;
    private int clientYellowTank = -1;
    private int clientBlueTank = -1;
    private int clientWhiteTank = -1;
    private int clientPureTank = -1;

    public int getProgress() { return this.progress; }
    public int getRedTank() { return redTank; }
    public int getYellowTank() { return yellowTank; }
    public int getBlueTank() { return blueTank; }
    public int getWhiteTank() { return whiteTank; }
    public int getPureTank() { return pureTank; }

    public int getClientProgress() { return this.clientProgress; }
    public void setClientProgress(int clientProgress) { this.clientProgress = clientProgress; }
    public int getClientRedTank() { return clientRedTank; }
    public void setClientRedTank(int clientRedTank) { this.clientRedTank = clientRedTank; }
    public int getClientYellowTank() { return clientYellowTank; }
    public void setClientYellowTank(int clientYellowTank) { this.clientYellowTank = clientYellowTank; }
    public int getClientBlueTank() { return clientBlueTank; }
    public void setClientBlueTank(int clientBlueTank) { this.clientBlueTank = clientBlueTank; }
    public int getClientWhiteTank() { return clientWhiteTank; }
    public void setClientWhiteTank(int clientWhiteTank) { this.clientWhiteTank = clientWhiteTank; }
    public int getClientPureTank() { return clientPureTank; }
    public void setClientPureTank(int clientPureTank) { this.clientPureTank = clientPureTank; }

    /**
     * ITickable
     */
    @Override
    public void update() {

        if (WorldHelper.isClientWorld(getWorld()))
            return;


        if (progress > 0) {
            progress--;
            if (progress <= 0) {
                // finished
                processOutputs();
            }
            markDirty();
        } else {
            tryStart();
        }
    }

    private void processOutputs() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            SqueezerManager.SqueezerRecipe recipe = SqueezerManager.INSTANCE.getRecipe(inputHandler.getStackInSlot(i));
            if (recipe != null) {
                LogHelper.info("fill tanks with " + recipe.getDyeMakeup());
                if (insertOutput(recipe.getDyeMakeup(), false)) {
                    inputHandler.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }

    private void tryStart() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            SqueezerManager.SqueezerRecipe recipe = SqueezerManager.INSTANCE.getRecipe(inputHandler.getStackInSlot(i));
            if (recipe != null && insertOutput(recipe.getDyeMakeup(), true)) {
                progress = CONFIG_SQUEEZE_TICKS;
                markDirty();
            }
        }
    }

    private boolean insertOutput(SqueezerManager.DyeMakeup dyeMakeup, boolean simulate) {

        int newRed = redTank + dyeMakeup.getRed();
        int newYellow = yellowTank + dyeMakeup.getYellow();
        int newBlue = blueTank + dyeMakeup.getBlue();
        int newWhite = whiteTank + dyeMakeup.getWhite();

        // Must be space in at least one tank
        if (newRed > TANK_MB_SIZE && newYellow > TANK_MB_SIZE && newBlue > TANK_MB_SIZE && newWhite > TANK_MB_SIZE)
            return false;

        if (!simulate) {
            redTank += dyeMakeup.getRed();
            yellowTank += dyeMakeup.getYellow();
            blueTank += dyeMakeup.getBlue();
            whiteTank += dyeMakeup.getWhite();

            redTank = MathHelper.clamp(redTank, 0, TANK_MB_SIZE);
            yellowTank = MathHelper.clamp(yellowTank, 0, TANK_MB_SIZE);
            blueTank = MathHelper.clamp(blueTank, 0, TANK_MB_SIZE);
            whiteTank = MathHelper.clamp(whiteTank, 0, TANK_MB_SIZE);

            while (redTank >= CONFIG_PURE_RED && yellowTank >= CONFIG_PURE_YELLOW && blueTank >= CONFIG_PURE_BLUE && whiteTank >= CONFIG_PURE_WHITE && (pureTank + 1000 <= TANK_MB_SIZE) ) {
                LogHelper.info("Generate 1000 pure");
                pureTank += 1000;
                redTank -= CONFIG_PURE_RED;
                yellowTank -= CONFIG_PURE_YELLOW;
                blueTank -= CONFIG_PURE_BLUE;
                whiteTank -= CONFIG_PURE_WHITE;
            }
        }

        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    /**
     * IDebug
     */
    @Override
    public void getDebugText(List<String> debug) {
        debug.add("progress:" + progress + " red:" + redTank + " yellow:" + yellowTank + " blue:" + blueTank + " white:" + whiteTank + " pure:" + pureTank);
    }

    /**
     * IGuiTile
     */
    @Override
    public Container createContainer(EntityPlayer entityPlayer) {
        return new ContainerSqueezer(entityPlayer.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer entityPlayer) {
        return new GuiSqueezer(this, new ContainerSqueezer(entityPlayer.inventory, this));
    }

    /**
     * IRestorableTileEntity
     */
    @Override
    public void readRestorableFromNBT(NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("inputs"))
            inputHandler.deserializeNBT((NBTTagCompound)nbtTagCompound.getTag("inputs"));

        progress = nbtTagCompound.getInteger("progress");
        redTank = nbtTagCompound.getInteger("red");
        yellowTank = nbtTagCompound.getInteger("yellow");
        blueTank = nbtTagCompound.getInteger("blue");
        whiteTank = nbtTagCompound.getInteger("white");
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag("inputs", inputHandler.serializeNBT());
        nbtTagCompound.setInteger("progress", progress);
        nbtTagCompound.setInteger("red", redTank);
        nbtTagCompound.setInteger("yellow", yellowTank);
        nbtTagCompound.setInteger("blue", blueTank);
        nbtTagCompound.setInteger("white", whiteTank);
    }

    /**
     *
     */
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return SqueezerManager.INSTANCE.getRecipe(stack) != null;
        }

        @Override
        protected void onContentsChanged(int slot) {
            /**
             * We need to tell the tile entity that something has changed so
             * that the chest contents are persistent
             */
            TileEntitySqueezer.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedInvWrapper = new CombinedInvWrapper(inputHandler);

    /**
     *
     */
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        /**
         * Can only insert from the top
         */
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedInvWrapper);
            else if (facing == EnumFacing.UP)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        }
        return super.getCapability(capability, facing);
    }
}
