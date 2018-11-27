package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.util.FactoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStructure extends Block implements ITileEntityProvider {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockStructure(FactoryBlock factoryBlock) {

        super(Material.ROCK);
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
        setCreativeTab(Woot.tab);
        setTranslationKey(Woot.MODID + "." + this.basename);
        setRegistryName(this.basename);
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
