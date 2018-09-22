package ipsis;

import ipsis.woot.Files;
import ipsis.woot.ModBlocks;
import ipsis.woot.ModWorlds;
import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.drops.DropManager;
import ipsis.woot.event.ServerTickEventHandler;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(ModBlocks.heartBlock));
        }
    };

    @Mod.Instance
    public static Woot instance;

    public static Logger logger;

    public static DropManager DROP_MANAGER = new DropManager();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);

        MinecraftForge.EVENT_BUS.register(new LivingDropsEventHandler());
        MinecraftForge.EVENT_BUS.register(new ServerTickEventHandler());
        ModWorlds.preInit();
        Files.init(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new WootCommand());

        // TODO - not sure about this!
        TartarusManager.setWorld(event.getServer().getWorld(ModWorlds.tartarus_id));
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {

        Woot.DROP_MANAGER.shutdown();
    }

}
