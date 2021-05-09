package ipsis.woot;

import ipsis.woot.modules.Modules;
import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.setup.ClientSetup;
import ipsis.woot.setup.Config;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.setup.Registration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Woot.MODID)
public class Woot {

    public static final String MODID = "woot";
    public static ModSetup modSetup = new ModSetup();
    private Modules modules = new Modules();
    public Modules getModules() { return this.modules; }
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
    }
}
