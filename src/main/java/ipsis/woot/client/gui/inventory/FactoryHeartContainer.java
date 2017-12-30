package ipsis.woot.client.gui.inventory;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

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

    public void handleFarmInfo(FarmUIInfo info) {

        LogHelper.info("FactoryHeartContainer: handleFarmInfo");
        te.setGuiFarmInfo(info);
    }
}
