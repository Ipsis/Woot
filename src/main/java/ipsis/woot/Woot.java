package ipsis.woot;

import ipsis.woot.common.WootConfig;
import ipsis.woot.server.TickEventHandler;
import ipsis.woot.server.command.WootCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("woot")
public class Woot {

    public static final Logger LOGGER = LogManager.getLogger();

    public Woot() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WootConfig.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, WootConfig.serverSpec);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new TickEventHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        new WootCommand(event.getCommandDispatcher());
    }
}
