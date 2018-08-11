package ipsis;

import ipsis.woot.event.LivingDropsEventHandler;
import ipsis.woot.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Woot.MODID, name = Woot.MODNAME, version = Woot.VERSION, useMetadata = true)

public class Woot {

    public static final String MODID = "woot";
    public static final String MODNAME = "Woot";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "ipsis.woot.proxy.ClientProxy", serverSide = "ipsis.woot.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Woot instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);

        MinecraftForge.EVENT_BUS.register(new LivingDropsEventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
    }

}
