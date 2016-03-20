package ipsis.oss.client;

import ipsis.woot.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * cpw/ironchest - ModelHelper.java
 */
@SideOnly(Side.CLIENT)
public class ModelHelper {

    public static void registerItem(Item item, int metadata, String itemName) {

        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(Reference.MOD_ID + ":" + itemName, "inventory"));
    }

    public static void registerBlock(Block block, int metadata, String blockName) {

        registerItem(Item.getItemFromBlock(block), metadata, blockName);
    }

    public static void registerBlock(Block block, String blockName) {

        registerBlock(block, 0, blockName);
    }

    public static void registerItem(Item item, String itemName) {

        registerItem(item, 0, itemName);
    }
}
