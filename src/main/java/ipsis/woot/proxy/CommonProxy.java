package ipsis.woot.proxy;

import ipsis.Woot;
import ipsis.woot.Config;
import ipsis.woot.ModBlocks;
import ipsis.woot.blocks.BlockHeart;
import ipsis.woot.blocks.BlockLayout;
import ipsis.woot.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber(modid = Woot.MODID)
public class CommonProxy {
    public static Configuration config;


    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages();
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "woot.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        event.getRegistry().register(new BlockLayout());
        event.getRegistry().register(new BlockHeart());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        event.getRegistry().register(new ItemBlock(ModBlocks.layoutBlock).setRegistryName(ModBlocks.layoutBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.heartBlock).setRegistryName(ModBlocks.heartBlock.getRegistryName()));

    }
}
