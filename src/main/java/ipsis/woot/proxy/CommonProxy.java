package ipsis.woot.proxy;

import ipsis.woot.event.HandlerLivingDropsEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void preInit() {

        registerBlockItemModels();
        registerItemRenderers();
        registerEventHandlers();
    }

    public void init() {
    }
    public void postInit() {
    }

    protected void registerBlockItemModels() { }
    protected void registerItemRenderers() { }

    protected void registerEventHandlers() {

        MinecraftForge.EVENT_BUS.register(new HandlerLivingDropsEvent());
    }

}
