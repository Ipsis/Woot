package ipsis.woot;

import ipsis.woot.client.AnvilTileEntitySpecialRenderer;
import ipsis.woot.client.LayoutTileEntitySpecialRenderer;
import ipsis.woot.client.ui.OracleScreen;
import ipsis.woot.client.ui.SqueezerScreen;
import ipsis.woot.common.configuration.Config;
import ipsis.woot.common.configuration.Policy;
import ipsis.woot.factory.blocks.LayoutTileEntity;
import ipsis.woot.factory.blocks.heart.HeartScreen;
import ipsis.woot.factory.layout.PatternRepository;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.misc.anvil.AnvilCraftingManagerLoader;
import ipsis.woot.misc.anvil.AnvilTileEntity;
import ipsis.woot.misc.squeezer.SqueezerRegistry;
import ipsis.woot.mod.*;
import ipsis.woot.network.NetworkChannel;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("woot")
public class Woot {

    public static final String MODID = "woot";
    public static final Logger LOGGER = LogManager.getLogger();

    public Woot() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModEvents::onFileChange);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);

        MinecraftForge.EVENT_BUS.register(new Registration());
        MinecraftForge.EVENT_BUS.register(new ModEvents());

        // Register ourselves
        MinecraftForge.EVENT_BUS.register(this);

        ModFluids.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.debug("FMLCommonSetupEvent");
        Policy.get().loadFromConfig();
        ModFiles.INSTANCE.init();
        NetworkChannel.init();
        PatternRepository.get().load();
        Config.loadFromConfig();
        DropRegistry.get().fromJson();
        DropRegistry.get().primeAllMobLearning();
        AnvilCraftingManagerLoader.load();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.debug("FMLClientSetupEvent");
        ClientRegistry.bindTileEntitySpecialRenderer(LayoutTileEntity.class, new LayoutTileEntitySpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(AnvilTileEntity.class, new AnvilTileEntitySpecialRenderer());
        ScreenManager.registerFactory(ModBlocks.HEART_CONTAINER, HeartScreen::new);
        ScreenManager.registerFactory(ModBlocks.ORACLE_CONTAINER, OracleScreen::new);
        ScreenManager.registerFactory(ModBlocks.SQUEEZER_CONTAINER, SqueezerScreen::new);
    }

    public static ItemGroup itemGroup = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.CONTROLLER_BLOCK);
        }
    };

}
