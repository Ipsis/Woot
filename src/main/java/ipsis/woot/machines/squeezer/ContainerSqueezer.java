package ipsis.woot.machines.squeezer;

import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.PacketSyncSqueezerData;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSqueezer extends Container {

    private TileEntitySqueezer squeezer;

    public ContainerSqueezer(IInventory playerInventory, TileEntitySqueezer squeezer) {
        this.squeezer = squeezer;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 95;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 95;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileEntitySqueezer.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileEntitySqueezer.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileEntitySqueezer.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    private void addOwnSlots() {

        IItemHandler iItemHandler = this.squeezer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotItemHandler(iItemHandler, 0, 39, 40));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return squeezer.canInteractWith(entityPlayer);
    }

    private boolean needSync(TileEntitySqueezer squeezer) {

        if (this.squeezer.getProgress() != this.squeezer.getClientProgress()) return true;
        if (this.squeezer.getBlueTank() != this.squeezer.getClientBlueTank()) return true;
        if (this.squeezer.getRedTank() != this.squeezer.getClientRedTank()) return true;
        if (this.squeezer.getWhiteTank() != this.squeezer.getClientWhiteTank()) return true;
        if (this.squeezer.getYellowTank() != this.squeezer.getClientYellowTank()) return true;
        if (this.squeezer.getPureTank() != this.squeezer.getClientPureTank()) return true;

        return false;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (WorldHelper.isClientWorld(squeezer.getWorld()))
            return;

        if (needSync(squeezer)) {
            squeezer.setClientProgress(squeezer.getProgress());
            squeezer.setClientBlueTank(squeezer.getBlueTank());
            squeezer.setClientPureTank(squeezer.getPureTank());
            squeezer.setClientRedTank(squeezer.getRedTank());
            squeezer.setClientWhiteTank(squeezer.getWhiteTank());
            squeezer.setClientYellowTank(squeezer.getYellowTank());

            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP)listener;
                    PacketHandler.INSTANCE.sendTo(new PacketSyncSqueezerData(
                            squeezer.getClientProgress(), squeezer.getClientBlueTank(), squeezer.getClientPureTank(),
                            squeezer.getClientRedTank(), squeezer.getClientYellowTank(), squeezer.getClientPureTank()), entityPlayerMP);
                }
            }
        }
    }

    public void sync(int progress, int blue, int pure, int red, int white, int yellow) {
        squeezer.setClientProgress(progress);
        squeezer.setClientBlueTank(blue);
        squeezer.setClientPureTank(pure);
        squeezer.setClientRedTank(red);
        squeezer.setClientWhiteTank(white);
        squeezer.setClientYellowTank(yellow);
    }

}
