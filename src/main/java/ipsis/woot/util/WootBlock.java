package ipsis.woot.util;

import ipsis.Woot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WootBlock extends Block {

    public WootBlock(Material materialIn, String basename) {

        super(materialIn);
        setCreativeTab(Woot.tab);
        setTranslationKey(Woot.MODID + "." + basename);
        setRegistryName(basename);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(getRegistryName(), ".inventory"));
    }
}
