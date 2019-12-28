package ipsis.woot;

import ipsis.woot.config.Config;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.layout.Layout;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.mod.*;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.setup.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
    public static final Logger LOGGER = LogManager.getLogger();
    public static ModSetup setup = new ModSetup();

    public Woot() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        SqueezerSetup.register();
        OracleSetup.register();
        FactorySetup.register();
        LayoutSetup.register();
        AnvilSetup.register();

        MinecraftForge.EVENT_BUS.register(new Registration());

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> setup.init(e));
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLClientSetupEvent e) -> setup.initClient(e));

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("woot-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("woot-common.toml"));

        // Register ourselves
        MinecraftForge.EVENT_BUS.register(this);

        ModFluids.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static ItemGroup itemGroup = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(FactorySetup.HEART_BLOCK.get());
        }
    };

    public static Item.Properties createStandardProperties() {
        return new Item.Properties().group(Woot.itemGroup);
    }

}
