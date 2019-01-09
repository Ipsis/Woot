package ipsis.woot.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper INSTANCE;

    private static int id = 0;
    private static int getNextId() {
        return id++;
    }

    public static void registerMessages(String channelName) {

        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        // Service -> Client
        INSTANCE.registerMessage(PacketSyncStaticHeartData.Handler.class, PacketSyncStaticHeartData.class, getNextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketWindowPropertyFixed.Handler.class, PacketWindowPropertyFixed.class, getNextId(), Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncSqueezerData.Handler.class, PacketSyncSqueezerData.class, getNextId(), Side.CLIENT);

        // Client -> Server
        INSTANCE.registerMessage(PacketRequestServerData.Handler.class, PacketRequestServerData.class, getNextId(), Side.SERVER);
    }
}
