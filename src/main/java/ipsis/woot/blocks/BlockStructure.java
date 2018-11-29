package ipsis.woot.blocks;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStructure extends WootBlock implements ITileEntityProvider {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockStructure(FactoryBlock factoryBlock) {
        super(Material.ROCK, factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    public FactoryBlock getFactoryBlockType() { return this.factoryBlock; }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityStructure();
    }
}
