package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.util.FactoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStructure extends Block {

    private final String basename;
    private final FactoryBlock blockType;

    public BlockStructure(FactoryBlock blockType) {

        super(Material.ROCK);
        this.blockType = blockType;
        this.basename = blockType.getName();
        setCreativeTab(Woot.tab);
        setUnlocalizedName(Woot.MODID + "." + this.basename);
        setRegistryName(this.basename);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(getRegistryName(), ".inventory"));
    }
}
