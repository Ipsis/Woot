package ipsis.woot.machines.squeezer;

import ipsis.woot.network.Messages;
import ipsis.woot.network.PacketSyncSqueezerState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSqueezer extends Container {


    private TileEntitySqueezer squeezer;

    public ContainerSqueezer(IInventory playerInventory, TileEntitySqueezer squeezer) {
        this.squeezer = squeezer;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addOwnSlots() {
        squeezer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
            addSlot(new SlotItemHandler(itemHandler, 0, 39, 40));
        });
    }

    private void addPlayerSlots(IInventory playerInventory) {

        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 95;
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 95;
            this.addSlot(new Slot(playerInventory, row, x, y));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        // Straight from McJty's YouTubeModdingTutorial
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileEntitySqueezer.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileEntitySqueezer.SIZE, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 0, TileEntitySqueezer.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (squeezer.doesClientNeedSync()) {
           squeezer.refreshClientValuesWithCurrent();

           for (IContainerListener listener : listeners) {
               if (listener instanceof EntityPlayerMP) {
                   EntityPlayerMP playerMP = (EntityPlayerMP)listener;
                   Messages.INSTANCE.sendTo(
                           new PacketSyncSqueezerState(
                                   squeezer.getClientEnergy(),
                                   squeezer.getClientProgress(),
                                   squeezer.getClientRed(),
                                   squeezer.getClientYellow(),
                                   squeezer.getClientBlue(),
                                   squeezer.getClientWhite(),
                                   squeezer.getClientPure()),
                           playerMP.connection.getNetworkManager(),
                           NetworkDirection.PLAY_TO_CLIENT);
               }
           }

        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return squeezer.canInteractWith(playerIn);
    }

    public void sync(int energy, int progress, int red, int yellow, int blue, int white, int pure) {
        squeezer.setClientInfo(energy,progress,red,yellow, blue, white, pure);
    }

}
