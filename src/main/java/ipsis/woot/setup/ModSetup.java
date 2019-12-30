package ipsis.woot.setup;

import ipsis.woot.config.MobOverride;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.infuser.InfuserRecipes;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.simulation.DropRegistry;
import ipsis.woot.modules.anvil.AnvilCraftingManagerLoader;
import ipsis.woot.mod.ModFiles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public void init(FMLCommonSetupEvent e) {

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
        AnvilCraftingManagerLoader.load();
        InfuserRecipes.load();
    }

    public void initClient(FMLClientSetupEvent e) {

    }
}
