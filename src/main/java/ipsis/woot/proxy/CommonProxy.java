package ipsis.woot.proxy;

import ipsis.woot.event.HandlerLivingDropsEvent;
import ipsis.woot.handler.ConfigHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void preInit() {

        registerBlockItemModels();
        registerEventHandlers();
    }

    public void init() {
    }
    public void postInit() {
        registerItemRenderers();
    }

    protected void registerBlockItemModels() { }
    protected void registerItemRenderers() { }

    protected void registerEventHandlers() {

        MinecraftForge.EVENT_BUS.register(new HandlerLivingDropsEvent());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }

}
