package ipsis.woot.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFixedProgressBar implements IMessage {

    public int guiId;
    public int id;
    public int data;

    public PacketFixedProgressBar() { }

    public PacketFixedProgressBar(int guiId, int id, int data) {

        this.guiId = guiId;
        this.id = id;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.guiId = buf.readInt();
        this.id = buf.readInt();
        this.data = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.guiId);
        buf.writeInt(this.id);
        buf.writeInt(this.data);
    }

    public static class Handler implements IMessageHandler<PacketFixedProgressBar, IMessage> {

        @Override
        public IMessage onMessage(PacketFixedProgressBar message, MessageContext ctx) {

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketFixedProgressBar pkt, MessageContext ctx) {

            EntityPlayerSP player = FMLClientHandler.instance().getClient().player;
            if (player != null && player.openContainer != null && player.openContainer.windowId == pkt.guiId)
                player.openContainer.updateProgressBar(pkt.id, pkt.data);
        }
    }
}
