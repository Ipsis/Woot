package ipsis.woot;

import ipsis.woot.factory.layout.FactoryPatternRepository;
import ipsis.woot.layout.TileEntityLayout;
import ipsis.woot.layout.TileEntitySpecialRendererLayout;
import ipsis.woot.mod.ModItems;
import ipsis.woot.proxy.ClientProxy;
import ipsis.woot.proxy.IProxy;
import ipsis.woot.proxy.ServerProxy;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);

        FactoryPatternRepository.getInstance().load();
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.setup(event);
    }

    private void client(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLayout.class, new TileEntitySpecialRendererLayout());
    }


}
