package ipsis.woot.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class WootConfig {

    public static class Server {

        public final IntValue simulationTicks;
        public final IntValue simulationMobCount;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server configuration settings")
                    .push("server");

            simulationTicks = builder
                    .comment("Number of ticks between mob simulations")
                    .translation("woot.configgui.simulationTicks")
                    .defineInRange("simulationTicks", 200, 20, 20 * 60);

            simulationMobCount = builder
                    .comment("Number of simulated mobs to learn from")
                    .translation("woot.configgui.simulationMobCount")
                    .defineInRange("simulationMobCount", 500,100, 5000);

            builder.pop();
        }
    }

    public static class Client {

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            builder.pop();
        }
    }

    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
    }
}
