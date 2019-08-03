package ipsis.woot.network;

import ipsis.woot.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

public class Network {

    private static ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "net");

    public static void init(){}

    public static SimpleChannel channel;
    static {
        channel = NetworkRegistry.ChannelBuilder.named(resourceLocation)
                .clientAcceptedVersions(s -> Objects.equals(s, "1"))
                .serverAcceptedVersions(s -> Objects.equals(s, "1"))
                .networkProtocolVersion(() -> "1")
                .simpleChannel();

        channel.messageBuilder(HeartStaticDataRequest.class, 1)
                .decoder(HeartStaticDataRequest::fromBytes)
                .encoder(HeartStaticDataRequest::toByte)
                .consumer(ServerHandler::onMessage)
                .add();

        channel.messageBuilder(HeartStaticDataReply.class, 2)
                .decoder(HeartStaticDataReply::fromBytes)
                .encoder(HeartStaticDataReply::toBytes)
                .consumer(ClientHandler::onMessage)
                .add();
    }


}
