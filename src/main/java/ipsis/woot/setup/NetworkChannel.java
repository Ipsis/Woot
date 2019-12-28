package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.network.HeartStaticDataReply;
import ipsis.woot.modules.oracle.network.DropRegistryStatusReply;
import ipsis.woot.modules.oracle.network.SimulatedMobDropsReply;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

public class NetworkChannel {

    private static ResourceLocation resourceLocation = new ResourceLocation(Woot.MODID, "net");

    public static void init(){}

    public static SimpleChannel channel;
    static {
        channel = NetworkRegistry.ChannelBuilder.named(resourceLocation)
                .clientAcceptedVersions(s -> Objects.equals(s, "1"))
                .serverAcceptedVersions(s -> Objects.equals(s, "1"))
                .networkProtocolVersion(() -> "1")
                .simpleChannel();

        channel.registerMessage(
                1,
                ServerDataRequest.class,
                ServerDataRequest::toByte, ServerDataRequest::fromBytes,
                ServerDataRequest::handle);

        channel.registerMessage(
                2,
                HeartStaticDataReply.class,
                HeartStaticDataReply::toBytes, HeartStaticDataReply::fromBytes, HeartStaticDataReply::handle);

        channel.registerMessage(
                3,
                DropRegistryStatusReply.class,
                DropRegistryStatusReply::toBytes, DropRegistryStatusReply::fromBytes, DropRegistryStatusReply::handle);

        channel.registerMessage(
                4,
                SimulatedMobDropsReply.class,
                SimulatedMobDropsReply::toBytes, SimulatedMobDropsReply::fromBytes, SimulatedMobDropsReply::handle);
    }
}
