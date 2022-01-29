package ipsis.woot;

import ipsis.woot.modules.Modules;
import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.modules.squeezer.SqueezerModule;
import ipsis.woot.setup.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Woot.MODID)
public class Woot {

    public static final String MODID = "woot";
    public static ModSetup modSetup = new ModSetup();
    private Modules modules = new Modules();
    private ClientInfo clientInfo = new ClientInfo();
    public Modules getModules() { return this.modules; }
    public ClientInfo getClientInfo() { return this.clientInfo; }
    public static Woot instance;

    public Woot() {

        instance = this;
        setupModules();

        Config.register(modules);
        Registration.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(modSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }

    private void setupModules() {

        modules.register(new FactoryModule());
        modules.register(new SqueezerModule());
    }
}
