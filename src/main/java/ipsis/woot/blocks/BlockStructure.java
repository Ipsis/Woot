package ipsis.woot.blocks;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStructure extends WootBlock implements ITileEntityProvider {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockStructure(FactoryBlock factoryBlock) {

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


    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityStructure();
    }
}
