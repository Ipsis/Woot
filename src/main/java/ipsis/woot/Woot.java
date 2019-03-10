package ipsis.woot;

import ipsis.woot.config.Config;
import ipsis.woot.event.LivingDeathEventHandler;
import ipsis.woot.event.LivingDropsEventHandler;
import ipsis.woot.factory.layout.FactoryPatternRepository;
import ipsis.woot.layout.TileEntityLayout;
import ipsis.woot.layout.TileEntitySpecialRendererLayout;
import ipsis.woot.machines.squeezer.SqueezerRegistry;
import ipsis.woot.mod.ModItems;
import ipsis.woot.network.Messages;
import ipsis.woot.proxy.ClientProxy;
import ipsis.woot.client.GuiHandler;
import ipsis.woot.proxy.IProxy;
import ipsis.woot.proxy.ServerProxy;
import ipsis.woot.server.command.WootCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("woot")
public class Woot {

    public static Woot INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "woot";
    //public static final String MODNAME = "Woot";
    //public static final String VERSION = "0.0.1";

    public static IProxy proxy = DistExecutor.runForDist(()->()->new ClientProxy(), ()->()->new ServerProxy());

    /**
     * Creative Tab
     */
    public static ItemGroup TAB_WOOT = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.debugItem);
        }
    };

    public Woot() {
        INSTANCE = this;

        FluidRegistry.enableUniversalBucket();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new LivingDropsEventHandler());
        MinecraftForge.EVENT_BUS.register(new LivingDeathEventHandler());

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::getClientGuiElement);

        Config.load(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("woot-client.toml"));
        Config.load(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("woot-server.toml"));

        FactoryPatternRepository.getInstance().load();
    }

    @SubscribeEvent
    public void serverStart(final FMLServerStartingEvent event) {
        new WootCommand(event.getCommandDispatcher());
    }

    private void setup(final FMLCommonSetupEvent event) {

        Messages.registerMessages("woot");
        proxy.setup(event);
        SqueezerRegistry.INSTANCE.loadRecipes();
    }

    private void client(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLayout.class, new TileEntitySpecialRendererLayout());
    }


}
