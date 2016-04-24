package ipsis.woot.proxy;

import ipsis.woot.client.renderer.TESRLayout;
import ipsis.woot.event.ItemTooltipHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.tileentity.TileEntityLayout;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();

        ModBlocks.blockFactory.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockController.initModel();
        ModBlocks.blockLayout.initModel();

        ModItems.itemPrism.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemSkull.initModel();;
        ModItems.itemFactoryFrame.initModel();
        ModItems.itemFactoryUpgrade.initModel();


        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLayout.class, new TESRLayout());
    }

    @Override
    public void init() {

        super.init();
    }

    @Override
    public void postInit() {

        super.postInit();

    }
}
