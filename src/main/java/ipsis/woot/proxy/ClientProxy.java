package ipsis.woot.proxy;

import ipsis.woot.Woot;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy{

    /**
     * IProxy
     */
    @Override
    public void setup(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Woot.LOGGER.info("Register models");
    }

}
