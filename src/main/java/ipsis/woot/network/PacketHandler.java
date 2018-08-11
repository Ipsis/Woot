package ipsis.woot.network;

import ipsis.Woot;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Woot.MODID);

    private static int id = 0;
    private static int getNextId() {
        return id++;
    }

    public static void registerMessages() {

        // Service side

        // Client side
    }
}
