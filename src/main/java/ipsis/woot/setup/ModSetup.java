package ipsis.woot.setup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
//import ipsis.woot.compat.top.WootTopPlugin;
import ipsis.woot.advancements.Advancements;
import ipsis.woot.compat.top.WootTopPlugin;
import ipsis.woot.config.OverrideLoader;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.generators.LootGeneration;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.modules.tools.ToolsSetup;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.mod.ModFiles;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.MobSimulatorSetup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;

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
        FluidConvertorSetup.register();
    }

    public void commonSetup(FMLCommonSetupEvent e) {

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());

        Advancements.init();
        PolicyRegistry.get().loadFromConfig();
        ModFiles.INSTANCE.init();
        NetworkChannel.init();
        PatternRepository.get().load();
        OverrideLoader.loadFromConfig();
        PolicyRegistry.get().loadFromConfig();
        MobSimulatorSetup.init();
        LootGeneration.get().loadFromConfig();

        File dropFile = ModFiles.INSTANCE.getLootFile();
        Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
            JsonObject jsonObject = JSONUtils.fromJson(GSON, new FileReader(dropFile), JsonObject.class);
            MobSimulator.getInstance().fromJson(jsonObject);
        } catch (Exception exception) {
            Woot.setup.getLogger().warn("Failed to load loot file {}", dropFile.getAbsolutePath());
        }
        setupPlugins();
    }

    public void clientSetup(FMLClientSetupEvent e) {

    }

    public void setupPlugins() {
        WootTopPlugin.init();
    }

    public Logger getLogger() { return logger; }
    public ItemGroup getCreativeTab() { return creativeTab; }
}
