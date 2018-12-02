package ipsis.woot.blocks.heart;

import ipsis.woot.blocks.TileEntityHeart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerHeart extends Container {

    private TileEntityHeart tileEntityHeart;

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntityHeart.canInteractWith(playerIn);
    }

    public ContainerHeart(IInventory playerInv, TileEntityHeart tileEntityHeart) {
        this.tileEntityHeart = tileEntityHeart;

        // there are NO slots in this container
    }
}
