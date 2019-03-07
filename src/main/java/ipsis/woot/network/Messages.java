package ipsis.woot.network;

import ipsis.woot.Woot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Messages {

    public static SimpleChannel INSTANCE;

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Woot.MODID, channelName),
                () -> ".0",
                s -> true,
                s-> true);
    }
}
