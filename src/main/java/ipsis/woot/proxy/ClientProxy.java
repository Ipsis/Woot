package ipsis.woot.proxy;

import ipsis.woot.init.ModBlocks;

public class ClientProxy extends CommonProxy {

    @Override
    protected void registerBlockItemModels() {

        ModBlocks.blockFactory.initModel();
    }

    @Override
    protected void registerItemRenderers() {

    }
}
