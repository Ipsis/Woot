package ipsis.woot.blocks;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPower extends WootBlock {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockPower(FactoryBlock factoryBlock) {

        super(Material.ROCK, factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    public FactoryBlock getFactoryBlockType() { return this.factoryBlock; }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(getRegistryName(), ".inventory"));
    }
}
