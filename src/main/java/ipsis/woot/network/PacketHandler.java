package ipsis.woot.network;

import ipsis.woot.network.packets.PacketFarmInfo;
import ipsis.woot.network.packets.PacketFixedProgressBar;
import ipsis.woot.network.packets.PacketGetFarmInfo;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    private static int packetId = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int getNextId() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {

        INSTANCE.registerMessage(PacketFarmInfo.Handler.class, PacketFarmInfo.class, getNextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketGetFarmInfo.Handler.class, PacketGetFarmInfo.class, getNextId(), Side.SERVER);
        INSTANCE.registerMessage(PacketFixedProgressBar.Handler.class, PacketFixedProgressBar.class, getNextId(), Side.CLIENT);
    }
}
