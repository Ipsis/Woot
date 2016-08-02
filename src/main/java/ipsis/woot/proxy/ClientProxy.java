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
        ModBlocks.blockUpgradeB.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockController.initModel();
        ModBlocks.blockLayout.initModel();
        ModBlocks.blockProxy.initModel();
        ModBlocks.blockExtender.initModel();

        ModItems.itemPrism.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemSkull.initModel();
        ModItems.itemShard.initModel();
        ModItems.itemDye.initModel();
        ModItems.itemFactoryFrame.initModel();
        ModItems.itemPrismFrame.initModel();

        ModItems.itemYahHammer.initModel();
        ModItems.itemPulverisedFerrocrete.initModel();
        ModItems.itemFerrocrete.initModel();

        ModItems.itemFerrocretePlate.initModel();
        ModItems.itemFactoryCasing.initModel();
        ModItems.itemFactoryUpgrade.initModel();
        ModItems.itemFactoryCap.initModel();
        ModItems.itemFactoryConnector.initModel();

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
