package ipsis.woot.proxy;

import ipsis.Woot;
import ipsis.woot.client.gui.GuiProxy;
import ipsis.woot.event.HandlerLivingDropsEvent;
import ipsis.woot.handler.ConfigHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.init.ModRecipes;
import ipsis.woot.plugins.top.TOPCompat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit() {

        ModBlocks.preInit();
        ModItems.preInit();
        ModBlocks.registerTileEntities();

        MinecraftForge.EVENT_BUS.register(new HandlerLivingDropsEvent());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());

        if (Loader.isModLoaded("theoneprobe"))
            TOPCompat.register();
    }

    public void init() {

        ModRecipes.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Woot.instance, new GuiProxy());
    }

    public void postInit() {

    }

}
