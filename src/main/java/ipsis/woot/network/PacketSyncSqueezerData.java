package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.machines.squeezer.ContainerSqueezer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncSqueezerData implements IMessage {

    public PacketSyncSqueezerData() { }

    private int progress;
    private int red;
    private int blue;
    private int white;
    private int yellow;
    private int pure;

    public PacketSyncSqueezerData(int progress, int blue, int pure, int red, int white, int yellow) {
        this.progress = progress;
        this.red = red;
        this.blue = blue;
        this.white = white;
        this.yellow = yellow;
        this.pure = pure;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.progress = byteBuf.readInt();
        this.blue = byteBuf.readInt();
        this.pure = byteBuf.readInt();
        this.red = byteBuf.readInt();
        this.white = byteBuf.readInt();
        this.yellow = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.progress);
        byteBuf.writeInt(this.blue);
        byteBuf.writeInt(this.pure);
        byteBuf.writeInt(this.red);
        byteBuf.writeInt(this.white);
        byteBuf.writeInt(this.yellow);
    }

    public static class Handler implements IMessageHandler<PacketSyncSqueezerData, IMessage> {

        @Override
        public IMessage onMessage(PacketSyncSqueezerData packetSyncSqueezerData, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetSyncSqueezerData, messageContext));
            return null;
        }

        private void handle(PacketSyncSqueezerData message, MessageContext ctx) {
            EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();
            if (player != null && player.openContainer instanceof ContainerSqueezer)
                ((ContainerSqueezer)player.openContainer).sync(message.progress, message.blue, message.pure, message.red, message.white, message.yellow);
        }
    }
}
