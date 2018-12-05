package ipsis.woot.blocks.heart;

import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (WorldHelper.isServerWorld(tileEntityHeart.getWorld())) {

            int running = tileEntityHeart.isRunning() ? 1 : 0;
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    EntityPlayerMP playerMP = (EntityPlayerMP) listener;
                    if (tileEntityHeart.getClientProgress() != tileEntityHeart.getRecipeProgress())
                        listener.sendWindowProperty(this, 0, tileEntityHeart.getRecipeProgress());

                    if (tileEntityHeart.getClientRunning() != running)
                        listener.sendWindowProperty(this, 1, running);
                }
            }

            tileEntityHeart.setClientRunning(running);
            tileEntityHeart.setClientProgress(tileEntityHeart.getRecipeProgress());
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0)
            tileEntityHeart.setClientProgress(data);
        else if (id == 1)
            tileEntityHeart.setClientRunning(data);
    }

    @SideOnly(Side.CLIENT)
    public void syncStaticHeartData(HeartUIFixedInfo info) {
        tileEntityHeart.setClientUIFixedInfo(info);
    }
}
