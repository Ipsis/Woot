package ipsis;

import ipsis.woot.Files;
import ipsis.woot.ModBlocks;
import ipsis.woot.ModWorlds;
import ipsis.woot.configuration.FactoryCostManager;
import ipsis.woot.configuration.vanilla.GeneralConfig;
import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.drops.DropManager;
import ipsis.woot.drops.generation.LootGenerator;
import ipsis.woot.event.ServerTickEventHandler;
import ipsis.woot.factory.structure.pattern.FactoryPatternRepository;
import ipsis.woot.policy.PolicyManager;
import ipsis.woot.server.command.WootCommand;
import ipsis.woot.event.LivingDropsEventHandler;
import ipsis.woot.proxy.CommonProxy;
import ipsis.woot.util.Debug;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(modid = Woot.MODID, name = Woot.MODNAME, version = Woot.VERSION, useMetadata = true)

public class Woot {

    public static final String MODID = "woot";
    public static final String MODNAME = "Woot";
    public static final String VERSION = "0.0.1";

    public static Debug debugging = new Debug();

    @SidedProxy(clientSide = "ipsis.woot.proxy.ClientProxy", serverSide = "ipsis.woot.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tab = new CreativeTabs(Woot.MODID) {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(Item.getItemFromBlock(ModBlocks.heartBlock));
        }
    };

    @Mod.Instance
    public static Woot instance;

    public static Logger logger;

    public static final DropManager DROP_MANAGER = new DropManager();
    public static final PolicyManager POLICY_MANAGER = new PolicyManager();
    public static final FactoryPatternRepository PATTERN_REPOSITORY = new FactoryPatternRepository();
    public static final LootGenerator LOOT_GENERATOR = new LootGenerator();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);

        PATTERN_REPOSITORY.load();

        MinecraftForge.EVENT_BUS.register(new LivingDropsEventHandler());
        MinecraftForge.EVENT_BUS.register(new ServerTickEventHandler());
        ModWorlds.preInit();
        Files.init(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        LOOT_GENERATOR.initialise();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new WootCommand());

        Woot.DROP_MANAGER.init();
        Woot.POLICY_MANAGER.init();

        // TODO - not sure about this!
        TartarusManager.setWorld(event.getServer().getWorld(GeneralConfig.TARTARUS_ID));
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        Woot.DROP_MANAGER.shutdown();
        FactoryCostManager.INSTANCE.clear();
    }

    @Mod.EventHandler
    public void onFMLModIdMappingEvent(FMLModIdMappingEvent event) { ModBlocks.setupFactoryBlockMapping(); }

}
