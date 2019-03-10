package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.Woot;
import ipsis.woot.machines.squeezer.ContainerSqueezer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncSqueezerState {

    private int energy;
    private int progress;
    private int red;
    private int yellow;
    private int blue;
    private int white;
    private int pure;

    public PacketSyncSqueezerState(ByteBuf buf) {
        energy = buf.readInt();
        progress = buf.readInt();
        red = buf.readInt();
        yellow = buf.readInt();
        blue = buf.readInt();
        white = buf.readInt();
        pure = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(energy);
        buf.writeInt(progress);
        buf.writeInt(red);
        buf.writeInt(yellow);
        buf.writeInt(blue);
        buf.writeInt(white);
        buf.writeInt(pure);
    }

    public PacketSyncSqueezerState(int energy, int progress, int red, int yellow, int blue, int white, int pure) {
        this.energy = energy;
        this.progress = progress;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;
        this.pure = pure;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityPlayer entityPlayer = Woot.proxy.getClientPlayer();
            if (entityPlayer.openContainer instanceof ContainerSqueezer)
                ((ContainerSqueezer) entityPlayer.openContainer).sync(energy, progress, red, yellow, blue, white, pure);
        });
        ctx.get().setPacketHandled(true);
    }

}
