package ipsis;

import ipsis.woot.command.CommandWoot;
import ipsis.woot.handler.ConfigHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModOreDictionary;
import ipsis.woot.manager.*;
import ipsis.woot.manager.loot.LootTable;
import ipsis.woot.manager.loot.LootTableManager;
import ipsis.woot.proxy.CommonProxy;
import ipsis.woot.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Woot {

    @Mod.Instance(Reference.MOD_ID)
    public static Woot instance;
    public static SpawnerManager spawnerManager = new SpawnerManager();
    public static MobRegistry mobRegistry = new MobRegistry();
    public static HeadRegistry headRegistry = new HeadRegistry();
    public static Random RANDOM = new Random();
    public static TierMapper tierMapper = new TierMapper();
    public static LootTableManager LOOT_TABLE_MANAGER = new LootTableManager();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs tabWoot = new CreativeTabs(Reference.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.blockFactory);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit();

        ConfigHandler.init(event.getSuggestedConfigurationFile());
        UpgradeManager.loadConfig();

        FMLInterModComms.sendMessage("Waila", "register", "ipsis.woot.plugins.waila.WailaDataProviderWoot.callbackRegister");

        ModOreDictionary.preInit();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
        headRegistry.init();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {

        event.registerServerCommand(new CommandWoot());
    }
}
