package ipsis;

import ipsis.woot.command.WootCommand;
import ipsis.woot.configuration.*;
import ipsis.woot.configuration.loaders.*;
import ipsis.woot.crafting.AnvilManager;
import ipsis.woot.crafting.AnvilManagerLoader;
import ipsis.woot.crafting.IAnvilManager;
import ipsis.woot.dimension.WootDimensionManager;
import ipsis.woot.event.HandlerRegistryEvent;
import ipsis.woot.farming.ISpawnRecipeRepository;
import ipsis.woot.farming.SpawnRecipeRepository;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModFurnace;
import ipsis.woot.init.ModOreDictionary;
import ipsis.woot.loot.*;
import ipsis.woot.loot.customdrops.CustomDropsRepository;
import ipsis.woot.loot.repository.LootRepository;
import ipsis.woot.loot.schools.TartarusManager;
import ipsis.woot.multiblock.FactoryPatternRepository;
import ipsis.woot.plugins.bloodmagic.BloodMagic;
import ipsis.woot.plugins.evilcraft.EvilCraft;
import ipsis.woot.plugins.enderio.EnderIO;
import ipsis.woot.plugins.thauncraft.Thaumcraft;
import ipsis.woot.plugins.thermal.Thermal;
import ipsis.woot.policy.IPolicy;
import ipsis.woot.policy.InternalPolicyLoader;
import ipsis.woot.policy.PolicyRepository;
import ipsis.woot.power.calculation.upgrades.CalculatorRepository;
import ipsis.woot.proxy.CommonProxy;
import ipsis.woot.reference.Files;
import ipsis.woot.reference.Reference;
import ipsis.woot.spawning.EntitySpawner;
import ipsis.woot.spawning.IEntitySpawner;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.SkullHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
    public static Random RANDOM = new Random();
    public static IWootConfiguration wootConfiguration = new WootConfigurationManager();
    public static ILootGeneration lootGeneration = new LootGeneration();
    public static LootRepository lootRepository = new LootRepository();
    public static CustomDropsRepository customDropsRepository= new CustomDropsRepository();
    public static IMobCost mobCosting = new MobHealthManager();
    public static IEntitySpawner entitySpawner = new EntitySpawner();
    public static DebugSetup debugSetup = new DebugSetup();
    public static IAnvilManager anvilManager = new AnvilManager();
    public static IPolicy policyRepository = new PolicyRepository();
    public static ISpawnRecipeRepository spawnRecipeRepository = new SpawnRecipeRepository();
    public static TartarusManager tartarusManager = new TartarusManager();
    public static WootDimensionManager wootDimensionManager = new WootDimensionManager();
    public static FactoryPatternRepository factoryPatternRepository = new FactoryPatternRepository();
    public static CalculatorRepository calculatorRepository = new CalculatorRepository();
    public static ChangeLog changeLog = new ChangeLog();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs tabWoot = new CreativeTabs(Reference.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(ModBlocks.blockFactoryHeart));
        }
    };

    public Woot() {

        MinecraftForge.EVENT_BUS.register(new HandlerRegistryEvent());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Files.init(event);
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        // From Forge docs - RegistryEvents are fired after Pre-Initialization
        // So item/blocks are only available now

        proxy.init();

        new InternalPolicyLoader().load(policyRepository);
        new ChangelogLoader().load();
        new FactoryConfigLoader().loadConfig(wootConfiguration);
        new FactoryIngredientsLoader().loadConfig();
        new CustomDropsLoader().loadConfig();

        ModFurnace.init();
        lootGeneration.initialise();
        AnvilManagerLoader.load();
        EnderIO.loadRecipes();
        ModOreDictionary.init();

        FMLInterModComms.sendMessage("Waila", "register", "ipsis.woot.plugins.waila.WailaDataProviderWoot.callbackRegister");

        if (Loader.isModLoaded(BloodMagic.BM_MODID))
            BloodMagic.init();

        if (Loader.isModLoaded(EvilCraft.EC_MODID))
            EvilCraft.init();

        if (Loader.isModLoaded(Thermal.THERMAL_EXPANSION_MODID))
            Thermal.init();

        if (Loader.isModLoaded(Thaumcraft.THAUMCRAFT_MODID))
            Thaumcraft.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
        SkullHelper.postInit();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {

        new FactoryLootLoader().loadConfig(lootRepository);
        event.registerServerCommand(new WootCommand());
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {

        lootRepository.writeToJsonFile(Files.lootFile);
    }
}
