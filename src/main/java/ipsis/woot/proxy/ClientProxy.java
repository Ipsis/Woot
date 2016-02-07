package ipsis.woot.proxy;

import ipsis.oss.LogHelper;
import ipsis.woot.block.BlockUpgrade;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.manager.Upgrade;
import ipsis.woot.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

    @Override
    protected void registerBlockItemModels() {

        ModBlocks.blockFactory.initModel();
        ModBlocks.blockUpgrade.initModel();
    }

    @Override
    protected void registerItemRenderers() {

        ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        for (Upgrade.Type t : Upgrade.Type.VALUES) {
            itemModelMesher.register(Item.getItemFromBlock(ModBlocks.blockUpgrade), t.getMetadata(),
                    new ModelResourceLocation(Reference.MOD_ID_LOWER + ":" + BlockUpgrade.BASENAME + "_" + t.getName(), "inventory"));
        }
    }
}
