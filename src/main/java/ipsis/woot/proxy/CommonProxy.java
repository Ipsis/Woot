package ipsis.woot.proxy;

import ipsis.Woot;
import ipsis.woot.client.gui.GuiProxy;
import ipsis.woot.event.HandlerLivingDeathEvent;
import ipsis.woot.event.HandlerLivingDropsEvent;
import ipsis.woot.handler.ConfigHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.plugins.top.TOPCompat;
import ipsis.woot.reference.Files;
import ipsis.woot.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit() {

        ModBlocks.preInit();
        ModItems.preInit();
        ModBlocks.registerTileEntities();
        PacketHandler.registerMessages(Reference.MOD_ID);

        MinecraftForge.EVENT_BUS.register(new HandlerLivingDropsEvent());
        MinecraftForge.EVENT_BUS.register(new HandlerLivingDeathEvent());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());

        ConfigHandler.init(Files.configFile);

        if (Loader.isModLoaded("theoneprobe"))
            TOPCompat.register();

        Woot.wootDimensionManager.init();
    }

    public void init() {

        NetworkRegistry.INSTANCE.registerGuiHandler(Woot.instance, new GuiProxy());
    }

    public void postInit() {

    }

}
