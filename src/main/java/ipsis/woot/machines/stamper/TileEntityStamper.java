package ipsis.woot.machines.stamper;

import ipsis.woot.util.IDebug;
import ipsis.woot.util.IGuiTile;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The stamper basically creates items from liquids ..... for Woot
 * Pure Dye can be used to convert a Dye Blank into on of the standard 16 colors of Dye Plates
 * Pure Enchant can be used to convert Stygian Iron Plates into Enchanted Plates for the upgrades
 */

public class TileEntityStamper extends TileEntity implements IDebug, ITickable, IGuiTile {

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return !isInvalid() && entityPlayer.getDistanceSq(getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    /**
     * IDebug
     */
    @Override
    public void getDebugText(List<String> debug) {
    }

    /**
     * ITickable
     */
    @Override
    public void update() {

        if (WorldHelper.isClientWorld(getWorld()))
            return;
    }


    /**
     * IGuiTile
     */
    @Override
    public Container createContainer(EntityPlayer entityPlayer) {
        return new ContainerStamper(entityPlayer.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer entityPlayer) {
        return new GuiStamper(this, new ContainerStamper(entityPlayer.inventory, this));
    }

    /**
     *
     */
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            // TODO only allow dye blanks
            return true;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileEntityStamper.this.markDirty();
        }
    };

    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityStamper.this.markDirty();
        }
    };

    private CombinedInvWrapper combinedInvWrapper = new CombinedInvWrapper(inputHandler, outputHandler);

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
         * Insert from the top
         * Extract from the bottom
         */
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedInvWrapper);
            else if (facing == EnumFacing.UP)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            else if (facing == EnumFacing.DOWN)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
        }

        return super.getCapability(capability, facing);
    }
}
