package ipsis.woot.network;

import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import ipsis.woot.misc.OracleTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerHandler {

    static boolean onMessage(HeartStaticDataRequest message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity sender = ctx.get().getSender();
        if (sender != null) {
            ctx.get().enqueueWork(() -> {
                TileEntity te = sender.world.getTileEntity(message.pos);
                if (te instanceof HeartTileEntity) {
                    NetworkChannel.channel.sendTo(new HeartStaticDataReply(((HeartTileEntity) te).createFactoryUIInfo()),
                            ctx.get().getSender().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                    ctx.get().setPacketHandled(true);
                }
            });
            return true;
        } else {
            return false;
        }
    }

    static boolean onMessage(ServerDataRequest message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity sender = ctx.get().getSender();
        if (sender != null) {
            ctx.get().enqueueWork(() -> {
                TileEntity te = ctx.get().getSender().world.getTileEntity(message.pos);
                if (message.requestType == ServerDataRequest.Type.DROP_REGISTRY_STATUS) {
                    if (te instanceof OracleTileEntity) {
                        NetworkChannel.channel.sendTo(new DropRegistryStatusReply(),
                                ctx.get().getSender().connection.netManager,
                                NetworkDirection.PLAY_TO_CLIENT);
                        ctx.get().setPacketHandled(true);
                    }
                } else if (message.requestType == ServerDataRequest.Type.SIMULATED_MOB_DROPS) {
                    if (te instanceof OracleTileEntity) {
                        NetworkChannel.channel.sendTo(new SimulatedMobDropsReply(message.s),
                                ctx.get().getSender().connection.netManager,
                                NetworkDirection.PLAY_TO_CLIENT);
                        ctx.get().setPacketHandled(true);
                    }
                } else if (message.requestType == ServerDataRequest.Type.HEART_STATIC_DATA) {
                    if (te instanceof HeartTileEntity) {
                        NetworkChannel.channel.sendTo(new HeartStaticDataReply(((HeartTileEntity) te).createFactoryUIInfo()),
                                ctx.get().getSender().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                        ctx.get().setPacketHandled(true);
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }
}
