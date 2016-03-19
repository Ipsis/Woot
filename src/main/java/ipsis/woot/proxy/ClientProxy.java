package ipsis.woot.proxy;

import ipsis.woot.event.ItemTooltipHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();

        ModBlocks.blockFactory.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockController.initModel();

        ModItems.itemPrism.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemSkull.initModel();;
        ModItems.itemFactoryFrame.initModel();
        ModItems.itemFactoryUpgrade.initModel();

        ModBlocks.registerTileEntities();

        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
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
