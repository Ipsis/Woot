package ipsis.woot.network;

import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import ipsis.woot.misc.OracleTileEntity;
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

    static void onMessage(ServerDataRequest message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntity te = ctx.get().getSender().world.getTileEntity(message.pos);
            if (message.requestType == ServerDataRequest.Type.DROP_REGISTRY_STATUS) {
                if (te instanceof OracleTileEntity) {
                    Network.channel.sendTo(new DropRegistryStatusReply(),
                            ctx.get().getSender().connection.netManager,
                            NetworkDirection.PLAY_TO_CLIENT);
                    ctx.get().setPacketHandled(true);
                }
            } else if (message.requestType == ServerDataRequest.Type.SIMULATED_MOB_DROPS) {
                if (te instanceof OracleTileEntity) {
                    Network.channel.sendTo(new SimulatedMobDropsReply(message.s),
                            ctx.get().getSender().connection.netManager,
                            NetworkDirection.PLAY_TO_CLIENT);
                    ctx.get().setPacketHandled(true);
                }
            } else if (message.requestType == ServerDataRequest.Type.HEART_STATIC_DATA) {
                if (te instanceof HeartTileEntity) {
                    Network.channel.sendTo(new HeartStaticDataReply(((HeartTileEntity) te).createFactoryUIInfo()),
                            ctx.get().getSender().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                    ctx.get().setPacketHandled(true);
                }
            }
        });
    }
}
