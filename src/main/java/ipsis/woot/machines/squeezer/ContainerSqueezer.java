package ipsis.woot.machines.squeezer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSqueezer extends Container {


    private TileEntitySqueezer squeezer;

    public ContainerSqueezer(IInventory playerInventory, TileEntitySqueezer squeezer) {
        this.squeezer = squeezer;
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {

        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlot(new Slot(playerInventory, row, x, y));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return squeezer.canInteractWith(playerIn);
    }

}
