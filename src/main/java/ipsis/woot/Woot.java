package ipsis.woot;

import ipsis.woot.server.command.WootCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("woot")
public class Woot {

    private static final Logger LOGGER = LogManager.getLogger();

    public Woot() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void serverStart(final FMLServerStartingEvent event) {
        new WootCommand(event.getCommandDispatcher());
    }
}
