package ipsis.woot.proxy;

import ipsis.Woot;
import ipsis.woot.Config;
import ipsis.woot.ModBlocks;
import ipsis.woot.ModFluids;
import ipsis.woot.blocks.*;
import ipsis.woot.fluids.BlockPureDye;
import ipsis.woot.fluids.BlockPureEnchant;
import ipsis.woot.generators.*;
import ipsis.woot.heart.BlockHeart;
import ipsis.woot.heart.TileEntityHeart;
import ipsis.woot.items.*;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.layout.TileEntityLayout;
import ipsis.woot.machines.squeezer.BlockSqueezer;
import ipsis.woot.machines.squeezer.SqueezerManager;
import ipsis.woot.machines.squeezer.TileEntitySqueezer;
import ipsis.woot.machines.stamper.BlockStamper;
import ipsis.woot.machines.stamper.TileEntityStamper;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.power.*;
import ipsis.woot.util.FactoryBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber(modid = Woot.MODID)
public class CommonProxy {
    public static Configuration config;


    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages(Woot.MODID);
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "woot.cfg"));
        Config.readConfig();
        ModFluids.init();
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Woot.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        Woot.DROP_MANAGER.init();
        SqueezerManager.INSTANCE.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        event.getRegistry().register(new BlockLayout());
        event.getRegistry().register(new BlockHeart());
        event.getRegistry().register(new BlockController());
        event.getRegistry().register(new BlockCell(FactoryBlock.POWER_1));
        event.getRegistry().register(new BlockCell(FactoryBlock.POWER_2));
        event.getRegistry().register(new BlockCell(FactoryBlock.POWER_3));
        event.getRegistry().register(new BlockImport());
        event.getRegistry().register(new BlockExport());

        event.getRegistry().register(new BlockSqueezer());
        event.getRegistry().register(new BlockStamper());

        event.getRegistry().register(new BlockConvertor(BlockConvertor.GeneratorType.TICK));
        event.getRegistry().register(new BlockConvertor(BlockConvertor.GeneratorType.RF));
        event.getRegistry().register(new BlockConvertor(BlockConvertor.GeneratorType.FLUID));
        event.getRegistry().register(new BlockCreativeRF());

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

        event.getRegistry().register(new BlockPureDye());
        event.getRegistry().register(new BlockPureEnchant());
        event.getRegistry().register(new BlockEffort());

        // TODO different event for TEs in 1.13 ?
        GameRegistry.registerTileEntity(TileEntityHeart.class, new ResourceLocation(Woot.MODID, "heart"));
        GameRegistry.registerTileEntity(TileEntityController.class, new ResourceLocation(Woot.MODID, "controller"));
        GameRegistry.registerTileEntity(TileEntityLayout.class, new ResourceLocation(Woot.MODID, "layout"));
        GameRegistry.registerTileEntity(TileEntityStructure.class, new ResourceLocation(Woot.MODID, "structure"));
        GameRegistry.registerTileEntity(TileEntityConvertorTick.class, new ResourceLocation(Woot.MODID, "generator_tick"));
        GameRegistry.registerTileEntity(TileEntityConvertorFluid.class, new ResourceLocation(Woot.MODID, "generator_fluid"));
        GameRegistry.registerTileEntity(TileEntityConvertorRF.class, new ResourceLocation(Woot.MODID, "generator_rf"));
        GameRegistry.registerTileEntity(TileEntityCreativeRF.class, new ResourceLocation(Woot.MODID, "creative_rf"));
        GameRegistry.registerTileEntity(TileEntitySqueezer.class, new ResourceLocation(Woot.MODID, "squeezer"));
        GameRegistry.registerTileEntity(TileEntityStamper.class, new ResourceLocation(Woot.MODID, "stamper"));
        GameRegistry.registerTileEntity(TileEntityCell.class, new ResourceLocation(Woot.MODID, "cell"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        event.getRegistry().register(new ItemBlock(ModBlocks.layoutBlock).setRegistryName(ModBlocks.layoutBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.heartBlock).setRegistryName(ModBlocks.heartBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.controllerBlock).setRegistryName(ModBlocks.controllerBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.power1Block).setRegistryName(ModBlocks.power1Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.power2Block).setRegistryName(ModBlocks.power2Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.power3Block).setRegistryName(ModBlocks.power3Block.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.generatorTickBlock).setRegistryName(ModBlocks.generatorTickBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.generatorRFBlock).setRegistryName(ModBlocks.generatorRFBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.generatorFluidBlock).setRegistryName(ModBlocks.generatorFluidBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.creativeRFBlock).setRegistryName(ModBlocks.creativeRFBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.importBlock).setRegistryName(ModBlocks.importBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.exportBlock).setRegistryName(ModBlocks.exportBlock.getRegistryName()));
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
        event.getRegistry().register(new ItemBlock(ModBlocks.squeezerBlock).setRegistryName(ModBlocks.squeezerBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.stamperBlock).setRegistryName(ModBlocks.stamperBlock.getRegistryName()));
        event.getRegistry().register(new ItemEnderShard());
        event.getRegistry().register(new ItemYaHammer());
        event.getRegistry().register(new ItemIntern());
        event.getRegistry().register(new ItemPrism());
        event.getRegistry().register(new ItemStygianPlate());
        event.getRegistry().register(new ItemDyeBlank());
        event.getRegistry().register(new ItemDebug());

        event.getRegistry().register(new ItemEnchantedPlate(0, "basic_enchantedplate"));
        event.getRegistry().register(new ItemEnchantedPlate(1, "enchantedplate"));
        event.getRegistry().register(new ItemEnchantedPlate(2, "advanced_enchantedplate"));

        event.getRegistry().register(new ItemDyePlate(SqueezerManager.DyeMakeup.BLACK, "black_dyeplate"));
        event.getRegistry().register(new ItemDyePlate(SqueezerManager.DyeMakeup.RED, "red_dyeplate"));
    }
}
