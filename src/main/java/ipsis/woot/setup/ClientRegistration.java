package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.client.AnvilTileEntitySpecialRenderer;
import ipsis.woot.modules.layout.client.LayoutTileEntitySpecialRenderer;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.oracle.client.OracleScreen;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.factory.client.HeartScreen;
import ipsis.woot.modules.anvil.blocks.AnvilTileEntity;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.modules.squeezer.client.SqueezerScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Woot.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Woot.LOGGER.debug("FMLClientSetupEvent");
        ClientRegistry.bindTileEntitySpecialRenderer(LayoutTileEntity.class, new LayoutTileEntitySpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(AnvilTileEntity.class, new AnvilTileEntitySpecialRenderer());
        ScreenManager.registerFactory(FactorySetup.HEART_BLOCK_CONTAINER.get(), HeartScreen::new);
        ScreenManager.registerFactory(OracleSetup.ORACLE_BLOCK_CONTAINER.get(), OracleScreen::new);
        ScreenManager.registerFactory(SqueezerSetup.SQUEEZER_BLOCK_CONTAINER.get(), SqueezerScreen::new);
    }
}
