package ipsis.woot;

import ipsis.woot.config.Config;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.modules.tools.ToolsSetup;
import ipsis.woot.setup.*;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("woot")
public class Woot {

    public static final String MODID = "woot";
    public static ModSetup setup = new ModSetup();
    public static final IProxy proxy = DistExecutor.runForDist(
            () -> () -> new ClientProxy(),
            () -> () -> new ServerProxy()
    );

    public Woot() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        setup.registrySetup();

        MinecraftForge.EVENT_BUS.register(new Registration());

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> setup.commonSetup(e));
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLClientSetupEvent e) -> setup.clientSetup(e));

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("woot-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("woot-common.toml"));
    }

    public static Item.Properties createStandardProperties() {
        return new Item.Properties().tab(setup.getCreativeTab());
    }

}
