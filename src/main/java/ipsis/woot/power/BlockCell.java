package ipsis.woot.power;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCell extends WootBlock implements ITileEntityProvider {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockCell(FactoryBlock factoryBlock) {
        super(Material.ROCK, factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    public FactoryBlock getFactoryBlockType() { return this.factoryBlock; }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {

        if (factoryBlock == FactoryBlock.POWER_1)
            return new TileEntityCell(10000);
        else if (factoryBlock == FactoryBlock.POWER_2)
            return new TileEntityCell(100000);
        else
            return new TileEntityCell(1000000);
    }
}
