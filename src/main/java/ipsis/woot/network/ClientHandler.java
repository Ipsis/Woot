package ipsis.woot.network;

import ipsis.woot.Woot;
import ipsis.woot.client.ui.OracleContainer;
import ipsis.woot.factory.blocks.heart.HeartContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientHandler {

    static void onMessage(HeartStaticDataReply message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
            if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof HeartContainer) {
                Woot.LOGGER.info("onMessage: received heart reply");
                ((HeartContainer) clientPlayerEntity.openContainer).handleUIInfo(message.info);
                ctx.get().setPacketHandled(true);
            }
        });
    }

    static void onMessage(DropRegistryStatusReply message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
            if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof OracleContainer) {
                Woot.LOGGER.info("onMessage: received drop registry reply");
                ((OracleContainer) clientPlayerEntity.openContainer).handleDropRegistryStatus(message);
                ctx.get().setPacketHandled(true);
            }
        });
    }

    static void onMessage(SimulatedMobDropsReply message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
            if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof OracleContainer) {
                Woot.LOGGER.info("onMessage: received drops");
                ((OracleContainer) clientPlayerEntity.openContainer).handleSimulatedMobDrops(message);
                ctx.get().setPacketHandled(true);
            }
        });
    }
}
