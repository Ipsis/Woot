package ipsis.woot.network;

import ipsis.woot.Woot;
import ipsis.woot.client.ui.OracleContainer;
import ipsis.woot.factory.blocks.heart.HeartContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientHandler {

    static boolean onMessage(HeartStaticDataReply message, Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof HeartContainer) {
                    ((HeartContainer) clientPlayerEntity.openContainer).handleUIInfo(message.info);
                    ctx.get().setPacketHandled(true);
                }
            });
            return true;
        } else {
            return false;
        }
    }

    static boolean onMessage(DropRegistryStatusReply message, Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof OracleContainer) {
                    ((OracleContainer) clientPlayerEntity.openContainer).handleDropRegistryStatus(message);
                    ctx.get().setPacketHandled(true);
                }
            });
            return true;
        } else {
            return false;
        }
    }

    static boolean onMessage(SimulatedMobDropsReply message, Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof OracleContainer) {
                    ((OracleContainer) clientPlayerEntity.openContainer).handleSimulatedMobDrops(message);
                    ctx.get().setPacketHandled(true);
                }
            });
            return true;
        } else {
            return false;
        }
    }
}
