package ipsis.woot.client.gui.inventory;

import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.packets.PacketFixedProgressBar;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FactoryHeartContainer extends Container {

    private TileEntityMobFactoryHeart te;

    public FactoryHeartContainer(IInventory playerInventory, TileEntityMobFactoryHeart te) {

        this.te = te;

        /**
         * No slots
        for (int row = 0; row < 9; row++) {
            int x = 9 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        } */
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }


    /**
     * NB: This can only sync data as shorts :(
     */

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {


        if (id == 0)
            this.te.guiProgress = data;
        else if (id == 1)
            this.te.guiStoredPower = data;
        else if (id == 2)
            this.te.guiRunning = data;
    }

    private int prevGuiProgress = -1;
    private int prevGuiStoredPower = -1;
    private int prevGuiRunning = -1;
    @Override
    public void detectAndSendChanges() {

        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); i++) {

            IContainerListener listener = this.listeners.get(i);
            if (listener instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP)listener;
                if (this.prevGuiProgress != this.te.getRecipeProgress())
                    listener.sendWindowProperty(this, 0, this.te.getRecipeProgress());
                if (this.prevGuiStoredPower != this.te.getStoredPower())
                    PacketHandler.INSTANCE.sendTo(new PacketFixedProgressBar(windowId, 1, this.te.getStoredPower()), player);
                if (this.prevGuiRunning != this.te.getRunning())
                    listener.sendWindowProperty(this, 2, this.te.getRunning());
            }

        }

        this.prevGuiProgress = this.te.getRecipeProgress();
        this.prevGuiStoredPower = this.te.getStoredPower();
        this.prevGuiRunning = this.te.getRunning();
    }

    private boolean sync = false;
    public void handleFarmInfo(FarmUIInfo info) {

        te.setGuiFarmInfo(info);
        sync = true;
    }

    public boolean hasServerSync() {
        return sync;
    }
}
