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
    protected void registerBlockItemModels() {

        ModBlocks.blockFactory.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockController.initModel();

        ModItems.itemPrism.initModel();
    }

    @Override
    protected void registerItemRenderers() {

        ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        for (EnumSpawnerUpgrade t : EnumSpawnerUpgrade.values()) {
             itemModelMesher.register(Item.getItemFromBlock(ModBlocks.blockUpgrade), t.getMetadata(),
                    new ModelResourceLocation(Reference.MOD_ID_LOWER + ":" + BlockMobFactoryUpgrade.BASENAME + "_" + t.getName(), "inventory"));
        }

        for (EnumMobFactoryModule m : EnumMobFactoryModule.VALUES) {
            itemModelMesher.register(Item.getItemFromBlock(ModBlocks.blockStructure), m.getMetadata(),
                    new ModelResourceLocation(Reference.MOD_ID_LOWER + ":" + BlockMobFactoryStructure.BASENAME + "_" + m.getName(), "inventory"));
        }
    }

    @Override
    protected void registerEventHandlers() {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
    }
}
