package ipsis;

import ipsis.woot.command.CommandWoot;
import ipsis.woot.handler.ConfigHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModEnchantments;
import ipsis.woot.init.ModOreDictionary;
import ipsis.woot.manager.*;
import ipsis.woot.manager.loot.LootTableManager;
import ipsis.woot.manager.spawnreq.SpawnReqManager;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.plugins.bloodmagic.BloodMagic;
import ipsis.woot.plugins.imc.EnderIO;
import ipsis.woot.proxy.CommonProxy;
import ipsis.woot.reference.Files;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.ng.IWootConfiguration;
import ipsis.woot.tileentity.ng.WootConfiguration;
import ipsis.woot.tileentity.ng.loot.*;
import ipsis.woot.tileentity.ng.loot.schools.SkyBoxSchool;
import ipsis.woot.util.WootMobName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
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
    public static SpawnReqManager SPAWN_REQ_MANAGER = new SpawnReqManager();
    public static boolean devMode = false;
    public static IWootConfiguration wootConfiguration = new WootConfiguration();
    public static ILootGeneration lootGeneration = new LootGeneration();
    public static ILootLearner lootLearner = new SkyBoxSchool();
    public static ILootRepository lootRepository = new LootRepository();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs tabWoot = new CreativeTabs(Reference.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(ModBlocks.blockFactory));
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit();
        Files.init(event);

        ConfigHandler.init(Files.configFile);
        UpgradeManager.loadConfig();

        FMLInterModComms.sendMessage("Waila", "register", "ipsis.woot.plugins.waila.WailaDataProviderWoot.callbackRegister");
        EnderIO.loadRecipes();

        ModOreDictionary.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init();

        if (Loader.isModLoaded(BloodMagic.BM_MODID))
            BloodMagic.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
        headRegistry.init();
        ModEnchantments.postInit();
        LOOT_TABLE_MANAGER.loadInternalBlacklist();
        LOOT_TABLE_MANAGER.loadDragonDrops();
        SPAWN_REQ_MANAGER.loadFromJson();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {

        LOOT_TABLE_MANAGER.load();
        event.registerServerCommand(new CommandWoot());
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {

        LOOT_TABLE_MANAGER.save();
    }
}
