package ipsis.woot.blocks;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockExport extends WootBlock {

    private static final String BASENAME = "export";

    public BlockExport() {

        super(Material.ROCK, BASENAME);
    }

    public static String getBasename() { return BASENAME; }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(getRegistryName(), ".inventory"));
    }


}
