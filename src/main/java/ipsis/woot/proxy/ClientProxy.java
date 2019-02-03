package ipsis.woot.proxy;

import ipsis.Woot;
import ipsis.woot.ModBlocks;
import ipsis.woot.ModItems;
import ipsis.woot.blocks.TileEntityLayout;
import ipsis.woot.client.TESRLayout;
import ipsis.woot.event.TextureStitchEventHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Woot.MODID)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(new TextureStitchEventHandler());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLayout.class, new TESRLayout());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        ModBlocks.layoutBlock.initModel();
        ModBlocks.heartBlock.initModel();
        ModBlocks.controllerBlock.initModel();
        ModBlocks.power1Block.initModel();
        ModBlocks.power2Block.initModel();
        ModBlocks.power3Block.initModel();
        ModBlocks.importBlock.initModel();
        ModBlocks.exportBlock.initModel();
        ModBlocks.boneStructureBlock.initModel();
        ModBlocks.fleshStructureBlock.initModel();
        ModBlocks.blazeStructureBlock.initModel();
        ModBlocks.enderStructureBlock.initModel();
        ModBlocks.netherStructureBlock.initModel();
        ModBlocks.redstoneStructureBlock.initModel();
        ModBlocks.upgradeStructureBlock.initModel();
        ModBlocks.cap1Block.initModel();
        ModBlocks.cap2Block.initModel();
        ModBlocks.cap3Block.initModel();
        ModBlocks.cap4Block.initModel();

        ModBlocks.squeezerBlock.initModel();
        ModBlocks.stamperBlock.initModel();

        ModBlocks.blockPureDye.initModel();
        ModBlocks.blockPureEnchant.initModel();
        ModBlocks.blockEffort.initModel();

        ModBlocks.generatorTickBlock.initModel();
        ModBlocks.generatorRFBlock.initModel();
        ModBlocks.generatorFluidBlock.initModel();
        ModBlocks.creativeRFBlock.initModel();

        ModItems.enderShard.initModel();
        ModItems.yaHammer.initModel();
        ModItems.intern.initModel();
        ModItems.prism.initModel();
        ModItems.dyeBlank.initModel();
        ModItems.stygianPlate.initModel();
        ModItems.basicEnchantedPlate.initModel();
        ModItems.enchantedPlate.initModel();
        ModItems.advancedEnchantedPlate.initModel();
        ModItems.debug.initModel();
        ModItems.blackDyePlate.initModel();
        ModItems.redDyePlate.initModel();
    }
}
