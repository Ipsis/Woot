package ipsis.woot.proxy;

import ipsis.woot.client.renderer.TESRAnvil;
import ipsis.woot.client.renderer.TESRLayout;
import ipsis.woot.event.HandlerTextureStitchEvent;
import ipsis.woot.event.ItemTooltipHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.tileentity.TileEntityAnvil;
import ipsis.woot.tileentity.TileEntityLayout;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
        MinecraftForge.EVENT_BUS.register(new HandlerTextureStitchEvent());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLayout.class, new TESRLayout());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvil.class, new TESRAnvil());
    }

    @Override
    public void init() {

        super.init();
    }

    @Override
    public void postInit() {

        super.postInit();
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {

        ModItems.initClient();
        ModBlocks.initClient();
    }
}
