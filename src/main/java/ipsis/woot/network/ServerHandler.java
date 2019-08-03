package ipsis.woot.network;

import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerHandler {

    static void onMessage(HeartStaticDataRequest message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntity te = ctx.get().getSender().world.getTileEntity(message.pos);
            if (te instanceof HeartTileEntity) {
                Network.channel.sendTo(new HeartStaticDataReply(((HeartTileEntity) te).createFactoryUIInfo()),
                        ctx.get().getSender().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                ctx.get().setPacketHandled(true);
            }
        });
    }
}
