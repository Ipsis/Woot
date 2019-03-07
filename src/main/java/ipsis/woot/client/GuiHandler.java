package ipsis.woot.client;

import ipsis.woot.Woot;
import ipsis.woot.machines.squeezer.ContainerSqueezer;
import ipsis.woot.machines.squeezer.GuiSqueezer;
import ipsis.woot.machines.squeezer.TileEntitySqueezer;
import ipsis.woot.util.IGuiTile;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;

public class GuiHandler {

    @Nullable
    public static GuiScreen getClientGuiElement(FMLPlayMessages.OpenContainer message) {
        PacketBuffer buf = message.getAdditionalData();
        BlockPos pos = buf.readBlockPos();
        Woot.LOGGER.info("getClientGuiElement: " + message.getId() + " " + pos);

        World world = Woot.proxy.getClientWorld();
        EntityPlayer entityPlayer = Woot.proxy.getClientPlayer();
        TileEntity te = world.getTileEntity(pos);

        return new GuiSqueezer((TileEntitySqueezer)te, new ContainerSqueezer(entityPlayer.inventory, (TileEntitySqueezer)te));
/*        return new GuiSqueezer(this, new ContainerSqueezer(entityPlayer.inventory, this));
        if (te instanceof IGuiTile) {
            Woot.LOGGER.info("getClientGuiElement: opening IGuiTile" + entityPlayer);
            return ((IGuiTile) te).createGui(entityPlayer);
        }

        return null; */
    }
}
