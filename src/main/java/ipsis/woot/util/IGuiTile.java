package ipsis.woot.util;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface IGuiTile {

    Container createContainer(EntityPlayer entityPlayer);
    GuiContainer createGui(EntityPlayer entityPlayer);
}
