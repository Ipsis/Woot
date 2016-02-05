package ipsis;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.init.ModRecipes;
import ipsis.woot.manager.SpawnerManager2;
import ipsis.woot.proxy.CommonProxy;
import ipsis.woot.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Woot {

    @Mod.Instance(Reference.MOD_ID)
    public static Woot instance;
    public static SpawnerManager2 spawnerManager2 = new SpawnerManager2();

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

        ModBlocks.init();
        ModItems.init();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {

        ModRecipes.init();
        ModBlocks.registerTileEntities();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
    }
}
