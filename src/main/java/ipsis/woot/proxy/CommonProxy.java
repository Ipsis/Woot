package ipsis.woot.proxy;

import ipsis.Woot;
import ipsis.woot.Config;
import ipsis.woot.ModBlocks;
import ipsis.woot.blocks.*;
import ipsis.woot.items.ItemEnderShard;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.util.FactoryBlock;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

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
        Woot.DROP_MANAGER.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        event.getRegistry().register(new BlockLayout());
        event.getRegistry().register(new BlockHeart());
        event.getRegistry().register(new BlockController());

        event.getRegistry().register(new BlockStructure(FactoryBlock.BONE));
        event.getRegistry().register(new BlockStructure(FactoryBlock.FLESH));
        event.getRegistry().register(new BlockStructure(FactoryBlock.BLAZE));
        event.getRegistry().register(new BlockStructure(FactoryBlock.ENDER));
        event.getRegistry().register(new BlockStructure(FactoryBlock.NETHER));
        event.getRegistry().register(new BlockStructure(FactoryBlock.REDSTONE));
        event.getRegistry().register(new BlockStructure(FactoryBlock.UPGRADE));
        event.getRegistry().register(new BlockStructure(FactoryBlock.CAP_1));
        event.getRegistry().register(new BlockStructure(FactoryBlock.CAP_2));
        event.getRegistry().register(new BlockStructure(FactoryBlock.CAP_3));
        event.getRegistry().register(new BlockStructure(FactoryBlock.CAP_4));

        // TODO different event for TEs in 1.13 ?
        GameRegistry.registerTileEntity(TileEntityHeart.class, Woot.MODID + ":heart");
        GameRegistry.registerTileEntity(TileEntityController.class, Woot.MODID + ":controller");
        GameRegistry.registerTileEntity(TileEntityLayout.class, Woot.MODID + ":layout");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        event.getRegistry().register(new ItemBlock(ModBlocks.layoutBlock).setRegistryName(ModBlocks.layoutBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.heartBlock).setRegistryName(ModBlocks.heartBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.controllerBlock).setRegistryName(ModBlocks.controllerBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.boneStructureBlock).setRegistryName(ModBlocks.boneStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.fleshStructureBlock).setRegistryName(ModBlocks.fleshStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blazeStructureBlock).setRegistryName(ModBlocks.blazeStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.enderStructureBlock).setRegistryName(ModBlocks.enderStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.netherStructureBlock).setRegistryName(ModBlocks.netherStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.redstoneStructureBlock).setRegistryName(ModBlocks.redstoneStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.upgradeStructureBlock).setRegistryName(ModBlocks.upgradeStructureBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap1Block).setRegistryName(ModBlocks.cap1Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap2Block).setRegistryName(ModBlocks.cap2Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap3Block).setRegistryName(ModBlocks.cap3Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cap4Block).setRegistryName(ModBlocks.cap4Block.getRegistryName()));
        event.getRegistry().register(new ItemEnderShard());

    }
}
