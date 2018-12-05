package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Allow sync of progress via real int data.
 * Vanilla caps via SPacketWindowProperty the data value to be a short
 */
public class PacketWindowPropertyFixed implements IMessage {

    public PacketWindowPropertyFixed() { }

    private int windowId;
    private int id;
    private int data;
    public PacketWindowPropertyFixed(int windowId, int id, int data) {
        this.windowId = windowId;
        this.id = id;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.windowId = buf.readUnsignedByte();
        this.id = buf.readShort();
        this.data = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.windowId);
        buf.writeShort(this.id);
        buf.writeInt(this.data);
    }

    public static class Handler implements IMessageHandler<PacketWindowPropertyFixed, IMessage> {

        @Override
        public IMessage onMessage(PacketWindowPropertyFixed message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketWindowPropertyFixed message, MessageContext ctx) {
            EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();
            if (player != null && player.openContainer != null && player.openContainer.windowId == message.windowId)
                player.openContainer.updateProgressBar(message.id, message.data);
        }
    }
}
