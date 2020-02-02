package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.compat.top.WootTopPlugin;
import ipsis.woot.config.MobOverride;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.simulation.CustomDropsLoader;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.modules.tools.ToolsSetup;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.simulation.DropRegistry;
import ipsis.woot.mod.ModFiles;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModSetup {

    private Logger logger = LogManager.getLogger();
    private ItemGroup creativeTab;

    public ModSetup() {
        creativeTab = new ItemGroup(Woot.MODID) {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(FactorySetup.HEART_BLOCK.get());
            }
        };
    }

    public void registrySetup() {
        InfuserSetup.register();
        SqueezerSetup.register();
        OracleSetup.register();
        FactorySetup.register();
        LayoutSetup.register();
        AnvilSetup.register();
        FluidSetup.register();
        ToolsSetup.register();
        DebugSetup.register();
        GenericSetup.register();
    }

    public void commonSetup(FMLCommonSetupEvent e) {

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());

        PolicyRegistry.get().loadFromConfig();
        ModFiles.INSTANCE.init();
        NetworkChannel.init();
        PatternRepository.get().load();
        MobOverride.get().loadFromConfig();
        PolicyRegistry.get().loadFromConfig();
        FactoryConfiguration.pushToWootConfig();
        DropRegistry.get().fromJson();
        DropRegistry.get().primeAllMobLearning();
        setupPlugins();
        CustomDropsLoader.load();
    }

    public void clientSetup(FMLClientSetupEvent e) {

    }

    public void setupPlugins() {
        WootTopPlugin.init();
    }

    public Logger getLogger() { return logger; }
    public ItemGroup getCreativeTab() { return creativeTab; }
}
