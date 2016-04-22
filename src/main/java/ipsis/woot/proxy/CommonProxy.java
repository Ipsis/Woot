package ipsis.woot.proxy;

import ipsis.woot.event.HandlerLivingDropsEvent;
import ipsis.woot.handler.ConfigHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.init.ModRecipes;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void preInit() {

        ModBlocks.preInit();
        ModItems.preInit();
        ModBlocks.registerTileEntities();

        MinecraftForge.EVENT_BUS.register(new HandlerLivingDropsEvent());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }

    public void init() {

        ModRecipes.init();
    }

    public void postInit() {

    }

}
