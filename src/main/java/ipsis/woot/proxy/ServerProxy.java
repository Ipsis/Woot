package ipsis.woot.proxy;

import ipsis.Woot;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ServerProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        Woot.DROP_MANAGER.init();
    }
}
