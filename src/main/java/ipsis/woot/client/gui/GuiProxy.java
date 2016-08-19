package ipsis.woot.client.gui;

import ipsis.woot.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        // Manual has no container etc
        if (ID == Reference.GUI_MANUAL)
            return null;

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == Reference.GUI_MANUAL)
            return new GuiManual();

        return null;
    }
}
