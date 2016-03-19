package ipsis.woot.proxy;

import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.block.BlockMobFactoryUpgrade;
import ipsis.woot.event.ItemTooltipHandler;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();

        ModBlocks.blockFactory.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockController.initModel();

        ModItems.itemPrism.initModel();
        ModItems.itemXpShard.initModel();
        ModItems.itemSkull.initModel();;

        ModBlocks.registerTileEntities();

        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
    }

    @Override
    public void init() {

        super.init();
    }

    @Override
    public void postInit() {

        super.postInit();

    }
}
