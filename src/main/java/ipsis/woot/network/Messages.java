package ipsis.woot.network;

import ipsis.woot.Woot;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Messages {

    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    private static int nextID() { return ID++; }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Woot.MODID, channelName),
                () -> "1.0",
                s -> true,
                s -> true);

        /**
         * Server side
         */

        /**
         * Client side
         */
        INSTANCE.registerMessage(nextID(),
                PacketSyncSqueezerState.class,
                PacketSyncSqueezerState::toBytes,
                PacketSyncSqueezerState::new,
                PacketSyncSqueezerState::handle);
    }
}
