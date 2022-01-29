package ipsis.woot.setup;

import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.modules.factory.client.LayoutSpecialRenderer;
import ipsis.woot.modules.squeezer.SqueezerModule;
import ipsis.woot.modules.squeezer.client.DyeSqueezerGUI;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(FMLClientSetupEvent e) {

        RenderTypeLookup.setRenderLayer(FactoryModule.BASE_GLASS.get(), RenderType.cutoutMipped());
        ClientRegistry.bindTileEntityRenderer(FactoryModule.LAYOUT_TE.get(), LayoutSpecialRenderer::new);

        ScreenManager.register(SqueezerModule.DYE_SQUEEZER_CONTAINER.get(), DyeSqueezerGUI::new);
    }
}
